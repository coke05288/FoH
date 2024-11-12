package com.hzipyb.productservice.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.stereotype.Component;

@Component
public class CustomLogger {
    private static final Logger logger = LoggerFactory.getLogger(CustomLogger.class);
    private static final Marker USER_ACTION = MarkerFactory.getMarker("USER_ACTION");

    public void logUserAction(String message) {
        logger.info(USER_ACTION, message);
    }
}
