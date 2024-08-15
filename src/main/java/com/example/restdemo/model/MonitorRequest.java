package com.example.restdemo.model;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.Date;

@Data
@AllArgsConstructor
public class MonitorRequest {
    private Long vid;
    private Long delay;
    private Date timeStamp;

    @PostConstruct
    private void setTimeStamp() {
        this.timeStamp = Date.from(Instant.now());
    }
}
