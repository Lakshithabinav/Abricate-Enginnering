package com.example.AbricateEngineering.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.AbricateEngineering.entity.DataRecord;

public interface DataRecordRepository extends JpaRepository<DataRecord, LocalDateTime> {
    List<DataRecord> findAllByDateTimeBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);
}
