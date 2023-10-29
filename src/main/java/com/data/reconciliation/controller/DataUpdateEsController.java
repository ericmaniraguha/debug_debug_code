package com.data.reconciliation.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.data.reconciliation.service.DataUpdateEsService;

@RestController
@RequestMapping("/update")
public class DataUpdateEsController {
    private final DataUpdateEsService dataUpdateEsService;

    public DataUpdateEsController(DataUpdateEsService dataUpdateEsService) {
        this.dataUpdateEsService = dataUpdateEsService;
    }

    @GetMapping("/esrecords")
    public String updateMissingRecords() {
        dataUpdateEsService.updateRecords(); // Call the update method
        System.out.println("update method");
        return "Update initiated";
    }
    
}
