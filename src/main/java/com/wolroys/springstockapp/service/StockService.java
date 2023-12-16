package com.wolroys.springstockapp.service;

import com.wolroys.springstockapp.dto.FigiesDto;
import com.wolroys.springstockapp.dto.StocksDto;
import com.wolroys.springstockapp.dto.StocksPricesDto;
import com.wolroys.springstockapp.dto.TickersDto;
import com.wolroys.springstockapp.model.Stock;

import java.util.concurrent.CompletableFuture;

public interface StockService {

    CompletableFuture<Stock> getStockByTicker(String ticker);

    CompletableFuture<StocksDto> getStocksByTickers(TickersDto tickers);

    StocksPricesDto getPrices(FigiesDto figiesDto);
}
