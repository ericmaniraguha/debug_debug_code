package com.data.reconciliation.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.data.reconciliation.entity.EntityRdbms;
import com.data.reconciliation.service.EfficientDataRetrievalRdbmsService;

@RestController
@RequestMapping("/")
public class EfficientDataRetrievalRdbsmController {
    private final EfficientDataRetrievalRdbmsService retrievalService;

    public EfficientDataRetrievalRdbsmController(EfficientDataRetrievalRdbmsService retrievalService) {
        this.retrievalService = retrievalService;
    }
    @GetMapping("/rdbms/all-data")
    public List<EntityRdbms> getAllRdbmsMessages() {
        List<EntityRdbms> allMessages = retrievalService.getAllMessages();
        return allMessages;
    }
    @GetMapping("/rdbms/most-recent")
    public List<EntityRdbms> getMostRecentRdbmsMessages() {
        List<EntityRdbms> messagesGreaterThanCurrent = retrievalService.getMessagesGreaterThanCurrent();
        return messagesGreaterThanCurrent;
    }
}