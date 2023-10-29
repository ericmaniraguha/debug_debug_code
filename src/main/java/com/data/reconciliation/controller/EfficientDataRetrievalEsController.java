package com.data.reconciliation.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.data.reconciliation.entity.EntityEs;
import com.data.reconciliation.service.EfficientDataRetrievalEsService;

@RestController
@RequestMapping("elasticsearch")

public class EfficientDataRetrievalEsController {

    private final EfficientDataRetrievalEsService esService;

    public EfficientDataRetrievalEsController(EfficientDataRetrievalEsService esService) {
        this.esService = esService;
    }

    @GetMapping
    public Object getMessages() {
        return esService.getMessages();
    }

}