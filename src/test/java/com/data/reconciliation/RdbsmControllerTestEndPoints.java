package com.data.reconciliation;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.data.reconciliation.controller.EfficientDataRetrievalRdbsmController;
import com.data.reconciliation.entity.EntityRdbms;
import com.data.reconciliation.service.EfficientDataRetrievalRdbmsService;

public class RdbsmControllerTestEndPoints {

    @Mock
    private EfficientDataRetrievalRdbmsService retrievalService;

    @InjectMocks
    private EfficientDataRetrievalRdbsmController controller;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testGetAllRdbmsMessages_Success() throws Exception {
        // Setup
        List<EntityRdbms> messages = new ArrayList<>();
        messages.add(new EntityRdbms(1L, 1L, "Message 1", LocalDateTime.now()));
        messages.add(new EntityRdbms(2L, 2L, "Message 2", LocalDateTime.now()));

        when(retrievalService.getAllMessages()).thenReturn(messages);

        // Perform GET request to "/rdbms/all-data"
        mockMvc.perform(get("/rdbms/all-data"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].msg", is("Message 1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].msg", is("Message 2")));

        // Verify that the retrievalService.getAllMessages() method was called once
        verify(retrievalService, times(1)).getAllMessages();
    }

    @Test
    public void testGetMostRecentRdbmsMessages_Success() throws Exception {
        // Setup
        List<EntityRdbms> messages = new ArrayList<>();
        messages.add(new EntityRdbms(1L, 1L, "Message 1", LocalDateTime.now()));
        messages.add(new EntityRdbms(2L, 2L, "Message 2", LocalDateTime.now()));

        when(retrievalService.getMessagesGreaterThanCurrent()).thenReturn(messages);

        // Perform GET request to "/rdbms/most-recent"
        mockMvc.perform(get("/rdbms/most-recent"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].msg", is("Message 1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].msg", is("Message 2")));

        // Verify that the retrievalService.getMessagesGreaterThanCurrent() method was called once
        verify(retrievalService, times(1)).getMessagesGreaterThanCurrent();
    }
}
