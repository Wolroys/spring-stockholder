package com.wolroys.springstockapp.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Stock {
    String ticker;
//    String classCode;
    String figi;
    String name;
    String type;
    Currency currency;
    String source;
}
