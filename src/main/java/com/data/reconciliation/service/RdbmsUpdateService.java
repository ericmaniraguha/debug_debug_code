
package com.data.reconciliation.service;

import com.data.reconciliation.entity.EntityRdbms;
import com.data.reconciliation.entity.MissingRecordsEntity;
import com.data.reconciliation.entity.CSVFile;
import com.data.reconciliation.repository.RdbmsRepository;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class RdbmsUpdateService {
    private final RdbmsRepository rdbmsRepository;
    private static final Logger logger = LoggerFactory.getLogger(RdbmsUpdateService.class);
    private final ResourceLoader resourceLoader;
    private final Set<Long> processedIds = new HashSet<>();
    
    private final DataComparisonService dataComparisonService;
   

    

    public RdbmsUpdateService(RdbmsRepository rdbmsRepository, ResourceLoader resourceLoader, DataComparisonService dataComparisonService) {
        this.rdbmsRepository = rdbmsRepository;
        this.resourceLoader = resourceLoader;
        this.dataComparisonService = dataComparisonService;
    }

    /**
     * Update the RDBMS records from a CSV file.
     *
     * @param csvFilePath Path to the CSV file.
     * @param updateOnce  Set to true for updating only once.
     */

//    public void updateRdbmsFromCsv(String csvFilePath, boolean updateOnce) {
//        List<EntityRdbms> jsonRecords = new ArrayList<>();
//        List<EntityRdbms> updatedRecords = new ArrayList<>();
//
//        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
//            String line;
//            while ((line = br.readLine()) != null) {
//                String[] parts = line.split(",");
//                String source = parts[3];
//                if ("Elasticsearch".equalsIgnoreCase(source)) {
//                    Long id = Long.parseLong(parts[0]);
//                    if (!updateOnce || !processedIds.contains(id)) {
//                        processedIds.add(id);
//                        jsonRecords.add(new EntityRdbms(id, parts[1], LocalDateTime.parse(parts[2])));
//                    }
//                }
//            }
//
//            for (EntityRdbms record : jsonRecords) {
//                try {
//                    EntityRdbms updatedRecord = rdbmsRepository.save(record);
//                    updatedRecords.add(updatedRecord);
//                } catch (Exception e) {
//                    logger.error("Error saving record with ID {}: {}", record.getId(), e.getMessage());
//                }
//            }
//            rdbmsRepository.saveAll(updatedRecords);
//
//            // After updating, run the comparison and save missing records to CSV
//            List<MissingRecordsEntity> missingRecords = dataComparisonService.compareData();
//            dataComparisonService.saveMissingRecordsToCSV(missingRecords);
//
//        } catch (IOException e) {
//            logger.error("An error occurred while processing the CSV file: {}", e.getMessage());
//            throw new RuntimeException("Error processing CSV file", e);
//        }
//    }
//

    public void updateRdbmsFromCsv(String csvFilePath, boolean updateOnce) {
        if (csvFilePath == null) {
            logger.error("CSV file path is null.");
            return; // Return or handle this case appropriately
        }

        List<EntityRdbms> jsonRecords = new ArrayList<>();
        List<EntityRdbms> updatedRecords = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String source = parts[3];
                if ("Elasticsearch".equalsIgnoreCase(source)) {
                    Long id = Long.parseLong(parts[0]);
                    if (!updateOnce || !processedIds.contains(id)) {
                        processedIds.add(id);
                        jsonRecords.add(new EntityRdbms(id, parts[1], LocalDateTime.parse(parts[2])));
                    }
                }

            }

            for (EntityRdbms record : jsonRecords) {
                try {
                    EntityRdbms updatedRecord = rdbmsRepository.save(record);
                    updatedRecords.add(updatedRecord);
                } catch (Exception e) {
                    logger.error("Error saving record with ID {}: {}", record.getId(), e.getMessage());
                }
            }
            rdbmsRepository.saveAll(updatedRecords);

            // After updating, run the comparison and save missing records to CSV
            List<MissingRecordsEntity> missingRecords = dataComparisonService.compareData();
            dataComparisonService.saveMissingRecordsToCSV(missingRecords);

        } catch (IOException e) {
            logger.error("An error occurred while processing the CSV file: {}", e.getMessage());
            throw new RuntimeException("Error processing CSV file", e);
        }
    }

//      Run a comparison between different data sources.
    
    public void runComparison() {
    }
    

//     Clean the set of processed IDs.
    
    public void cleanProcessedIds() {
        processedIds.clear();
    }
}