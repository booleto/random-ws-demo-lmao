package com.example.restdemo.exception;

import lombok.Data;

@Data
public class VehicleDupeException extends RuntimeException {
    private final Integer code;

    public VehicleDupeException(String message, Integer code) {
        super(message);
        this.code = code;
    }
}
