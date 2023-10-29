package com.data.reconciliation;


import static org.mockito.Mockito.when;

import com.data.reconciliation.entity.EntityRdbms;
import com.data.reconciliation.repository.RdbmsRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class RdbmsRepositoryTest {

    @Mock
    private RdbmsRepository rdbmsRepository;

    @BeforeEach
    void setUp() {
        // Define the behavior of mocked methods
        List<EntityRdbms> testData = Arrays.asList(
                new EntityRdbms(1L, 1L, "Message 1", LocalDateTime.now()),
                new EntityRdbms(2L, 2L, "Message 2", LocalDateTime.now()),
                new EntityRdbms(3L, 3L, "Message 3", LocalDateTime.now())
        );

        when(rdbmsRepository.findByCurrentId(2L)).thenReturn(Collections.singletonList(
                new EntityRdbms(2L, null, "Message 2", LocalDateTime.now())
        ));

        // Define the behavior of findMaxId() to return the expected value
        when(rdbmsRepository.findMaxId()).thenReturn(3L);

        when(rdbmsRepository.findAll()).thenReturn(testData);
        when(rdbmsRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(testData.get(0)));
    }
}
