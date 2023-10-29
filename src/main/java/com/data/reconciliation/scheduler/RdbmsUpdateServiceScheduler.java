package com.data.reconciliation.scheduler;

//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import com.data.reconciliation.service.RdbmsUpdateService;
//
//@Component
//public class RdbmsUpdateServiceScheduler {
//
//    private final RdbmsUpdateService rdbmsUpdateService;
//
//    public RdbmsUpdateServiceScheduler(RdbmsUpdateService rdbmsUpdateService) {
//        this.rdbmsUpdateService = rdbmsUpdateService;
//    }
//
//    @Scheduled(fixedRate = 86400000) // Schedule this method to run every 24 hours
//    public void scheduledUpdateRdbmsFromCsv() {
//        rdbmsUpdateService.updateRdbmsFromCsv("path_to_your_csv_file.csv", false);
//    }
//}


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.data.reconciliation.service.RdbmsUpdateService;

@Component
public class RdbmsUpdateServiceScheduler {
    private final RdbmsUpdateService rdbmsUpdateService;

    public RdbmsUpdateServiceScheduler(RdbmsUpdateService rdbmsUpdateService) {
        this.rdbmsUpdateService = rdbmsUpdateService;
    }

    @Scheduled(fixedRateString = "${scheduler.rdbmsUpdateService.updateRdbmsFromCsv.fixedRate}")
    public void updateRdbmsFromCsvScheduled() {
        rdbmsUpdateService.updateRdbmsFromCsv(null, false); // Call the version without parameters
    }

    @Scheduled(fixedRateString = "${scheduler.rdbmsUpdateService.runComparison.fixedRate}")
    public void runComparisonScheduled() {
    	System.out.println("The comparison service is called to update the rdbms.....");
        rdbmsUpdateService.runComparison();
    }
}

