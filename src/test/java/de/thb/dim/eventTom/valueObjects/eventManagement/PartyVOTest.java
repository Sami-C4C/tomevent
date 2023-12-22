package de.thb.dim.eventTom.valueObjects.eventManagement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Osama Ahmad , MN:20233244
 */
class PartyVOTest {

    private EventVO party1, party2, partyWithNullCatering, partyWithNullPerformer;
    private LocalDateTime date;

    @BeforeEach
    void setUp() {
        date = LocalDateTime.of(2023, 10, 5, 20, 0);

        party1 = new PartyVO(1, "Party A", new String[]{"Sound", "Lights"}, "Club XYZ", date, "Buffet", "DJ John");
        party2 = new PartyVO(4, "Party A", new String[]{"Sound", "Lights"}, "Club XYZ", date, "Buffet", "DJ John");
        partyWithNullCatering = new PartyVO(1, "Party A", new String[]{"Sound", "Lights"}, "Club XYZ", date, null, "DJ John");
        partyWithNullPerformer = new PartyVO(1, "Party A", new String[]{"Sound", "Lights"}, "Club XYZ", date, "Buffet", null);
    }


    @Test
    void testEquals() {
        assertTrue(party1.equals(party1), "A party must equal itself.");
        assertFalse(party1.equals(null), "A party should not equal null.");

        Object otherObject = new Object();
        assertFalse(party1.equals(otherObject), "A party should not equal an object of a different class.");

        PartyVO differentCatering = new PartyVO(1, "Party A", new String[]{"Sound", "Lights"}, "Club XYZ", date, "Full Bar", "DJ John");
        assertFalse(party1.equals(differentCatering), "Parties with different catering should not be equal.");


        EventVO party2Clone = new PartyVO(4, "Party A", new String[]{"Sound", "Lights"}, "Club XYZ", date, "Buffet", "DJ John");

        assertTrue(party2.equals(party2Clone), "Two parties with the same catering should be equal.");

        PartyVO anotherPartyWithNullCatering = new PartyVO(1, "Party A", new String[]{"Sound", "Lights"}, "Club XYZ", date, null, "DJ John");
        assertTrue(partyWithNullCatering.equals(anotherPartyWithNullCatering), "Two parties with null catering should be equal.");

        PartyVO differentPerformer = new PartyVO(1, "Party A", new String[]{"Sound", "Lights"}, "Club XYZ", date, "Buffet", "DJ Jane");
        assertFalse(party1.equals(differentPerformer), "Parties with different performers should not be equal.");


        PartyVO anotherPartyWithNullPerformer = new PartyVO(1, "Party A", new String[]{"Sound", "Lights"}, "Club XYZ", date, "Buffet", null);
        assertTrue(partyWithNullPerformer.equals(anotherPartyWithNullPerformer), "Two parties with null performers should be equal.");
    }


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

        assertEquals(party3, party4);

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

        assertEquals(event1, event2);
        event2.setCatering(null);
        assertNotEquals(event1, event2);
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