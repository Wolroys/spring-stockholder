package com.wolroys.springstockapp.service;

import com.wolroys.springstockapp.dto.*;
import com.wolroys.springstockapp.exception.StockNotFoundException;
import com.wolroys.springstockapp.model.Currency;
import com.wolroys.springstockapp.model.Stock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.tinkoff.piapi.contract.v1.InstrumentShort;
import ru.tinkoff.piapi.contract.v1.Quotation;
import ru.tinkoff.piapi.core.InvestApi;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class TinkoffStockService implements StockService{

    private final InvestApi api;


    @Async
    public CompletableFuture<List<InstrumentShort>> getInstrumentShortByTicker(String ticker){
        var instrumentsService = api.getInstrumentsService();
        return instrumentsService.findInstrument(ticker);
    }

    @Async
    public CompletableFuture<String> getCurrency(String figi){
        return CompletableFuture.completedFuture(api.getInstrumentsService().getInstrumentByFigi(figi).join().getCurrency().toUpperCase());
    }


    @Override
    public CompletableFuture<Stock> getStockByTicker(String ticker) {

        var list = getInstrumentShortByTicker(ticker).join();

        if (list.isEmpty()){
            throw new StockNotFoundException(String.format("Stock %S not found.", ticker));
        }

        var itemShort = list.get(0);

        return CompletableFuture.completedFuture(new Stock(
                itemShort.getTicker(),
                itemShort.getClassCode(),
                itemShort.getFigi(),
                itemShort.getName(),
                itemShort.getInstrumentType(),
                Currency.valueOf(getCurrency(itemShort.getFigi()).join()),
                "TINKOFF"
        ));
    }

    @Async
    @Override
    public CompletableFuture<StocksDto> getStocksByTickers(TickersDto tickers){  //TODO CompletableFuture return
        List<CompletableFuture<List<InstrumentShort>>> instruments = new ArrayList<>();
        tickers.getTickers().forEach(ticker -> instruments.add(getInstrumentShortByTicker(ticker)));
        List<Stock> stocks = instruments.stream()
                .map(CompletableFuture::join)
                .map(instr -> {
                    if (!instr.isEmpty()){
                        return instr.get(0);
                    }

                    return null;
                })
                .filter(Objects::nonNull)
                .map(item -> new Stock(
                        item.getTicker(),
                        item.getClassCode(),
                        item.getFigi(),
                        item.getName(),
                        item.getInstrumentType(),
                        Currency.valueOf(getCurrency(item.getFigi()).join()),
                        "TINKOFF"
                )).toList();

        return CompletableFuture.completedFuture(new StocksDto(stocks));
    }

    public StockPrice getPrice(String figi){ //TODO switch to instrumentId
        var orderBook = api.getMarketDataService().getOrderBook(figi, 1);
        Quotation quotation = orderBook.join().getLastPrice();

        Double lastPrice = quotation.getUnits() == 0 && quotation.getNano() == 0
                ? BigDecimal.ZERO.doubleValue()
                : BigDecimal.valueOf(quotation.getUnits())
                .add(BigDecimal.valueOf(quotation.getNano(), 9)).doubleValue();

        return new StockPrice(figi, lastPrice);
    }

    public StocksPricesDto getPrices(FigiesDto figiesDto){
        long start = System.currentTimeMillis();
        var stockPrices = figiesDto.getFigies().stream()
                .map(this::getPrice)
                .toList();

        log.info("Time - {}", System.currentTimeMillis() - start);
        return new StocksPricesDto(stockPrices);
    }
}
