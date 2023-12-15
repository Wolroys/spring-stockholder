package com.wolroys.springstockapp.controller;

import com.wolroys.springstockapp.dto.StocksDto;
import com.wolroys.springstockapp.dto.TickersDto;
import com.wolroys.springstockapp.model.Stock;
import com.wolroys.springstockapp.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @GetMapping("/stocks/{ticker}")
    public Stock getStock(@PathVariable String ticker){
        return stockService.getStockByTicker(ticker);
    }

    @PostMapping("/stocks/getStocksByTickers")
    public CompletableFuture<StocksDto> getStocksByTickers(@RequestBody TickersDto tickersDto){
        return stockService.getStocksByTickers(tickersDto);
    }
}
