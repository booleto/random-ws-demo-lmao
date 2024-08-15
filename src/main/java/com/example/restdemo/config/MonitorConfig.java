package com.example.restdemo.config;

import com.example.restdemo.exception.handler.MonitorExceptionHandler;
import com.example.restdemo.service.task.MonitorTask;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Configuration
public class MonitorConfig {
    private final Map<WebSocketSession, ScheduledFuture<MonitorTask>> monitorTasks = new ConcurrentHashMap<>();

    @Bean
    public Map<WebSocketSession, ScheduledFuture<MonitorTask>> getMonitorTasks() {
        return monitorTasks;
    }

    @Bean
    public MonitorExceptionHandler monitorExceptionHandler() {
        return new MonitorExceptionHandler();
    }

    @Bean
    public ThreadPoolTaskScheduler getTaskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setRemoveOnCancelPolicy(true);
        taskScheduler.setErrorHandler(monitorExceptionHandler());
        return taskScheduler;
    }
}
