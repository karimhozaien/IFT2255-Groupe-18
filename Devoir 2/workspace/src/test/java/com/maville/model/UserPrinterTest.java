package com.maville.model;


import com.maville.controller.repository.WorkRepository;
import com.maville.view.MenuView;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class UserPrinterTest {

    private final WorkRepository workRepository = new WorkRepository();

    @Test
    void testGetRecordsWithValidJson() throws IOException {
        // Arrange
        String jsonResponse = "{ \"result\": { \"records\": [ { \"id\": \"1\", \"reason_category\": \"routier\" } ] } }";

        // Act
        List<WorkRepository.Result.Record> records = workRepository.getRecords(jsonResponse);

        // Assert
        assertNotNull(records);
        assertEquals(1, records.size());
        assertEquals("1", records.get(0).getId());
        assertEquals("routier", records.get(0).getTypeOfWorkRaw());
    }

    @Test
    void testGetRecordsWithInvalidJson() {
        // Arrange
        String jsonResponse = "invalid json";

        // Act & Assert
        assertThrows(IOException.class, () -> workRepository.getRecords(jsonResponse));
    }


}