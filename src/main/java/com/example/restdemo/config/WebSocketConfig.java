package com.example.restdemo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocket
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    private final Integer outHeartbeat = 2000;
    private final Integer inHeartbeat = 2000;
    private final ThreadPoolTaskScheduler messageBrokerTaskScheduler;

    public WebSocketConfig(/*Environment env,*/ @Lazy ThreadPoolTaskScheduler taskScheduler) {
//        this.outHeartbeat = env.getProperty("server.websocket.heartbeat.out", Integer.class);
//        this.inHeartbeat = env.getProperty("server.websocket.heartbeat.in", Integer.class);
        this.messageBrokerTaskScheduler = taskScheduler;
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/test")
                .setHeartbeatValue(new long[] {outHeartbeat, inHeartbeat})
                .setTaskScheduler(this.messageBrokerTaskScheduler);
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        WebSocketMessageBrokerConfigurer.super.registerStompEndpoints(registry);
        registry.addEndpoint("/speed").setAllowedOrigins("*");
        registry.addEndpoint("/speed").withSockJS();
//        registry.addEndpoint("/test").setAllowedOrigins("*");
//        registry.addEndpoint("/test").setAllowedOrigins("*").withSockJS();
    }


}
