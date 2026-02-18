package com.contoso.holiday.controller;

import com.contoso.holiday.model.CanadaDayResponse;
import com.contoso.holiday.service.CanadaDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * REST controller for Canada Day calculations.
 * Provides HTTP API for the functionality from COBOL AS400 program CANDAY01.CBLLE
 */
@RestController
@RequestMapping("/api/v1/canada-day")
public class CanadaDayController {
    
    private final CanadaDayService canadaDayService;
    
    @Autowired
    public CanadaDayController(CanadaDayService canadaDayService) {
        this.canadaDayService = canadaDayService;
    }
    
    /**
     * Calculate what day of the week Canada Day falls on for a given year.
     * 
     * @param year The year to calculate (1600-3000)
     * @return Response containing day of week and additional information
     * 
     * Example: GET /api/v1/canada-day/2024
     * Response: {"year":2024,"dayOfWeek":"Monday","message":"...","weekend":false}
     */
    @GetMapping("/{year}")
    public ResponseEntity<?> getCanadaDay(@PathVariable int year) {
        try {
            CanadaDayResponse response = canadaDayService.calculateCanadaDay(year);
            return ResponseEntity.ok(response);
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
     * Health check endpoint.
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "Canada Day Calculator");
        response.put("migrated_from", "COBOL AS400 CANDAY01.CBLLE");
        return ResponseEntity.ok(response);
    }
}
