package com.contoso.logging.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Entity representing a log record.
 * Migrated from COBOL AS400 physical file LOGP0.pf
 * 
 * Original COBOL fields:
 * - xdate (8 chars) -> logDate
 * - xtime (8 chars) -> logTime  
 * - xjob (10 chars) -> jobName
 * - xuser (10 chars) -> userName
 * - xjobnum (6 chars) -> jobNumber
 * - xtext (40 chars) -> messageText
 */
@Entity
@Table(name = "log_records")
public class LogRecord {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "log_date", nullable = false)
    private LocalDate logDate;
    
    @Column(name = "log_time", nullable = false)
    private LocalTime logTime;
    
    @Column(name = "job_name", length = 10)
    private String jobName;
    
    @Column(name = "user_name", length = 10)
    private String userName;
    
    @Column(name = "job_number", length = 6)
    private String jobNumber;
    
    @Column(name = "message_text", length = 40, nullable = false)
    private String messageText;
    
    public LogRecord() {
    }
    
    public LogRecord(LocalDate logDate, LocalTime logTime, String jobName, 
                     String userName, String jobNumber, String messageText) {
        this.logDate = logDate;
        this.logTime = logTime;
        this.jobName = jobName;
        this.userName = userName;
        this.jobNumber = jobNumber;
        this.messageText = messageText;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public LocalDate getLogDate() {
        return logDate;
    }
    
    public void setLogDate(LocalDate logDate) {
        this.logDate = logDate;
    }
    
    public LocalTime getLogTime() {
        return logTime;
    }
    
    public void setLogTime(LocalTime logTime) {
        this.logTime = logTime;
    }
    
    public String getJobName() {
        return jobName;
    }
    
    public void setJobName(String jobName) {
        this.jobName = jobName;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getJobNumber() {
        return jobNumber;
    }
    
    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }
    
    public String getMessageText() {
        return messageText;
    }
    
    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }
}
