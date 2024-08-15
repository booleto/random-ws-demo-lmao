package com.example.restdemo.controller.handler;

import com.example.restdemo.model.MonitorRequest;
import com.example.restdemo.model.Vehicle;
import com.example.restdemo.service.MonitorService;
import com.example.restdemo.service.VehicleService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MonitorSocketHandler implements WebSocketHandler {
    private final Logger LOGGER = LoggerFactory.getLogger(MonitorSocketHandler.class);
    @Autowired
    private MonitorService monitorService;
//    private final Map<String, WebSocketSession> sessionMap = new ConcurrentHashMap<>();
//
//    @Override
//    public Mono<Void> handle(WebSocketSession session) {
//        Map<String, Object> handshakeData = session.getAttributes();
//        Integer vid = (Integer) handshakeData.get("vid");
//        Integer delay = (Integer) handshakeData.get("delay");
//
//        session
//            .receive()
//            .handle((message, synchronousSink) -> {
//                    LOGGER.info(message.getPayloadAsText());
//                    sendVehicleData(session)
//                }
//            );
//
//    }
//
//    private Mono<String> sendVehicleData(WebSocketSession session, String message, Integer delay) {
//        WebSocketMessage webMessage = session.textMessage(message);
//
//
//    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        LOGGER.info("Connection closed");
        monitorService.cancelMonitorTask(session);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        LOGGER.info("Connection established");

    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        LOGGER.info("Handling message");
        session.sendMessage(new TextMessage("Received message"));

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode data = objectMapper.readTree(message.getPayload().toString());
        Long vid = data.get("vid").asLong();
        Long delay = data.get("delay").asLong(1000);

        monitorService.addMonitorTask(session, vid, delay);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        LOGGER.info("Message transport error");
        throw new RuntimeException("Error omg");
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}

