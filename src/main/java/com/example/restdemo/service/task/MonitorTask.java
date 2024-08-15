package com.example.restdemo.service.task;

import com.example.restdemo.exception.MonitorTaskException;
import com.example.restdemo.model.Vehicle;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;


@AllArgsConstructor
public class MonitorTask implements Runnable{
    private static Logger LOGGER = LoggerFactory.getLogger(MonitorTask.class);
    private WebSocketSession session;
    private Vehicle monitoredVehicle;
    private Long delay;

    @Override
    public void run() {
        try {
            LOGGER.info("Attempting to send message to session: ".concat(session.getId()));
            String message = new ObjectMapper().writeValueAsString(monitoredVehicle);
            session.sendMessage(new TextMessage(message));
        } catch (IOException e) {
            throw new MonitorTaskException("Error while sending message", session);
        }
    }


}
