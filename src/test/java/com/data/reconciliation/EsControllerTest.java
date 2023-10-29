package com.data.reconciliation;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.data.reconciliation.controller.EfficientDataRetrievalEsController;
import com.data.reconciliation.entity.EntityEs;
import com.data.reconciliation.service.EfficientDataRetrievalEsService;

@WebMvcTest(EfficientDataRetrievalEsController.class)
public class EsControllerTest {

    @MockBean
    private EfficientDataRetrievalEsService esService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        // Initialize the MockMvc instance
        mockMvc = MockMvcBuilders.standaloneSetup(new EfficientDataRetrievalEsController(esService)).build();
    }
//   Tests for the getMessages() endpoint:
    @Test
    public void testGetMessages_Success() throws Exception {
        // Setup test data: Create a list of EntityEs objects
        List<EntityEs> messages = new ArrayList<>();
        messages.add(new EntityEs("1", "Message 1", LocalDateTime.now()));
        messages.add(new EntityEs("2", "Message 2", LocalDateTime.now()));

        // Mock the behavior of esService.getMessages() to return the test data list
        when(esService.getMessages()).thenReturn(messages);

        // Perform the GET request to the /elasticsearch endpoint
        mockMvc.perform(MockMvcRequestBuilders.get("/elasticsearch")
                .accept(MediaType.APPLICATION_JSON))
                // Verify the response status code is 200 (OK)
                .andExpect(MockMvcResultMatchers.status().isOk())
                // Verify the response content type is JSON
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                // Verify the JSON response by checking a specific attribute of the first message
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].msg").value("Message 1"))
                // You can ignore the produced_at attribute in the comparison as it's dynamic
                // If you want to check the timestamp, you can use a custom Hamcrest matcher for LocalDateTime.
                .andReturn();
    }
//    Test when the service returns messages with different timestamps
    @Test
    public void testGetMessages_Timestamps() throws Exception {
        // Setup test data: Create a list of EntityEs objects with different timestamps
        LocalDateTime now = LocalDateTime.now();
        List<EntityEs> messages = new ArrayList<>();
        messages.add(new EntityEs("1", "Message 1", now));
        messages.add(new EntityEs("2", "Message 2", now.minusHours(1)));
        messages.add(new EntityEs("3", "Message 3", now.minusDays(1)));

        // Mock the behavior of esService.getMessages() to return the test data list
        when(esService.getMessages()).thenReturn(messages);

        // Perform the GET request to the /elasticsearch endpoint
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/elasticsearch")
                .accept(MediaType.APPLICATION_JSON))
                // Verify the response status code is 200 (OK)
                .andExpect(MockMvcResultMatchers.status().isOk())
                // Verify the response content type is JSON
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                // Verify the JSON response by checking the timestamps of each message
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", is("1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].msg", is("Message 1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].produced_at", notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", is("2")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].msg", is("Message 2")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].produced_at", notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].id", is("3")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].msg", is("Message 3")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].produced_at", notNullValue()))
                .andReturn();

    }
    //Test for the incorrect endpoint:
@Test
public void testGetMessages_IncorrectEndpoint() throws Exception {
    // Perform the GET request to a non-existent endpoint
    mockMvc.perform(MockMvcRequestBuilders.get("/invalid_endpoint")
            .accept(MediaType.APPLICATION_JSON)) 
            // Verify the response status code is 404 (Not Found)
            .andExpect(MockMvcResultMatchers.status().isNotFound())
                        .andReturn();
}
}
