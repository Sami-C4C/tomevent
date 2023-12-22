package de.thb.dim.eventTom.valueObjects.eventManagement;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Osama Ahmad , MN:20233244
 */
class PartyVOTest {
    @Test
    void testPartyVOInitialization() {
        PartyVO party = new PartyVO(2, "PartyEvent", new String[]{"DJ-Set", "Lichter"}, "Club", LocalDateTime.now(), "Catering-Service", "DJ Max");
        assertEquals(2, party.getId());
        assertEquals("PartyEvent", party.getName());
        assertEquals("Catering-Service", party.getCatering());
        assertEquals("DJ Max", party.getPerformer());

    }

    @Test
    void testPartyVOStandardConstructor() {
        PartyVO party = new PartyVO(1, "Party1", null, null, null, null, null);
        assertEquals(1, party.getId());
        assertNotNull(party.getName());
        assertNull(party.getLocation());
        assertNull(party.getDate());
        assertNull(party.getCatering());
        assertNull(party.getPerformer());
        // Weitere Überprüfungen für die Attribute von EventVO
    }

    @Test
    void testEqualsWithDifferentClass() {
        PartyVO party = new PartyVO(1, "Party", new String[]{"DJ"}, "Club", LocalDateTime.now(), "Catering", "DJ");
        Object other = new Object();

        assertNotEquals(party, other);
    }


    @Test
    void testEqualsWithNullCateringAndPerformer() {
        LocalDateTime fixedDate = LocalDateTime.of(2023, 9, 22, 12, 0);

        PartyVO party1 = new PartyVO(1, "Party", new String[]{"DJ"}, "Club", fixedDate, null, "DJ");
        PartyVO party2 = new PartyVO(1, "Party", new String[]{"DJ"}, "Club", fixedDate, "Catering", "DJ");

        assertNotEquals(party1, party2);
        party1.setCatering("Catering");
        assertEquals(party1, party2);

        LocalDateTime fixedDate2 = LocalDateTime.of(2023, 3, 28, 9, 0);

        PartyVO party3 = new PartyVO(1, "Party", new String[]{"DJ"}, "Club", fixedDate2, "Catering", null);
        PartyVO party4 = new PartyVO(1, "Party", new String[]{"DJ"}, "Club", fixedDate2, "Catering", "DJ");

        assertNotEquals(party3, party4);

        party3.setPerformer("DJ");

        assertEquals(party3,party4);

    }


    @Test
    void testEqualsWithDifferentCateringAndPerformer() {
        PartyVO party1 = new PartyVO(1, "Party", new String[]{"DJ"}, "Club", LocalDateTime.now(), "Catering1", "DJ");
        PartyVO party2 = new PartyVO(1, "Party", new String[]{"DJ"}, "Club", LocalDateTime.now(), "Catering2", "DJ");

        assertNotEquals(party1, party2);

        PartyVO party3 = new PartyVO(1, "Party", new String[]{"DJ"}, "Club", LocalDateTime.now(), "Catering", "DJ1");
        PartyVO party4 = new PartyVO(1, "Party", new String[]{"DJ"}, "Club", LocalDateTime.now(), "Catering", "DJ2");

        assertNotEquals(party3, party4);

        party3.setPerformer(null);
        assertFalse(party3.equals(party4));
    }


    @Test
    public void testDifferentInstanceComparison() {
        LocalDateTime fixedDate = LocalDateTime.of(2023, 9, 22, 12, 0);

        EventVO event1 = new PartyVO(2, "PartyEvent", new String[]{"DJ-Set", "Lichter"}, "Club", fixedDate, "Catering-Service", "DJ Max");
        PartyVO event2 = new PartyVO(2, "PartyEvent", new String[]{"DJ-Set", "Lichter"}, "Club", fixedDate, "Catering-Service", "DJ Max");

        assertEquals(event1,event2);
        event2.setCatering(null);
        assertNotEquals(event1,event2);
    }


    @Test
    void testEqualsWithNullCateringComparison() {
        LocalDateTime fixedDate = LocalDateTime.of(2023, 9, 22, 12, 0);

        PartyVO party1 = new PartyVO(1, "Party", new String[]{"DJ"}, "Club", fixedDate, null, "DJ");
        PartyVO party2 = new PartyVO(1, "Party", new String[]{"DJ"}, "Club", fixedDate, "Catering5", "DJ");

        assertNotEquals(party1, party2);

       party1.setCatering("Catering5");

       assertTrue(party1.equals(party2));
    }


}