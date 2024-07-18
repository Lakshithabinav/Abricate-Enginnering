package com.example.AbricateEngineering.Service;

import org.springframework.stereotype.Service;
import com.example.AbricateEngineering.DAO.DataRecordDAO;
import com.example.AbricateEngineering.DAO.MaterialReport;
import com.example.AbricateEngineering.DAO.RecipeConsompsion;
import com.example.AbricateEngineering.Repository.DataRecordRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class DataRecordService {

    private final DataRecordRepository dataRecordRepository;
    private final DataDAOService dataDAOService;
    private final MaterialReportService materialReportService;

    private List<MaterialReport> materialReports;
    private List<RecipeConsompsion> recipeConsompsions;

    public DataRecordService(DataRecordRepository dataRecordRepository, DataDAOService dataDAOService, MaterialReportService materialReportService) {
        this.dataRecordRepository = dataRecordRepository;
        this.dataDAOService = dataDAOService;
        this.materialReportService = materialReportService;
    }

    public List<MaterialReport> getMaterialReportBtwnReports() {
        List<DataRecordDAO> dataRecordDAOs = dataRecordRepository.findAll().stream()
                .map(dataDAOService::convertToDAO)
                .collect(Collectors.toList());
        materialReports = new ArrayList<>();
        materialReportService.processMaterialReports(dataRecordDAOs, materialReports);
        return materialReports;
    }

    public List<RecipeConsompsion> getRecipeConsompsions() {
        List<DataRecordDAO> dataRecordDAOs = dataRecordRepository.findAll().stream()
                .map(dataDAOService::convertToDAO)
                .collect(Collectors.toList());
        recipeConsompsions = new ArrayList<>();
        dataRecordDAOs.forEach(dataRecordDAO -> processRecipeConsumption(dataRecordDAO, recipeConsompsions));
        return recipeConsompsions;
    }

    private void processRecipeConsumption(DataRecordDAO dataRecordDAO, List<RecipeConsompsion> recipeConsompsions) {
        String formulaName = dataRecordDAO.getFormula();
        if (!isRecipeConsompsion(formulaName, recipeConsompsions)) {
            recipeConsompsions.add(new RecipeConsompsion(formulaName, new ArrayList<>()));
        }
        int index = getIndexValue(formulaName, recipeConsompsions);
        materialReportService.processSingleMaterialReport(dataRecordDAO, recipeConsompsions.get(index).getMaterialReport());
    }

    private boolean isRecipeConsompsion(String formulaName, List<RecipeConsompsion> recipeConsompsions) {
        return recipeConsompsions.stream().anyMatch(recipeConsompsion -> recipeConsompsion.getFormulaName().equals(formulaName));
    }

    private int getIndexValue(String formulaName, List<RecipeConsompsion> recipeConsompsions) {
        return IntStream.range(0, recipeConsompsions.size())
                .filter(i -> recipeConsompsions.get(i).getFormulaName().equals(formulaName))
                .findFirst()
                .orElse(-1);
    }
}
