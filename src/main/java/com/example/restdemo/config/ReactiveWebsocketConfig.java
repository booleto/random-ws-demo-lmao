package com.example.restdemo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import reactor.core.publisher.Mono;

import java.util.List;

@Configuration
@EnableWebSocket
public class ReactiveWebsocketConfig implements WebFluxConfigurer {

}
