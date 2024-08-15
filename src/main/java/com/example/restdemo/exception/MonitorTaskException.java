package com.example.restdemo.exception;

import lombok.Getter;
import org.springframework.web.socket.WebSocketSession;

@Getter
public class MonitorTaskException extends RuntimeException{
    private final WebSocketSession session;

    public MonitorTaskException(String message, WebSocketSession session) {
        super(message);
        this.session = session;
    }
}
