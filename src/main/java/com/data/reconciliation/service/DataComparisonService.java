package com.data.reconciliation.service;

import com.data.reconciliation.entity.EntityEs;
import com.data.reconciliation.entity.EntityRdbms;
import com.data.reconciliation.entity.MissingRecordsEntity;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DataComparisonService {
    private final EfficientDataRetrievalEsService esService;
    private final EfficientDataRetrievalRdbmsService rdbmsService;

    public DataComparisonService(
            EfficientDataRetrievalEsService esService,
            EfficientDataRetrievalRdbmsService rdbmsService
    ) {
        this.esService = esService;
        this.rdbmsService = rdbmsService;
    }

    public List<MissingRecordsEntity> compareData() {
        List<EntityEs> esMessages = (List<EntityEs>) esService.getMessages();
        List<EntityRdbms> rdbmsMessages = rdbmsService.getAllMessages();

        List<MissingRecordsEntity> missingRecords = new ArrayList<>();

        // Create HashMaps for quick lookup
        Map<String, String> esMessageMap = new HashMap<>();
        for (EntityEs esMessage : esMessages) {
            esMessageMap.put(esMessage.getId(), "Elasticsearch");
        }

        Map<String, String> rdbmsMessageMap = new HashMap<>();
        for (EntityRdbms rdbmsMessage : rdbmsMessages) {
            rdbmsMessageMap.put(String.valueOf(rdbmsMessage.getId()), "RDBMS");
        }

        // Debugging: Print the contents of the HashMaps
        System.out.println("esMessageMap: " + esMessageMap);
        System.out.println("rdbmsMessageMap: " + rdbmsMessageMap);
        
        // Compare the data sources and populate missingRecords list
        for (EntityEs esMessage : esMessages) {
            if (!rdbmsMessageMap.containsKey(esMessage.getId())) {
                MissingRecordsEntity missingRecord = new MissingRecordsEntity();
                missingRecord.setId(esMessage.getId());
                missingRecord.setMsg(esMessage.getMsg());
                missingRecord.setProduced_at(esMessage.getProduced_at());
                missingRecord.setSource("Elasticsearch");
                missingRecords.add(missingRecord);
            }
        }

        for (EntityRdbms rdbmsMessage : rdbmsMessages) {
            if (!esMessageMap.containsKey(String.valueOf(rdbmsMessage.getId()))) {
                MissingRecordsEntity missingRecord = new MissingRecordsEntity();
                missingRecord.setId(String.valueOf(rdbmsMessage.getId()));
                missingRecord.setMsg(rdbmsMessage.getMsg());
                missingRecord.setProduced_at(rdbmsMessage.getProduced_at());
                missingRecord.setSource("RDBMS");
                missingRecords.add(missingRecord);
            }
        }

        return missingRecords;
    }

    public void saveMissingRecordsToCSV(List<MissingRecordsEntity> missingRecords) {
        try (FileWriter csvWriter = new FileWriter("missing_records.csv")) {
            // CSV header
            csvWriter.append("ID");
            csvWriter.append(",");
            csvWriter.append("Message");
            csvWriter.append(",");
            csvWriter.append("Produced_At");
            csvWriter.append(",");
            csvWriter.append("Data_Source");
            csvWriter.append("\n");

            // Write missing records to CSV
            for (MissingRecordsEntity missingRecord : missingRecords) {
                csvWriter.append(missingRecord.getId());
                csvWriter.append(",");
                csvWriter.append(missingRecord.getMsg());
                csvWriter.append(",");
                csvWriter.append(missingRecord.getProduced_at());
                csvWriter.append(",");
                csvWriter.append(missingRecord.getSource());
                csvWriter.append("\n");
            }

            csvWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}