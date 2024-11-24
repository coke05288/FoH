package com.hzipyb.inventoryservice.exception;

public class InventorySoldOutException extends RuntimeException{
    public InventorySoldOutException(String message){
        super(message);
    }
}
