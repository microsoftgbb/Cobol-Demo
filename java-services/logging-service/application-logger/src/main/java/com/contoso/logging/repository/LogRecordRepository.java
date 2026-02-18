package com.contoso.logging.repository;

import com.contoso.logging.model.LogRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

/**
 * Repository interface for LogRecord entity.
 * Provides database access for log records (LOGP0 physical file in COBOL).
 */
@Repository
public interface LogRecordRepository extends JpaRepository<LogRecord, Long> {
    
    /**
     * Find all log records for a specific date.
     */
    List<LogRecord> findByLogDate(LocalDate date);
    
    /**
     * Find all log records for a specific user.
     */
    List<LogRecord> findByUserName(String userName);
    
    /**
     * Find all log records for a specific job.
     */
    List<LogRecord> findByJobName(String jobName);
}
