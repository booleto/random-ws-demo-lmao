package com.example.restdemo.service;

import com.example.restdemo.exception.VehicleUnregisteredException;
import com.example.restdemo.model.Vehicle;
import com.example.restdemo.repository.VehicleReactiveRepository;
import com.example.restdemo.service.task.MonitorTask;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

@Service
@AllArgsConstructor
public class MonitorService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MonitorService.class);

    private final ThreadPoolTaskScheduler taskScheduler;
    private final Map<WebSocketSession, List<ScheduledFuture<?>>> tasks;
    private final VehicleService vehicleService;

    public void addMonitorTask(WebSocketSession session, Long vid, Long delay) {
        var task = createMonitorTask(session, vid, delay);
        ScheduledFuture<?> scheduledTask = taskScheduler.scheduleAtFixedRate(task, Duration.ofMillis(delay));

        if (tasks.containsKey(session)) {
            tasks.get(session).add(scheduledTask);
        } else {
            List<ScheduledFuture<?>> taskList = new ArrayList<>();
            taskList.add(scheduledTask);
            tasks.put(session, taskList);
        }
    }

    public void cancelMonitorTask(WebSocketSession session) {
        LOGGER.info("Cancelling task from session: ".concat(session.getId()));
        tasks.get(session).parallelStream().forEach(
                scheduledFuture -> scheduledFuture.cancel(true)
        );
        tasks.remove(session);
    }

    public MonitorTask createMonitorTask(WebSocketSession session, Long vid, Long delay) {
        Vehicle vehicle = vehicleService.findByVid(vid)
                .doOnError(throwable -> {
                    throw new VehicleUnregisteredException("Unregistered vehicle", 404);
                })
                .block();
        return new MonitorTask(session, vehicle, delay);
    }
}
