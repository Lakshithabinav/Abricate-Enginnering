package com.example.AbricateEngineering.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.AbricateEngineering.DAO.MaterialReport;
import com.example.AbricateEngineering.DAO.RecipeConsompsion;
import com.example.AbricateEngineering.Service.DataRecordService;
import java.util.List;

@RestController
@RequestMapping("/api/data")
public class DataRecordController {

    private final DataRecordService dataRecordService;

    public DataRecordController(DataRecordService dataRecordService) {
        this.dataRecordService = dataRecordService;
    }

    @GetMapping("/material-report")
    public List<MaterialReport> getFirstFiveRecords() {
        return dataRecordService.getMaterialReportBtwnReports();
    }
    @GetMapping("/recipe-consumptions")
    public List<RecipeConsompsion> getRecipeConsompsions() {
        return dataRecordService.getRecipeConsompsions();
    }
}
