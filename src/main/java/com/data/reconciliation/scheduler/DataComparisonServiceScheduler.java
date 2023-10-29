package com.data.reconciliation.scheduler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.data.reconciliation.service.DataComparisonService;

@Component
public class DataComparisonServiceScheduler {

    private final DataComparisonService dataComparisonService;
    
    @Value("${scheduler.data-comparison.rate}")
    private long dataComparisonRate;

    @Value("${scheduler.save-missing-records.rate}")
    private long saveMissingRecordsRate;

    public DataComparisonServiceScheduler(DataComparisonService dataComparisonService) {
        this.dataComparisonService = dataComparisonService;
    }

    @Scheduled(fixedRateString = "${scheduler.data-comparison.rate}") // Schedule this method to run every 60 seconds
    public void scheduledDataComparison() {
    	System.out.println("Data Comparison is called .....");
        dataComparisonService.compareData();
    }

    @Scheduled(fixedRateString = "${scheduler.save-missing-records.rate}") // Schedule this method to run every 60 seconds
    public void scheduledSaveMissingRecordsToCSV() {
    	System.out.println("Data Are saved in CSV file.....");
        dataComparisonService.saveMissingRecordsToCSV(dataComparisonService.compareData());
    }
}
