package com.contoso.logging.model;

/**
 * Request model for creating log entries.
 * Represents the linkage section data from COBOL LOG0010CB program.
 */
public class LogRequest {
    private String messageText;
    private String jobName;
    private String userName;
    private String jobNumber;
    
    public LogRequest() {
    }
    
    public LogRequest(String messageText, String jobName, String userName, String jobNumber) {
        this.messageText = messageText;
        this.jobName = jobName;
        this.userName = userName;
        this.jobNumber = jobNumber;
    }
    
    public String getMessageText() {
        return messageText;
    }
    
    public void setMessageText(String messageText) {
        this.messageText = messageText;
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
}
