package com.wolroys.springstockapp.service;

import com.wolroys.springstockapp.dto.StocksDto;
import com.wolroys.springstockapp.dto.TickersDto;
import com.wolroys.springstockapp.model.Stock;

import java.util.concurrent.CompletableFuture;

public interface StockService {

    CompletableFuture<Stock> getStockByTicker(String ticker);

    CompletableFuture<StocksDto> getStocksByTickers(TickersDto tickers);
}
