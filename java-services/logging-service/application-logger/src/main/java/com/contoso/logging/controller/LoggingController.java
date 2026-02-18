package com.contoso.logging.controller;

import com.contoso.logging.model.LogRecord;
import com.contoso.logging.model.LogRequest;
import com.contoso.logging.service.LoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST controller for application logging.
 * Provides HTTP API for the functionality from COBOL AS400 program LOG0010CB.cblle
 */
@RestController
@RequestMapping("/api/v1/logs")
public class LoggingController {
    
    private final LoggingService loggingService;
    
    @Autowired
    public LoggingController(LoggingService loggingService) {
        this.loggingService = loggingService;
    }
    
    /**
     * Create a new log entry.
     * 
     * POST /api/v1/logs
     * Body: {"messageText":"User login","userName":"JOHN","jobName":"WEBAPP","jobNumber":"123456"}
     */
    @PostMapping
    public ResponseEntity<?> createLogEntry(@RequestBody LogRequest request) {
        try {
            LogRecord logRecord = loggingService.createLogEntry(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(logRecord);
        } catch (IllegalArgumentException ex) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (Exception ex) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Internal server error: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    
    /**
     * Retrieve all log entries.
     * 
     * GET /api/v1/logs
     */
    @GetMapping
    public ResponseEntity<List<LogRecord>> getAllLogs() {
        List<LogRecord> logs = loggingService.getAllLogEntries();
        return ResponseEntity.ok(logs);
    }
    
    /**
     * Retrieve log entries for a specific date.
     * 
     * GET /api/v1/logs/date/2024-07-01
     */
    @GetMapping("/date/{date}")
    public ResponseEntity<List<LogRecord>> getLogsByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<LogRecord> logs = loggingService.getLogEntriesByDate(date);
        return ResponseEntity.ok(logs);
    }
    
    /**
     * Retrieve log entries for a specific user.
     * 
     * GET /api/v1/logs/user/JOHN
     */
    @GetMapping("/user/{userName}")
    public ResponseEntity<List<LogRecord>> getLogsByUser(@PathVariable String userName) {
        List<LogRecord> logs = loggingService.getLogEntriesByUser(userName);
        return ResponseEntity.ok(logs);
    }
    
    /**
     * Retrieve log entries for a specific job.
     * 
     * GET /api/v1/logs/job/WEBAPP
     */
    @GetMapping("/job/{jobName}")
    public ResponseEntity<List<LogRecord>> getLogsByJob(@PathVariable String jobName) {
        List<LogRecord> logs = loggingService.getLogEntriesByJob(jobName);
        return ResponseEntity.ok(logs);
    }
    
    /**
     * Health check endpoint.
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "Application Logging Service");
        response.put("migrated_from", "COBOL AS400 LOG0010CB.cblle");
        return ResponseEntity.ok(response);
    }
}
