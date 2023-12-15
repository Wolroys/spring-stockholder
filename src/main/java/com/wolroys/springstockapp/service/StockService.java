package com.wolroys.springstockapp.service;

import com.wolroys.springstockapp.model.Stock;

public interface StockService {

    Stock getStockByTicker(String ticker);
}
