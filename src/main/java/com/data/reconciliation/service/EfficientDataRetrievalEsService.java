package com.data.reconciliation.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.data.reconciliation.entity.EntityEs;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;
import jakarta.json.JsonArray;
import jakarta.json.JsonNumber;

@Service
public class EfficientDataRetrievalEsService {
    public final String ES_API_URL = "http://172.29.108.84:9200/messages/_search";
    private final RestTemplate restTemplate;

    public EfficientDataRetrievalEsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<EntityEs> getMessages() {
        List<EntityEs> messages = new ArrayList<>();
        int lastId = readLastIdFromFile();

        try {
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(ES_API_URL, String.class);
            String response = responseEntity.getBody();
            JsonObject jsonObject = parseJsonResponse(response);

            if (jsonObject != null) { // Check if jsonObject is not null
                JsonObject hitsObject = jsonObject.getJsonObject("hits");
                
                if (hitsObject != null) { // Check if hitsObject is not null
                    JsonArray hitsArray = hitsObject.getJsonArray("hits");

                    // Create a PriorityQueue to keep track of the top IDs
                    PriorityQueue<Integer> queue = new PriorityQueue<>((a, b) -> Integer.compare(b, a));

                    for (JsonValue hitValue : hitsArray) {
                        if (hitValue.getValueType() == JsonValue.ValueType.OBJECT) {
                            JsonObject hitObject = (JsonObject) hitValue;
                            JsonObject sourceObject = hitObject.getJsonObject("_source");
                            JsonNumber idJsonNumber = sourceObject.getJsonNumber("id");
                            
                            if (idJsonNumber != null) { // Check if idJsonNumber is not null
                                int messageId = idJsonNumber.intValue();
                        
                                if (messageId > lastId) {
                                    queue.add(messageId);
                                }
                            } else {
                                System.err.println("The 'id' field is null for a message.");
                            }
                        } else {
                            System.err.println("Invalid data in hitsArray: " + hitValue);
                        }
                    }

                    while (!queue.isEmpty()) {
                        int messageId = queue.poll();
                        if (messageId > lastId && messageId <= hitsArray.size()) {
                            JsonObject sourceObject = hitsArray.getJsonObject(messageId - 1).getJsonObject("_source");

                            EntityEs message = new EntityEs();
                            message.setId(String.valueOf(messageId));
                            message.setMsg(sourceObject.getString("msg"));
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
                            LocalDateTime date = LocalDateTime.parse(sourceObject.getString("produced_at"), formatter);
                            message.setProduced_at(date);

                            messages.add(message);

                            lastId = messageId;
                        }
                    }

                    // Update the text file "last_id.txt" with the new biggest ID
                    updateLastIdInFile(lastId);
                } else {
                    System.err.println("Received null hitsObject");
                }
            } else {
                System.err.println("Received null jsonObject");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return messages;
    }

    private JsonObject parseJsonResponse(String response) {
        try (JsonReader reader = Json.createReader(new StringReader(response))) {
            return reader.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int readLastIdFromFile() {
        int lastId = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("last_id.txt"))) {
            String line = br.readLine();
            if (line != null) {
                lastId = Integer.parseInt(line.trim());
            }
        } catch (FileNotFoundException e) {
            // Create the file with the default initial value (e.g., 0)
            updateLastIdInFile(lastId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lastId;
    }

    private void updateLastIdInFile(int newLastId) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("last_id.txt"))) {
            bw.write(String.valueOf(newLastId));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getLastId() {
        return readLastIdFromFile();
    }

    public List<EntityEs> getMessagesGreaterThanId(long thresholdId) {
        // TODO: Implement this method
        return null;
    }
}
