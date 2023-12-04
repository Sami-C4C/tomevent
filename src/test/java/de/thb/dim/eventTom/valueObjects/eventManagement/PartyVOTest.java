package de.thb.dim.eventTom.valueObjects.eventManagement;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PartyVOTest {
    @Test
    void testPartyVOInitialization() {
        PartyVO party = new PartyVO(2, "PartyEvent", new String[]{"DJ-Set", "Lichter"}, "Club", LocalDateTime.now(), "Catering-Service", "DJ Max");
        assertEquals(2, party.getId());
        assertEquals("PartyEvent", party.getName());
        assertEquals("Catering-Service", party.getCatering());
        assertEquals("DJ Max", party.getPerformer());

    }


}