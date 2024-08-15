package com.example.restdemo.model;

import jakarta.annotation.PostConstruct;
import lombok.*;
import org.springframework.web.reactive.socket.WebSocketMessage;

import java.time.Instant;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WSMessage {
    private Integer statusCode;
    private String message;
    private String senderIdentifier;
    private String operationType;

    @Setter(AccessLevel.NONE)
    private Date timeStamp;

    @PostConstruct
    private void setTimeStamp() {
        this.timeStamp = Date.from(Instant.now());
    }
}
