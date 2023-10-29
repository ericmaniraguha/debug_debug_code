package com.data.reconciliation.scheduler;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.data.reconciliation.service.EfficientDataRetrievalRdbmsService;

@Component
public class EfficientDataRetrievalRdbmsScheduler {

    private final EfficientDataRetrievalRdbmsService rdbmsService;

    public EfficientDataRetrievalRdbmsScheduler(EfficientDataRetrievalRdbmsService rdbmsService) {
        this.rdbmsService = rdbmsService;
    }

    @Scheduled(fixedRateString = "${scheduler.efficientDataRetrievalRdbms.fixedRate}") // Schedule this method to run every 10 seconds
    public void scheduledRdbmsDataRetrieval() {
        rdbmsService.getMessagesGreaterThanCurrent();
    }
}
