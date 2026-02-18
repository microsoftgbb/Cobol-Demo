package com.contoso.holiday.model;

/**
 * Response model for Canada Day calculator.
 * Represents the day of week that Canada Day falls on for a given year.
 */
public class CanadaDayResponse {
    private int year;
    private String dayOfWeek;
    private String message;
    private boolean isWeekend;
    
    public CanadaDayResponse() {
    }
    
    public CanadaDayResponse(int year, String dayOfWeek, String message, boolean isWeekend) {
        this.year = year;
        this.dayOfWeek = dayOfWeek;
        this.message = message;
        this.isWeekend = isWeekend;
    }
    
    public int getYear() {
        return year;
    }
    
    public void setYear(int year) {
        this.year = year;
    }
    
    public String getDayOfWeek() {
        return dayOfWeek;
    }
    
    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public boolean isWeekend() {
        return isWeekend;
    }
    
    public void setWeekend(boolean weekend) {
        isWeekend = weekend;
    }
}
