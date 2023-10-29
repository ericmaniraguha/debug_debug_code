package com.data.reconciliation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.data.reconciliation.controller.EfficientDataRetrievalRdbsmController;
import com.data.reconciliation.entity.EntityRdbms;
import com.data.reconciliation.service.EfficientDataRetrievalRdbmsService;

public class RdbsmControllerTest {

    // Mock the EfficientDataRetrievalRdbmsService
    @Mock
    private EfficientDataRetrievalRdbmsService retrievalService;

    // Create an instance of the EfficientDataRetrievalRdbsmController and inject the mocks
    @InjectMocks
    private EfficientDataRetrievalRdbsmController controller;

    @BeforeEach
    public void setUp() {
        // Initialize the mocks before each test method
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllRdbmsMessages_Success() {
        // Setup test data: Create a list of EntityRdbms objects
        List<EntityRdbms> messages = new ArrayList<>();
        messages.add(new EntityRdbms(1L, 1L, "Message 1", LocalDateTime.now()));
        messages.add(new EntityRdbms(2L, 2L, "Message 2", LocalDateTime.now()));

        // Mock the behavior of retrievalService.getAllMessages() to return the test data list
        when(retrievalService.getAllMessages()).thenReturn(messages);

        // Call the method under test: controller.getAllRdbmsMessages()
        List<EntityRdbms> actualMessages = controller.getAllRdbmsMessages();

        // Assert that the result matches the test data
        assertEquals(messages, actualMessages);

        // Verify that retrievalService.getAllMessages() was called exactly once
        verify(retrievalService, times(1)).getAllMessages();
    }

    @Test
    public void testGetMostRecentRdbmsMessages_Success() {
        // Setup test data: Create a list of EntityRdbms objects
        List<EntityRdbms> messages = new ArrayList<>();
        messages.add(new EntityRdbms(1L, 1L, "Message 1", LocalDateTime.now()));
        messages.add(new EntityRdbms(2L, 2L, "Message 2", LocalDateTime.now()));

        // Mock the behavior of retrievalService.getMessagesGreaterThanCurrent() to return the test data list
        when(retrievalService.getMessagesGreaterThanCurrent()).thenReturn(messages);

        // Call the method under test: controller.getMostRecentRdbmsMessages()
        List<EntityRdbms> actualMessages = controller.getMostRecentRdbmsMessages();

        // Assert that the result matches the test data
        assertEquals(messages, actualMessages);

        // Verify that retrievalService.getMessagesGreaterThanCurrent() was called exactly once
        verify(retrievalService, times(1)).getMessagesGreaterThanCurrent();
    }
}
