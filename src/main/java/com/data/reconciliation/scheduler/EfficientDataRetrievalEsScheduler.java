package com.data.reconciliation.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.data.reconciliation.service.EfficientDataRetrievalEsService;

@Component
public class EfficientDataRetrievalEsScheduler {

    private final EfficientDataRetrievalEsService esService;

    public EfficientDataRetrievalEsScheduler(EfficientDataRetrievalEsService esService) {
        this.esService = esService;
    }

    @Scheduled(fixedRateString = "${scheduler.efficientDataRetrievalEs.fixedRate}") // Schedule this method to run every 5 seconds
    public void scheduledEsDataRetrieval() {
        esService.getMessages();
    }
}
