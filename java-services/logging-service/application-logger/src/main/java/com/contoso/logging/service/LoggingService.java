package com.contoso.logging.service;

import com.contoso.logging.model.LogRecord;
import com.contoso.logging.model.LogRequest;
import com.contoso.logging.repository.LogRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Service class for application logging.
 * Business logic migrated from COBOL AS400 program LOG0010CB.cblle
 * 
 * Original COBOL logic:
 * - Accepts message text via linkage section
 * - Calls CL program to get job attributes (job name, user, job number)
 * - Gets system date (ACCEPT FROM DATE YYYYMMDD)
 * - Gets system time (ACCEPT FROM TIME)
 * - Writes to LOGP0 physical file
 */
@Service
public class LoggingService {
    
    private final LogRecordRepository logRecordRepository;
    
    @Autowired
    public LoggingService(LogRecordRepository logRecordRepository) {
        this.logRecordRepository = logRecordRepository;
    }
    
    /**
     * Create a log entry.
     * Mimics the COBOL LOG0010CB program's functionality.
     * 
     * @param request Log request containing message and optional job info
     * @return Created log record
     */
    public LogRecord createLogEntry(LogRequest request) {
        if (request.getMessageText() == null || request.getMessageText().trim().isEmpty()) {
            throw new IllegalArgumentException("Message text is required");
        }
        
        if (request.getMessageText().length() > 40) {
            throw new IllegalArgumentException("Message text must not exceed 40 characters");
        }
        
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        
        String jobName = request.getJobName() != null ? 
            truncate(request.getJobName(), 10) : "WEBSERVICE";
        String userName = request.getUserName() != null ? 
            truncate(request.getUserName(), 10) : "SYSTEM";
        String jobNumber = request.getJobNumber() != null ? 
            truncate(request.getJobNumber(), 6) : "000000";
        
        LogRecord logRecord = new LogRecord(
            currentDate,
            currentTime,
            jobName,
            userName,
            jobNumber,
            request.getMessageText()
        );
        
        return logRecordRepository.save(logRecord);
    }
    
    /**
     * Retrieve all log entries.
     */
    public List<LogRecord> getAllLogEntries() {
        return logRecordRepository.findAll();
    }
    
    /**
     * Retrieve log entries for a specific date.
     */
    public List<LogRecord> getLogEntriesByDate(LocalDate date) {
        return logRecordRepository.findByLogDate(date);
    }
    
    /**
     * Retrieve log entries for a specific user.
     */
    public List<LogRecord> getLogEntriesByUser(String userName) {
        return logRecordRepository.findByUserName(userName);
    }
    
    /**
     * Retrieve log entries for a specific job.
     */
    public List<LogRecord> getLogEntriesByJob(String jobName) {
        return logRecordRepository.findByJobName(jobName);
    }
    
    /**
     * Truncate string to specified length (matching COBOL field sizes).
     */
    private String truncate(String value, int maxLength) {
        if (value == null) {
            return null;
        }
        return value.length() > maxLength ? value.substring(0, maxLength) : value;
    }
}
