package com.example.restdemo.config;

import com.example.restdemo.controller.handler.MonitorSocketHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
public class WebSocketHandlerConfig implements WebSocketConfigurer {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketHandlerConfig.class);
    @Bean
    public WebSocketHandler webSocketHandler() {
        return new MonitorSocketHandler();
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler(), "/test")
                .setAllowedOrigins("*");
        registry.addHandler(webSocketHandler(), "/test")
                .setAllowedOrigins("*")
                .withSockJS();
        LOGGER.info("Registered Websocket endpoint");
    }


}
