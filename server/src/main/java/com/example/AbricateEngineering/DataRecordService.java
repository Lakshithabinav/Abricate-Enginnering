package com.example.AbricateEngineering;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DataRecordService {

    private final DataRecordRepository dataRecordRepository;

    @Autowired
    public DataRecordService(DataRecordRepository dataRecordRepository) {
        this.dataRecordRepository = dataRecordRepository;
    }

    public List<DataRecord> findFirstFiveRecords() {
        return dataRecordRepository.findAll(PageRequest.of(0, 5)).getContent();
    }
}
