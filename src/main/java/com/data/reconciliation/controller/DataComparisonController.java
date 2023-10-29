package com.data.reconciliation.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.data.reconciliation.entity.MissingRecordsEntity;
import com.data.reconciliation.service.DataComparisonService;

import java.util.List;

@RestController
@RequestMapping("/compare")
public class DataComparisonController {
    private final DataComparisonService dataComparisonService;

    public DataComparisonController(DataComparisonService dataComparisonService) {
        this.dataComparisonService = dataComparisonService;
    }

    @GetMapping("/missing-records")
    public List<MissingRecordsEntity> getMissingRecords() {
        List<MissingRecordsEntity> missingRecords = dataComparisonService.compareData();
        dataComparisonService.saveMissingRecordsToCSV(missingRecords); // Save missing records to CSV
        return missingRecords;
    }
}