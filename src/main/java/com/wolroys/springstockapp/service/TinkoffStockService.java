package com.wolroys.springstockapp.service;

import com.wolroys.springstockapp.exception.StockNotFoundException;
import com.wolroys.springstockapp.model.Currency;
import com.wolroys.springstockapp.model.Stock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.piapi.core.InvestApi;

@Service
@RequiredArgsConstructor
public class TinkoffStockService implements StockService{

    private final InvestApi api;

    @Override
    public Stock getStockByTicker(String ticker) {
        var instrumentsService = api.getInstrumentsService();
        var listCF =  instrumentsService.findInstrument(ticker);
        var list = listCF.join().stream().toList();

        if (list.isEmpty()){
            throw new StockNotFoundException(String.format("Stock %S not found.", ticker));
        }

        var itemShort = list.get(0);

        var item = instrumentsService.getInstrumentByTicker(ticker, itemShort.getClassCode()).join();


        return new Stock(
                item.getTicker(),
//                item.getClassCode(),
                item.getFigi(),
                item.getName(),
                item.getInstrumentType(),
                Currency.valueOf(item.getCurrency().toUpperCase()),
                "TINKOFF"
        );
    }
}
