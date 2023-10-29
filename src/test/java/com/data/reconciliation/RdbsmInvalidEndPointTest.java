package com.data.reconciliation;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.data.reconciliation.controller.EfficientDataRetrievalRdbsmController;
import com.data.reconciliation.service.EfficientDataRetrievalRdbmsService;

public class RdbsmInvalidEndPointTest {

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
    public void testInvalidURL_GetAllRdbmsMessages() throws Exception {
        // Perform GET request to an invalid URL for /rdbms/all-data
        mockMvc.perform(get("/invalid-url-for-all-data"))
                .andExpect(status().isNotFound());

        // Verify that the retrievalService.getAllMessages() method was not called
        verifyNoInteractions(retrievalService);
    }

    @Test
    public void testInvalidURL_GetMostRecentRdbmsMessages() throws Exception {
        // Perform GET request to an invalid URL for /rdbms/most-recent
        mockMvc.perform(get("/invalid-url-for-most-recent"))
                .andExpect(status().isNotFound());

        // Verify that the retrievalService.getMessagesGreaterThanCurrent() method was not called
        verifyNoInteractions(retrievalService);
    }
}
