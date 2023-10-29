package com.data.reconciliation;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.data.reconciliation.controller.EfficientDataRetrievalRdbsmController;
import com.data.reconciliation.entity.EntityRdbms;
import com.data.reconciliation.service.EfficientDataRetrievalRdbmsService;

@SpringBootTest
@ActiveProfiles("test")
public class RdbsmControllerComponentTest {

    @Autowired
    private EfficientDataRetrievalRdbsmController controller;

    @MockBean
    private EfficientDataRetrievalRdbmsService retrievalService;

    @Test
    public void testGetAllRdbmsMessages_Success() {
        // Setup
        List<EntityRdbms> messages = new ArrayList<>();
        messages.add(new EntityRdbms(1L, 1L, "Message 1", LocalDateTime.now()));
        messages.add(new EntityRdbms(2L, 2L, "Message 2", LocalDateTime.now()));

        when(retrievalService.getAllMessages()).thenReturn(messages);

        // Run
        List<EntityRdbms> actualMessages = controller.getAllRdbmsMessages();

        // Assert
        assertEquals(messages, actualMessages);
    }

    @Test
    public void testGetMostRecentRdbmsMessages_Success() {
        // Setup
        List<EntityRdbms> messages = new ArrayList<>();
        messages.add(new EntityRdbms(1L, 1L, "Message 1", LocalDateTime.now()));
        messages.add(new EntityRdbms(2L, 2L, "Message 2", LocalDateTime.now()));

        when(retrievalService.getMessagesGreaterThanCurrent()).thenReturn(messages);

        // Run
        List<EntityRdbms> actualMessages = controller.getMostRecentRdbmsMessages();

        // Assert
        assertEquals(messages, actualMessages);
    }
}
