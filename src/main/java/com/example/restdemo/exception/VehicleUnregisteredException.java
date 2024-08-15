package com.example.restdemo.exception;

import lombok.Data;

@Data
public class VehicleUnregisteredException extends RuntimeException {
    private final Integer code;

    public VehicleUnregisteredException(String message, Integer code) {
        super(message);
        this.code = code;
    }
}
