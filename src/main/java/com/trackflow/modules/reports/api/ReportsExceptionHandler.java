package com.trackflow.modules.reports.api;

import com.trackflow.modules.reports.domain.TrackingNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.Map;

@RestControllerAdvice
public class ReportsExceptionHandler {

    @ExceptionHandler(TrackingNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(TrackingNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                "timestamp", Instant.now(),
                "status", 404,
                "error", "Not Found",
                "message", ex.getMessage()
        ));
    }
}