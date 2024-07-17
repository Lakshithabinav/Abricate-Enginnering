package com.example.AbricateEngineering;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/data")
public class DataRecordController {

    private final DataRecordService dataRecordService;

    @Autowired
    public DataRecordController(DataRecordService dataRecordService) {
        this.dataRecordService = dataRecordService;
    }

    @GetMapping("/first-five")
    public List<DataRecord> getFirstFiveRecords() {
        return dataRecordService.findFirstFiveRecords();
    }
}
