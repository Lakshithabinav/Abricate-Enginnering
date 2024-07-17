package com.example.AbricateEngineering;



import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataRecordRepository extends JpaRepository<DataRecord,LocalDateTime> {
    // No additional methods needed for basic CRUD operations
}
