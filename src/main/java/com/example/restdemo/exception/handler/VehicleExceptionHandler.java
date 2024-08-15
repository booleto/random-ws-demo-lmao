package com.example.restdemo.exception.handler;

import com.example.restdemo.exception.VehicleDupeException;
import com.example.restdemo.exception.VehicleUnregisteredException;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class VehicleExceptionHandler {
    @ExceptionHandler(VehicleUnregisteredException.class)
    public ErrorResponse handleVehicleException(VehicleUnregisteredException e) {
        return ErrorResponse.builder(
                e,
                HttpStatusCode.valueOf(e.getCode()),
                e.getMessage()
        ).build();
    }

    @ExceptionHandler(VehicleDupeException.class)
    public ErrorResponse handleDuplicateException(VehicleDupeException e) {
        return ErrorResponse.builder(
                e,
                HttpStatusCode.valueOf(e.getCode()),
                e.getMessage()
        ).build();
    }
}
