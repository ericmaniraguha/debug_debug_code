package com.data.reconciliation;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.data.reconciliation.entity.EntityRdbms;
import com.data.reconciliation.repository.RdbmsRepository;
import com.data.reconciliation.service.EfficientDataRetrievalRdbmsService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EfficientDataRetrievalRdbmsServiceTest {
    @Mock
    private RdbmsRepository rdbmsRepository;

    @InjectMocks
    private EfficientDataRetrievalRdbmsService rdbmsService;

    @Before
    public void setUp() {
        // Mock the behavior of the rdbmsRepository.findMaxId() method
        when(rdbmsRepository.findMaxId()).thenReturn(5L); // For testing, assume the maximum ID is 5
    }

//    @Test
//    public void testGetMessagesGreaterThanCurrent() {
//        // Create sample data with IDs greater than the current value (1)
//        List<EntityRdbms> sampleData = new ArrayList<>();
//        sampleData.add(new EntityRdbms(2L, 2L, "Sample Data 2", LocalDateTime.now()));
//        sampleData.add(new EntityRdbms(3L, 3L, "Sample Data 3", LocalDateTime.now()));
//
//        // Mock the behavior of the rdbmsRepository.findByCurrentId() method
//        when(rdbmsRepository.findByCurrentId(1L)).thenReturn(sampleData);
//
//        // Call the method under test
//        List<EntityRdbms> result = rdbmsService.getMessagesGreaterThanCurrent();
//
//        // Assert the result
//        assertEquals(sampleData, result); // Check if the result matches the sample data
//        assertEquals(3L, rdbmsService.getMessagesGreaterThanCurrent()); // Check if the 'current' value is updated to 3L
//        
//    }
    
}
