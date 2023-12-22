package de.thb.dim.eventTom.valueObjects.eventManagement;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


/**
 * @author Osama Ahmad , MN:20233244
 */
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

    @Test
    void testDefaultConstructor() {
        EventVO event = new PartyVO(23, "Party13", null, null, null, null, null);

        // Überprüfen Sie, ob die Standardwerte korrekt gesetzt sind
        assertEquals(23, event.getId());
        assertNotNull(event.getName());
        assertNull(event.getEquipment());
        assertNull(event.getLocation());
        assertNull(event.getDate());
        assertEquals(0, event.getNrAvailableTickets()); // Oder was auch immer der Standardwert sein soll
        // Überprüfen Sie weitere Standardwerte für seatTicketPrice, seasonTicketPrice, backstageTicketPrice falls erforderlich
    }

    @Test
    public void testSameObjectComparison() {
        EventVO event1 = new PartyVO(2, "PartyEvent", new String[]{"DJ-Set", "Lichter"}, "Club", LocalDateTime.now(), "Catering-Service", "DJ Max");
        assertTrue(event1.equals(event1));
    }

    @Test
    public void testNullComparison() {
        EventVO event1 = new PartyVO(2, "PartyEvent", new String[]{"DJ-Set", "Lichter"}, "Club", LocalDateTime.now(), "Catering-Service", "DJ Max");
        assertFalse(event1.equals(null));
    }

    @Test
    public void testDifferentClassComparison() {
        EventVO event1 = new PartyVO(2, "PartyEvent", new String[]{"DJ-Set", "Lichter"}, "Club", LocalDateTime.now(), "Catering-Service", "DJ Max");
        Object otherObject = new Object();

        assertFalse(event1.equals(otherObject));
    }

    @Test
    public void testDifferentFieldsComparison() {
        LocalDateTime fixedDate1 = LocalDateTime.of(2023, 9, 22, 12, 0);
        LocalDateTime fixedDat2 = LocalDateTime.of(2023, 7, 12, 12, 0);

        EventVO event1 = new PartyVO(2, "PartyEvent1", new String[]{"DJ-Set", "Lichter"}, "Club", fixedDate1, "Catering-Service", "DJ Max");
        EventVO event2 = new PartyVO(3, "PartyEvent2", new String[]{"DJ-Set", "Lichter"}, "Club", fixedDat2, "Catering-Service", "DJ Max");

        assertFalse(event1.equals(event2));
    }


    @Test
    public void testSameFieldsComparison() {
        LocalDateTime fixedDate = LocalDateTime.of(2023, 9, 22, 12, 0);

        EventVO event1 = new PartyVO(2, "PartyEvent1", new String[]{"DJ-Set", "Lichter"}, "Club", fixedDate, "Catering-Service", "DJ Max");
        PartyVO party1 = new PartyVO(2, "PartyEvent1", new String[]{"DJ-Set", "Lichter"}, "Club", fixedDate, "Catering-Service", "DJ Max");

        assertTrue(event1.equals(party1));
    }

    @Test
    public void testNullFieldsComparison() {
        LocalDateTime fixedDate = LocalDateTime.of(2023, 9, 22, 12, 0);

        EventVO event1 = new PartyVO(2, "PartyEvent1", new String[]{"DJ-Set", "Lichter"}, "Club", fixedDate, "Catering-Service", "DJ Max");

        EventVO eventWithNullDate = new PartyVO(2, "PartyEvent1", new String[]{"DJ-Set", "Lichter"}, null, null, "Catering-Service", "DJ Max");

        assertFalse(event1.equals(eventWithNullDate));


    }

    @Test
    public void testNullLocationComparison() {
        LocalDateTime fixedDate = LocalDateTime.of(2023, 9, 22, 12, 0);

        EventVO event1 = new PartyVO(2, "PartyEvent1", new String[]{"DJ-Set", "Lichter"}, "Club", fixedDate, "Catering-Service", "DJ Max");

        EventVO eventWithNullLocation = new PartyVO(2, "PartyEvent1", new String[]{"DJ-Set", "Lichter"}, null, fixedDate, "Catering-Service", "DJ Max");

        assertFalse(event1.equals(eventWithNullLocation));

    }

    @Test
    public void testDifferentLocationComparison() {
        LocalDateTime fixedDate = LocalDateTime.of(2023, 9, 22, 12, 0);

        EventVO event1 = new PartyVO(2, "PartyEvent1", new String[]{"DJ-Set", "Lichter"}, "Club2", fixedDate, "Catering-Service", "DJ Max");

        EventVO event7 = new PartyVO(2, "PartyEvent1", new String[]{"DJ-Set", "Lichter"}, "Club3", fixedDate, "Catering-Service", "DJ Max");

        assertFalse(event1.equals(event7));

    }
    @Test
    public void testNullLocationComparison2() {
        LocalDateTime fixedDate = LocalDateTime.of(2023, 9, 22, 12, 0);

        EventVO event1 = new PartyVO(2, "PartyEvent1", new String[]{"DJ-Set", "Lichter"}, null, fixedDate, "Catering-Service", "DJ Max");

        EventVO event7 = new PartyVO(2, "PartyEvent1", new String[]{"DJ-Set", "Lichter"}, "Club3", fixedDate, "Catering-Service", "DJ Max");

        assertFalse(event1.equals(event7));

    }

    @Test
    public void testDifferentDatesComparison() {
        LocalDateTime fixedDat = LocalDateTime.of(2023, 7, 12, 12, 0);

        EventVO event1 = new PartyVO(2, "PartyEvent1", new String[]{"DJ-Set", "Lichter"}, "Club", fixedDat, "Catering-Service", "DJ Max");
        EventVO event2 = new PartyVO(2, "PartyEvent1", new String[]{"DJ-Set", "Lichter"}, "Club", null, "Catering-Service", "DJ Max");

        assertFalse(event1.equals(event2));
    }


    @Test
    public void testDifferentIDsComparison() {
        LocalDateTime fixedDat = LocalDateTime.of(2023, 7, 12, 12, 0);

        EventVO event1 = new PartyVO(2, "PartyEvent1", new String[]{"DJ-Set", "Lichter"}, "Club", fixedDat, "Catering-Service", "DJ Max");
        EventVO event2 = new PartyVO(3, "PartyEvent1", new String[]{"DJ-Set", "Lichter"}, "Club", fixedDat, "Catering-Service", "DJ Max");

        assertFalse(event1.equals(event2));
    }


    @Test
    public void testDifferentNamesComparison() {
        LocalDateTime fixedDat = LocalDateTime.of(2023, 7, 12, 12, 0);

        EventVO event1 = new PartyVO(2, "PartyEvent1", new String[]{"DJ-Set", "Lichter"}, "Club", fixedDat, "Catering-Service", "DJ Max");
        EventVO event2 = new PartyVO(2, "PartyEvent3", new String[]{"DJ-Set", "Lichter"}, "Club", fixedDat, "Catering-Service", "DJ Max");

        assertFalse(event1.equals(event2));
    }


}