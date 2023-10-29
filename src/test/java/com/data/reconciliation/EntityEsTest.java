package com.data.reconciliation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.data.reconciliation.entity.EntityEs;

public class EntityEsTest {

    private EntityEs entity;

    @BeforeEach
    public void setUp() {
        // Create a new EntityEs object before each test
        entity = new EntityEs("1", "Test Message", LocalDateTime.now());
    }

    @Test
    public void testGetId() {
        assertEquals("1", entity.getId());
    }

    @Test
    public void testSetId() {
        entity.setId("2");
        assertEquals("2", entity.getId());
    }

    @Test
    public void testGetMsg() {
        assertEquals("Test Message", entity.getMsg());
    }

    @Test
    public void testSetMsg() {
        entity.setMsg("New Message");
        assertEquals("New Message", entity.getMsg());
    }

    @Test
    public void testGetProducedAt() {
        LocalDateTime now = LocalDateTime.now();
        assertEquals(now.getYear(), entity.getProduced_at().getYear());
        assertEquals(now.getMonth(), entity.getProduced_at().getMonth());
        assertEquals(now.getDayOfMonth(), entity.getProduced_at().getDayOfMonth());
    }

    @Test
    public void testSetProducedAt() {
        LocalDateTime newDateTime = LocalDateTime.of(2023, 7, 31, 12, 30, 0);
        entity.setProduced_at(newDateTime);
        assertEquals(newDateTime, entity.getProduced_at());
    }

    @Test
    public void testConstructor() {
        EntityEs newEntity = new EntityEs("3", "Constructor Test", LocalDateTime.of(2023, 8, 1, 10, 15, 0));
        assertEquals("3", newEntity.getId());
        assertEquals("Constructor Test", newEntity.getMsg());
        assertEquals(LocalDateTime.of(2023, 8, 1, 10, 15, 0), newEntity.getProduced_at());
    }
}
