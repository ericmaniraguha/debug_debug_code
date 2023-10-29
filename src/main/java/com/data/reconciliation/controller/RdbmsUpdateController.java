//package com.data.reconciliation.controller;
//
//import com.data.reconciliation.service.RdbmsUpdateService;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/rdbms")
//public class RdbmsUpdateController {
//
//    private final RdbmsUpdateService rdbmsUpdateService;
//
//    public RdbmsUpdateController(RdbmsUpdateService rdbmsUpdateService) {
//        this.rdbmsUpdateService = rdbmsUpdateService;
//    }
//
//    @PostMapping("/update-from-csv")
//    public ResponseEntity<String> updateRdbmsFromCsv() {
//        try {
//            rdbmsUpdateService.updateRdbmsFromCsv(true); // Set to true for updating only once
//            // Run the comparison method after updating
//            rdbmsUpdateService.runComparison();
//            // Clean processed IDs
//            rdbmsUpdateService.cleanProcessedIds();
//            return ResponseEntity.ok("RDBMS records updated from CSV and processed data cleaned.");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating RDBMS from CSV: " + e.getMessage());
//        }
//    }
//}

package com.data.reconciliation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.data.reconciliation.entity.CSVFile;
import com.data.reconciliation.service.RdbmsUpdateService;

@RestController
public class RdbmsUpdateController {
    private final RdbmsUpdateService rdbmsUpdateService;

    public RdbmsUpdateController(RdbmsUpdateService rdbmsUpdateService) {
        this.rdbmsUpdateService = rdbmsUpdateService;
    }

    @PostMapping("/rdbms/update-from-csv")
    public ResponseEntity<String> updateRdbmsFromCsv(@RequestBody CSVFile csvFile) {
        String csvFilePath = csvFile.getPath();
        try {
            rdbmsUpdateService.updateRdbmsFromCsv(csvFilePath, true); // Set to true for updating only once
            // Run the comparison method after updating
            rdbmsUpdateService.runComparison();
            // Clean processed IDs
            rdbmsUpdateService.cleanProcessedIds();
            return ResponseEntity.ok("RDBMS records updated from CSV and proceed data cleaned.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating RDBMS from CSV: " + e.getMessage());
        }
    }
}
