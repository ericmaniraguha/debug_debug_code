package com.data.reconciliation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

//import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;


@Service
public class DataUpdateEsService {

    private final RestTemplate restTemplate;
    
    private final String elasticsearchBaseUrl = "http://172.29.108.84:9200/messages"; // Elasticsearch base URL

    @Autowired
    public DataUpdateEsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void updateRecords() {
    	// Retrieve the list of missing records from CSV or any source you have
    	List<Map<String, Object>> missingRecords = readMissingRecordsFromCsv(); // Implement this method
    
    	String indexName = "messages";

        for (Map<String, Object> data : missingRecords) {
            String dataSource = (String) data.get("Data Source");
            if ("RDBMS".equals(dataSource)) {
                String id = (String) data.get("ID");
                String updateUrl = elasticsearchBaseUrl + "/_doc/" + id;

                // Prepare the script for adding missing data
                String msg = (String) data.get("Message");
                String producedAt = (String) data.get("Produced At");
                String script = "ctx._source.id = params.id; ctx._source.msg = params.msg; ctx._source.produced_at = params.produced_at";
                Map<String, Object> params = Map.of(
                    "id", id,
                    "msg", msg,
                    "produced_at", producedAt
                );

                // Construct the request body
                String requestBody = "{"
                    + "\"script\": {"
                    + "\"source\": \"" + script + "\","
                    + "\"lang\": \"painless\","
                    + "\"params\": " + convertDataToJson(params)
                    + "}"
                    + "}";

                // Set headers for JSON content
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);

                // Create an HTTP entity with headers and body
                HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

                System.out.println("before post");
                // Send the POST request for partial update (upsert)
                ResponseEntity<String> response = restTemplate.postForEntity(
                    updateUrl,
                    entity,
                    String.class
                );
 
                HttpStatusCode status = response.getStatusCode();
                if (status.is2xxSuccessful()) {
                    System.out.println("Data update successful for ID: " + id);
                } else {
                    System.out.println("Data update failed for ID: " + id);
                    System.out.println("Response body: " + response.getBody());
                }
            }
        }
    }
    
    private List<Map<String, Object>> readMissingRecordsFromCsv() {
        List<Map<String, Object>> missingRecords = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("missing_records.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                Map<String, Object> record = Map.of(
                    "ID", values[0],
                    "Message", values[1],
                    "Produced_At", values[2],
                    "Data_Source", values[3]
                );
                missingRecords.add(record);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return missingRecords;
    }

    //convert data to Json format
    private String convertDataToJson(Map<String, Object> data) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(data);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    

}