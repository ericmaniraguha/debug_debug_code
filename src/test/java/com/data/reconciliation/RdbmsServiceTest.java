package com.data.reconciliation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataRetrievalFailureException;

import com.data.reconciliation.entity.EntityRdbms;
import com.data.reconciliation.exception.InternalServerErrorException;
import com.data.reconciliation.repository.RdbmsRepository;
import com.data.reconciliation.service.EfficientDataRetrievalRdbmsService;

public class RdbmsServiceTest {

	// Mock the RdbmsRepository
    @Mock
    private RdbmsRepository rdbmsRepository;

    // Create an instance of the EfficientDataRetrievalRdbmsService and inject the mocks
    @InjectMocks
    private EfficientDataRetrievalRdbmsService service;

    @BeforeEach
    public void setUp() {
        // Initialize the mocks before each test method
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllMessages_Success() {
        // Setup test data: Create a list of EntityRdbms objects
        List<EntityRdbms> messages = new ArrayList<>();
        messages.add(new EntityRdbms(1L, 1L, "Message 1", LocalDateTime.now()));
        messages.add(new EntityRdbms(2L, 2L, "Message 2", LocalDateTime.now()));

        // Mock the behavior of rdbmsRepository.findAll() to return the test data list
        when(rdbmsRepository.findAll()).thenReturn(messages);

        // Call the method under test: service.getAllMessages()
        List<EntityRdbms> actualMessages = service.getAllMessages();

        // Assert that the result matches the test data
        assertEquals(messages, actualMessages);

        // Verify that rdbmsRepository.findAll() was called exactly once
        verify(rdbmsRepository, times(1)).findAll();
    }

    @Test
    public void testGetAllMessages_DataRetrievalFailure() {
        // Setup: Mock the behavior of rdbmsRepository.findAll() to throw a DataRetrievalFailureException
        when(rdbmsRepository.findAll()).thenThrow(new DataRetrievalFailureException("Data retrieval failure"));

        // Run and Assert: Ensure that service.getAllMessages() throws an InternalServerErrorException
        assertThrows(InternalServerErrorException.class, () -> service.getAllMessages());

        // Verify that rdbmsRepository.findAll() was called exactly once
        verify(rdbmsRepository, times(1)).findAll();
    }
    @Test
    public void testGetMessagesGreaterThanCurrent_Success() {
        // Setup test data: Create a list of EntityRdbms objects
        List<EntityRdbms> messages = new ArrayList<>();
        messages.add(new EntityRdbms(3L, 3L, "Message 3", LocalDateTime.now()));
        messages.add(new EntityRdbms(4L, 4L, "Message 4", LocalDateTime.now()));

        // Mock the behavior of rdbmsRepository.findByCurrentId() to return the test data list
        when(rdbmsRepository.findByCurrentId(0L)).thenReturn(messages);

        // Call the method under test: service.getMessagesGreaterThanCurrent()
        List<EntityRdbms> actualMessages = service.getMessagesGreaterThanCurrent();

        // Assert that the result matches the test data
        assertEquals(messages, actualMessages);

        // Verify that rdbmsRepository.findByCurrentId() was called exactly once
        verify(rdbmsRepository, times(1)).findByCurrentId(0L);
    }

    private long current = 0L;

    @Test
    public void testGetMessagesGreaterThanCurrent_UpdatesCurrent() {
        // Setup test data: Create a list of EntityRdbms objects
        List<EntityRdbms> messages = new ArrayList<>();
        messages.add(new EntityRdbms(3L, 3L, "Message 3", LocalDateTime.now()));
        messages.add(new EntityRdbms(4L, 4L, "Message 4", LocalDateTime.now()));

        // Mock the behavior of rdbmsRepository.findByCurrentId() to return the test data list
        when(rdbmsRepository.findByCurrentId(0L)).thenReturn(messages);

        // Call the method under test: service.getMessagesGreaterThanCurrent()
        service.getMessagesGreaterThanCurrent();

        // Update the current variable to the ID of the most recent message
        current = messages.get(messages.size() - 1).getId();

        // Assert that the current variable is updated to the ID of the most recent message
        assertEquals(4L, current);
    }


    public List<EntityRdbms> getMessagesGreaterThanCurrent() {
        List<EntityRdbms> messages = rdbmsRepository.findByCurrentId(current);
        if (!messages.isEmpty()) {
            EntityRdbms mostRecentMessage = messages.get(messages.size() - 1);
            current = mostRecentMessage.getId();
        }

        return messages;
    }


}
