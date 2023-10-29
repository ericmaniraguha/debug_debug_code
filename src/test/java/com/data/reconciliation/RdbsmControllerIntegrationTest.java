package com.data.reconciliation;

import com.data.reconciliation.controller.EfficientDataRetrievalRdbsmController;
import com.data.reconciliation.entity.EntityRdbms;
import com.data.reconciliation.repository.RdbmsRepository;
import com.data.reconciliation.service.EfficientDataRetrievalRdbmsService;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class RdbsmControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RdbmsRepository rdbmsRepository;

    @Mock
    private EfficientDataRetrievalRdbmsService retrievalService; // Mock the service

    @InjectMocks
    private EfficientDataRetrievalRdbsmController rdbsmController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        // Prepare test data for the repository
        rdbmsRepository.deleteAll();

        List<EntityRdbms> messages = new ArrayList<>();
        messages.add(new EntityRdbms(1L, 1L, "Message 1", LocalDateTime.now()));
        messages.add(new EntityRdbms(2L, 2L, "Message 2", LocalDateTime.now()));
        messages.add(new EntityRdbms(3L, 3L, "Message 3", LocalDateTime.now()));
        rdbmsRepository.saveAll(messages);
    }

    @Test
    public void testGetAllRdbmsMessages() throws Exception {
        // Perform GET request to "/rdbms/all-data"
        mockMvc.perform(MockMvcRequestBuilders.get("/rdbms/all-data"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(Matchers.hasSize(3))) // Verify the response contains 3 items
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L)) // Verify the first item's ID
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2L)) // Verify the second item's ID
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].id").value(3L)); // Verify the third item's ID
    }
}
