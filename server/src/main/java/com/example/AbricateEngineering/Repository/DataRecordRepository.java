package com.example.AbricateEngineering.Repository;



import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.AbricateEngineering.entity.DataRecord;

@Repository
public interface DataRecordRepository extends JpaRepository<DataRecord,LocalDateTime> {
    // No additional methods needed for basic CRUD operations
}
