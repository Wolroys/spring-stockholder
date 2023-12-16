package com.wolroys.springstockapp.dto;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class StockPrice {

    private String figi;
    private double price;
}
