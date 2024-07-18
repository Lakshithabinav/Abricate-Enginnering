package com.example.AbricateEngineering.Service;

import org.springframework.stereotype.Service;
import com.example.AbricateEngineering.DAO.DataRecordDAO;
import com.example.AbricateEngineering.DAO.MaterialReport;
import com.example.AbricateEngineering.Repository.DataRecordRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class DataRecordService {

    private static final int NUMBER_OF_VALUES = 24;

    private final DataRecordRepository dataRecordRepository;
    private final DataDAOService dataDAOService;

    private List<MaterialReport> materialReports = new ArrayList<>();

    public DataRecordService(DataRecordRepository dataRecordRepository, DataDAOService dataDAOService) {
        this.dataRecordRepository = dataRecordRepository;
        this.dataDAOService = dataDAOService;
    }

    public List<MaterialReport> findFirstFiveRecords() {
        List<DataRecordDAO> dataRecordDAOs = dataRecordRepository.findAll().stream()
                .map(dataDAOService::convertToDAO)
                .collect(Collectors.toList());
        materialReports = new ArrayList<>();
        dataRecordDAOs.forEach(this::processDataRecordDAO);

        return materialReports;
    }

    private void processDataRecordDAO(DataRecordDAO dataRecordDAO) {
        IntStream.range(0, NUMBER_OF_VALUES).forEach(i -> {
            String materialName = dataRecordDAO.getNValues()[i];
            Integer achWeight = dataRecordDAO.getTValues()[i];
            Integer setWeight =  dataRecordDAO.getAValues()[i];

            if (materialName != null && achWeight != null && setWeight != null &&(achWeight!=0 || setWeight!=0)) {
                if (materialIsPresent(materialName)) {
                    changesInMeterialRecord(getIndexValue(materialName), achWeight, setWeight);
                } else {
                    addMeterialRecord(materialName, setWeight, achWeight);
                }
            }
        });
    }

    private void changesInMeterialRecord(int index, int achWeight, int setWeight) {
        MaterialReport existingMaterialReport = materialReports.get(index);
        existingMaterialReport.setAchWeight(existingMaterialReport.getAchWeight() + achWeight);
        existingMaterialReport.setSetWeight(existingMaterialReport.getSetWeight() + setWeight);
    }

    private void addMeterialRecord(String name, int setWeight, int achWeight) {
        MaterialReport newMaterialReport = new MaterialReport(name, setWeight, achWeight);
        materialReports.add(newMaterialReport);
    }

    private boolean materialIsPresent(String materialName) {
        return materialReports.stream().anyMatch(mr -> mr.getMaterialName().equals(materialName));
    }

    private int getIndexValue(String materialName) {
        return IntStream.range(0, materialReports.size())
                .filter(i -> materialReports.get(i).getMaterialName().equals(materialName))
                .findFirst()
                .orElse(-1);  // Return -1 if not found, handle this appropriately
    }
}
