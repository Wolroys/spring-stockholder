package com.wolroys.springstockapp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Setter
@Getter
public class TickersDto {
    private List<String> tickers;
}
