package com.example.restdemo.controller;

import com.example.restdemo.model.Vehicle;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

@Controller
@MessageMapping("/v0/monitor")
public class MonitorController {
    @MessageMapping("/speed")
    public Flux<Vehicle> getSpeedUpdate() {
        return null;
    }

    @MessageMapping("/test")
    @SendTo("/topic/test")
    public String sendTestMessage(String message) {
        return message;
    }
}
