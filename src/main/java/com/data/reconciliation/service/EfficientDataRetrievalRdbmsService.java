package com.data.reconciliation.service;

import java.io.*;
import java.util.List;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;
import com.data.reconciliation.entity.EntityRdbms;
import com.data.reconciliation.exception.InternalServerErrorException;
import com.data.reconciliation.repository.RdbmsRepository;

@Service
public class EfficientDataRetrievalRdbmsService {
    private final RdbmsRepository rdbmsRepository;
    private final String currentFilePath = "last_id_rdbms.txt"; // Path to the text file storing the current value

    public EfficientDataRetrievalRdbmsService(RdbmsRepository rdbmsRepository) {
        this.rdbmsRepository = rdbmsRepository;
        initializeCurrentId();
    }

    public List<EntityRdbms> getAllMessages() {
        try {
            return rdbmsRepository.findAll();
        } catch (DataRetrievalFailureException ex) {
            throw new InternalServerErrorException("Error occurred while retrieving data from the database.");
        }
    }

    public Long initializeCurrentId() {
        Long maxId = rdbmsRepository.findMaxId();
        if (maxId != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(currentFilePath))) {
                writer.write(String.valueOf(maxId)); // Write the maximum ID to the text file
            } catch (IOException e) {
                throw new InternalServerErrorException("Error occurred while writing to the file.");
            }
            return maxId;
        }
        return null;
    }

    public long getCurrent() {
        try (BufferedReader reader = new BufferedReader(new FileReader(currentFilePath))) {
            String line = reader.readLine();
            if (line != null) {
                return Long.parseLong(line); // Read the current value from the text file
            }
        } catch (IOException | NumberFormatException e) {
            throw new InternalServerErrorException("Error occurred while reading the file.");
        }
        return 0; // Default value if the file is empty or an error occurs
    }

    public List<EntityRdbms> getMessagesGreaterThanCurrent() {
        try {
            List<EntityRdbms> messagesGreaterThanCurrent = rdbmsRepository.findByCurrentId(getCurrent());
            if (!messagesGreaterThanCurrent.isEmpty()) {
                EntityRdbms mostRecentMessage = messagesGreaterThanCurrent.get(messagesGreaterThanCurrent.size() - 1);
                updateCurrentId(mostRecentMessage.getId()); // Update the current value in the text file
            }
            return messagesGreaterThanCurrent;
        } catch (DataRetrievalFailureException ex) {
            throw new InternalServerErrorException("Error occurred while retrieving data from the database.");
        }
    }

    private void updateCurrentId(long newValue) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(currentFilePath))) {
            writer.write(String.valueOf(newValue)); // Update the current value in the text file
        } catch (IOException e) {
            throw new InternalServerErrorException("Error occurred while writing to the file.");
        }
    }
}
