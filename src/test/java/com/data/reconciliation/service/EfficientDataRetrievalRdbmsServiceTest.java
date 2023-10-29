package com.data.reconciliation.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataRetrievalFailureException;

import com.data.reconciliation.entity.EntityRdbms;
import com.data.reconciliation.exception.InternalServerErrorException;
import com.data.reconciliation.repository.RdbmsRepository;

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

    @Test
    public void testGetMessagesGreaterThanCurrentWithData() {
        // Mock the behavior of the rdbmsRepository.findByCurrentId() method with some data
        when(rdbmsRepository.findByCurrentId(5L)).thenReturn(getSampleEntityRdbmsList()); // Change 5L based on your test data

        // Call the method under test
        List<EntityRdbms> result = rdbmsService.getMessagesGreaterThanCurrent();

        // Assert the result
        assertEquals(getSampleEntityRdbmsList(), result); // Check if the result matches the sample data
    }

    @Test
    public void testGetMessagesGreaterThanCurrentWithEmptyData() {
        // Mock the behavior of the rdbmsRepository.findByCurrentId() method with an empty list
        when(rdbmsRepository.findByCurrentId(5L)).thenReturn(new ArrayList<>());

        // Call the method under test
        List<EntityRdbms> result = rdbmsService.getMessagesGreaterThanCurrent();

        // Assert the result
        assertEquals(0, result.size()); // Ensure the result is an empty list
    }

    @Test(expected = InternalServerErrorException.class)
    public void testGetMessagesGreaterThanCurrentWithException() {
        // Mock the behavior of the rdbmsRepository.findByCurrentId() method to throw a DataRetrievalFailureException
        when(rdbmsRepository.findByCurrentId(5L)).thenThrow(DataRetrievalFailureException.class);

        // Call the method under test
        rdbmsService.getMessagesGreaterThanCurrent();
    }

    // Helper method to provide sample data for testing
    private List<EntityRdbms> getSampleEntityRdbmsList() {
        // Create and return a list of EntityRdbms objects with dummy data for testing
        // Modify this method based on your test data requirements
        List<EntityRdbms> sampleList = new ArrayList<>();
        sampleList.add(new EntityRdbms(6L, 6L, "Sample Data 1", LocalDateTime.now()));
        sampleList.add(new EntityRdbms(7L, 7L, "Sample Data 2", LocalDateTime.now()));
        return sampleList;
    }
}
