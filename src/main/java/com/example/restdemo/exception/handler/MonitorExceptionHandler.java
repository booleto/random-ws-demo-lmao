package com.example.restdemo.exception.handler;

import com.example.restdemo.exception.MonitorTaskException;
import org.springframework.util.ErrorHandler;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

public class MonitorExceptionHandler implements ErrorHandler {
    @Override
    public void handleError(Throwable t) {
        if (t instanceof MonitorTaskException) {
            t = (MonitorTaskException) t;
            WebSocketSession errorSession = ((MonitorTaskException) t).getSession();
            if (errorSession.isOpen()) {
                try {
                    errorSession.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
