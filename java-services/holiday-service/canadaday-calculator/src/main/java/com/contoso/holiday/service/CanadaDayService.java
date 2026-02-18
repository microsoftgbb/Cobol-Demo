package com.contoso.holiday.service;

import com.contoso.holiday.model.CanadaDayResponse;
import org.springframework.stereotype.Service;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

/**
 * Service class for Canada Day calculations.
 * Business logic migrated from COBOL AS400 program CANDAY01.CBLLE
 * 
 * Original COBOL logic:
 * - Uses FUNCTION INTEGER-OF-DATE to get day ordinal
 * - Uses FUNCTION MOD to calculate day of week
 * - Adjusts for Sunday=1 (vs COBOL's Monday=1)
 */
@Service
public class CanadaDayService {
    
    private static final String[] DAY_NAMES = {
        "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"
    };
    
    /**
     * Calculate what day of the week Canada Day falls on for a given year.
     * Canada Day is always July 1st.
     * 
     * @param year The year to calculate (1600-3000 per original COBOL validation)
     * @return CanadaDayResponse containing day of week and additional information
     * @throws IllegalArgumentException if year is out of valid range
     */
    public CanadaDayResponse calculateCanadaDay(int year) {
        validateYear(year);
        
        LocalDate canadaDay = LocalDate.of(year, Month.JULY, 1);
        DayOfWeek dayOfWeek = canadaDay.getDayOfWeek();
        String dayName = dayOfWeek.name();
        String formattedDayName = formatDayName(dayName);
        
        boolean isWeekend = (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY);
        String message = buildMessage(year, formattedDayName, dayOfWeek);
        
        return new CanadaDayResponse(year, formattedDayName, message, isWeekend);
    }
    
    /**
     * Validate year is within acceptable range (matching COBOL validation).
     * Original COBOL: IF WS-INPUT-YEAR < 1600 OR WS-INPUT-YEAR > 3000
     */
    private void validateYear(int year) {
        if (year < 1600 || year > 3000) {
            throw new IllegalArgumentException(
                "Year must be between 1600 and 3000 (received: " + year + ")"
            );
        }
    }
    
    /**
     * Format day name with proper capitalization.
     */
    private String formatDayName(String dayName) {
        if (dayName == null || dayName.isEmpty()) {
            return dayName;
        }
        return dayName.substring(0, 1).toUpperCase() + dayName.substring(1).toLowerCase();
    }
    
    /**
     * Build informational message about Canada Day.
     * Mimics the "Fun Facts" section from the original COBOL program.
     */
    private String buildMessage(int year, String dayName, DayOfWeek dayOfWeek) {
        StringBuilder msg = new StringBuilder();
        msg.append("Canada Day (July 1, ").append(year).append(") falls on a ").append(dayName).append(". ");
        
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            msg.append("Canada Day is on a weekend! Perfect for celebrations!");
        } else {
            msg.append("Canada Day is on a weekday - enjoy the long weekend!");
        }
        
        if (dayOfWeek == DayOfWeek.MONDAY) {
            msg.append(" Great way to start the week with a holiday!");
        } else if (dayOfWeek == DayOfWeek.FRIDAY) {
            msg.append(" Fantastic end to the work week!");
        }
        
        return msg.toString();
    }
}
