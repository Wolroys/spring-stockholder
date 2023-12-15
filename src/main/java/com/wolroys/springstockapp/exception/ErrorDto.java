package com.wolroys.springstockapp.exception;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class ErrorDto {

    private String error;
}
