package de.thb.dim.eventTom.valueObjects.eventManagement;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ShowVOTest {
    @Test
    void testShowVOInitialization() {
        ShowVO show = new ShowVO(3, "Theaterstück", new String[]{"Bühnendekoration", "Soundanlage"}, "Theater", LocalDateTime.now(), Duration.ofHours(2), 2);
        assertEquals(3, show.getId());
        assertEquals("Theaterstück", show.getName());
        assertEquals(Duration.ofHours(2), show.getRuntime());

    }

}