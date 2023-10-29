package com.data.reconciliation.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.data.reconciliation.entity.EntityEs;

@RunWith(MockitoJUnitRunner.class)
public class EfficientDataRetrievalEsServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private EfficientDataRetrievalEsService esService;

    private static final String ES_API_URL = "http://172.29.108.84:9200/messages/_search";

    @Before
    public void setUp() {
        // Mock updateLastIdInFile to avoid file writing during tests
    }

    @Test
    public void testGetMessagesWhenFiltering() {
        // Create sample JSON response from Elasticsearch API
        String jsonResponse = "{\"hits\":{\"hits\":[{\"_source\":{\"id\":2,\"msg\":\"Sample Data 2\",\"produced_at\":\"2023-08-07 12:34:56.789012\"}}]}}";
        ResponseEntity<String> responseEntity = new ResponseEntity<>(jsonResponse, HttpStatus.OK);

        // Mock the response from the Elasticsearch API
        when(restTemplate.getForEntity(ES_API_URL, String.class)).thenReturn(responseEntity);

        // Mock the behavior of file I/O methods
        when(esService.readLastIdFromFile()).thenReturn(1); // For testing, assume the lastId is 1

        // Call the method under test
        List<EntityEs> messages = esService.getMessages();

        // Assert the result
        assertEquals(1, messages.size());
        EntityEs entity = messages.get(0);
        assertEquals("2", entity.getId());
        assertEquals("Sample Data 2", entity.getMsg());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
        LocalDateTime expectedDate = LocalDateTime.parse("2023-08-07 12:34:56.789012", formatter);
        assertEquals(expectedDate, entity.getProduced_at());
    }

    @Test
    public void testGetMessagesWhenNotFiltering() {
        // Create sample JSON response from Elasticsearch API
        String jsonResponse = "{\"hits\":{\"hits\":[{\"_source\":{\"id\":1,\"msg\":\"Sample Data 1\",\"produced_at\":\"2023-08-07 12:34:56.789012\"}}]}}";
        ResponseEntity<String> responseEntity = new ResponseEntity<>(jsonResponse, HttpStatus.OK);

        // Mock the response from the Elasticsearch API
        when(restTemplate.getForEntity(ES_API_URL, String.class)).thenReturn(responseEntity);

        // Mock the behavior of file I/O methods
        when(esService.readLastIdFromFile()).thenReturn(1); // For testing, assume the lastId is 1

        // Call the method under test
        List<EntityEs> messages = esService.getMessages();

        // Assert the result
        assertEquals(0, messages.size()); // No messages should be added as all IDs in the sample JSON response are equal to lastId (1)
    }

    @Test
    public void testGetMessagesWhenFilteringWithIdsGreaterThanLastId() {
        // Create sample JSON response from Elasticsearch API
        String jsonResponse = "{\"hits\":{\"hits\":[{\"_source\":{\"id\":2,\"msg\":\"Sample Data 2\",\"produced_at\":\"2023-08-07 12:34:56.789012\"}}]}}";
        ResponseEntity<String> responseEntity = new ResponseEntity<>(jsonResponse, HttpStatus.OK);

        // Mock the response from the Elasticsearch API
        when(restTemplate.getForEntity(ES_API_URL, String.class)).thenReturn(responseEntity);

        // Mock the behavior of file I/O methods
        when(esService.readLastIdFromFile()).thenReturn(1); // For testing, assume the lastId is 1

        // Call the method under test
        List<EntityEs> messages = esService.getMessages();

        // Assert the result
        assertEquals(1, messages.size());
        EntityEs entity = messages.get(0);
        assertEquals("2", entity.getId());
        assertEquals("Sample Data 2", entity.getMsg());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
        LocalDateTime expectedDate = LocalDateTime.parse("2023-08-07 12:34:56.789012", formatter);
        assertEquals(expectedDate, entity.getProduced_at());
    }

    @Test
    public void testGetMessagesWhenFilteringWithIdsLessThanLastId() {
        // Create sample JSON response from Elasticsearch API
        String jsonResponse = "{\"hits\":{\"hits\":[{\"_source\":{\"id\":1,\"msg\":\"Sample Data 1\",\"produced_at\":\"2023-08-07 12:34:56.789012\"}}]}}";
        ResponseEntity<String> responseEntity = new ResponseEntity<>(jsonResponse, HttpStatus.OK);

        // Mock the response from the Elasticsearch API
        when(restTemplate.getForEntity(ES_API_URL, String.class)).thenReturn(responseEntity);

        // Mock the behavior of file I/O methods
        when(esService.readLastIdFromFile()).thenReturn(3); // For testing, assume the lastId is 3

        // Call the method under test
        List<EntityEs> messages = esService.getMessages();

        // Assert the result
        assertEquals(0, messages.size()); // No messages should be added as all IDs in the sample JSON response are less than or equal to lastId (3)
    }
}
