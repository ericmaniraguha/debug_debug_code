package com.data.reconciliation.scheduler;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.data.reconciliation.service.DataUpdateEsService;

@Component
public class DataUpdateEsServiceScheduler {

    private final DataUpdateEsService dataUpdateEsService;

    public DataUpdateEsServiceScheduler(DataUpdateEsService dataUpdateEsService) {
        this.dataUpdateEsService = dataUpdateEsService;
    }

    @Scheduled(fixedRateString = "${scheduler.dataUpdateEsService.fixedRate}") // Schedule this method to run every 1 hour
    public void scheduledUpdateRecords() {
    	System.out.println("The update service of ELK is called.....");
        dataUpdateEsService.updateRecords();
    }
}
