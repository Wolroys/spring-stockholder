package com.wolroys.springstockapp.exception;

public class StockNotFoundException extends RuntimeException{

    public StockNotFoundException(String message){
        super(message);
    }
}
