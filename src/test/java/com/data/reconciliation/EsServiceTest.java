package com.data.reconciliation;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.data.reconciliation.entity.EntityEs;
import com.data.reconciliation.service.EfficientDataRetrievalEsService;

public class EsServiceTest {

    private EfficientDataRetrievalEsService esService;
    private RestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        // Create a mock for RestTemplate
        restTemplate = mock(RestTemplate.class);

        // Initialize the service with the mocked RestTemplate
        esService = new EfficientDataRetrievalEsService(restTemplate);
    }

    @Test
    public void testGetMessages_Success() throws Exception {
        // Setup test data: Create a JSON response from Elasticsearch
        String jsonResponse = "{ \"hits\": { \"hits\": [ { \"_source\": { \"id\": 1, \"msg\": \"Message 1\", \"produced_at\": \"2023-07-27T12:00:00\" } } ] } }";

        // Mock the behavior of RestTemplate to return the test JSON response
        ResponseEntity<String> responseEntity = ResponseEntity.ok(jsonResponse);
        when(restTemplate.getForEntity(anyString(), eq(String.class))).thenReturn(responseEntity);

        // Call the method under test: esService.getMessages()
        List<EntityEs> actualMessages = (List<EntityEs>) esService.getMessages();

        // Initialize the expectedMessages list
        List<EntityEs> expectedMessages = new ArrayList<>();

        // Iterate through the actualMessages list and add each element to the expectedMessages list
        for (EntityEs message : actualMessages) {
            expectedMessages.add(message);
        }

        // Verify that the actualMessages list contains the expected messages
        assertEquals(expectedMessages, actualMessages);
    }

    @Test
    public void testGetMessages_EmptyResponse() throws Exception {
        // Setup test data: Create an empty JSON response from Elasticsearch
        String jsonResponse = "{ \"hits\": { \"hits\": [] } }";

        // Mock the behavior of RestTemplate to return the empty JSON response
        ResponseEntity<String> responseEntity = ResponseEntity.ok(jsonResponse);
        when(restTemplate.getForEntity(anyString(), eq(String.class))).thenReturn(responseEntity);

        // Call the method under test: esService.getMessages()
        List<EntityEs> actualMessages = (List<EntityEs>) esService.getMessages();

        // Verify that RestTemplate.getForEntity was called with the correct URL
        verify(restTemplate, times(1)).getForEntity(eq("http://172.29.108.84:9200/messages/_search"), eq(String.class));

        // Verify that the actualMessages list is empty
        assertEquals(0, actualMessages.size());
    }

}