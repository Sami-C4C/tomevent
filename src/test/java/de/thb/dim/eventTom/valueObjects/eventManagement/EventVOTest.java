package de.thb.dim.eventTom.valueObjects.eventManagement;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class EventVOTest {
    @Test
    void testEventVOInitialization() {
        EventVO event = new EventVO(1, "TestEvent", new String[]{"Microphone", "lighting"}, "concert hall", LocalDateTime.now(), 3) {
        };
        assertEquals(1, event.getId());
        assertEquals("TestEvent", event.getName());
        assertArrayEquals(new String[]{"Microphone", "lighting"}, event.getEquipment());
        assertEquals("concert hall", event.getLocation());

    }
}