package de.thb.dim.eventTom.valueObjects.eventManagement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


/**
 * @author Osama Ahmad , MN:20233244
 */
class EventVOTest {


    private EventVO party, show;
    private LocalDateTime testDate, showDate;


    @BeforeEach
    public void setup() {
        String[] equipment = {"Sound System", "Lights"};
        testDate = LocalDateTime.of(2023, 12, 22, 15, 30);
        showDate = LocalDateTime.of(2024, 12, 30, 15, 30);
        Duration runtime = Duration.ofHours(2);

        party = new PartyVO(1, "Party 1", equipment, "Club XYZ", testDate, "Buffet", "DJ John");
        show = new ShowVO(2, "Show 1", equipment, "Theater ABC", showDate, runtime, 1);

    }


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
    void testEquals() {
        assertTrue(party.equals(party), "An object should be equal to itself.");

        EventVO partyClone = new PartyVO(1, "Party 1", new String[]{"Sound System", "Lights"}, "Club XYZ", testDate, "Buffet", "DJ John");
        assertTrue(party.equals(partyClone) && partyClone.equals(party), "Symmetry condition failed.");


        EventVO partyClone1 = new PartyVO(1, "Party 1", new String[]{"Sound System", "Lights"}, "Club XYZ", testDate, "Buffet", "DJ John");
        EventVO partyClone2 = new PartyVO(1, "Party 1", new String[]{"Sound System", "Lights"}, "Club XYZ", testDate, "Buffet", "DJ John");
        assertTrue(party.equals(partyClone1) && partyClone1.equals(partyClone2) && party.equals(partyClone2), "Transitivity condition failed.");
        assertTrue(partyClone.equals(partyClone1), "Second invocation returned true, consistent with the first.");

        partyClone1.setDate(null);
        assertFalse(partyClone1.equals(partyClone2));

        assertTrue(party.equals(partyClone), "First invocation returned true.");
        assertFalse(party.equals(null), "Comparing to null should return false.");

        Object obj = new Object();
        assertFalse(obj instanceof EventVO );

        party.setName(null);
        try {
            assertFalse(party.getName().equals(partyClone.getName()));
        }catch (NullPointerException ex){
            System.out.println(ex.getMessage());
        }
        assertFalse(party.equals(partyClone));
    }


    @Test
    void testDefaultConstructor() {
        EventVO event = new EventVO() {
        }; // Using an anonymous subclass because EventVO is abstract

        assertEquals(0, event.getId(), "ID should be 0 by default");
        assertNull(event.getName(), "Name should be null by default");
        assertNull(event.getEquipment(), "Equipment should be null by default");
        assertNull(event.getLocation(), "Location should be null by default");
        assertNull(event.getDate(), "Date should be null by default");
        // Add assertions for any other fields you expect to have default values
    }

    @Test
    void testToString() {
        EventVO event = new EventVO(1, "Concert", new String[]{"Microphone", "Speakers"}, "City Hall", testDate, 2) {
        }; // Using an anonymous subclass because EventVO is abstract

        String expected = "1 - Concert: " + testDate.toString() + " at City Hall\tMicrophone, Speakers";
        assertEquals(expected, event.toString(), "String representation should match expected format.");
    }


    @Test
    void testDeParametrizedConstructor() {
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
//        EventVO event1 = new PartyVO(2, "PartyEvent", new String[]{"DJ-Set", "Lichter"}, "Club", LocalDateTime.now(), "Catering-Service", "DJ Max");
        assertTrue(party.equals(party));
    }

    @Test
    public void testNullComparison() {
//        EventVO event1 = new PartyVO(2, "PartyEvent", new String[]{"DJ-Set", "Lichter"}, "Club", LocalDateTime.now(), "Catering-Service", "DJ Max");
        assertFalse(show.equals(null));
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

        assertFalse(event1.equals(eventWithNullLocation) && eventWithNullLocation.equals(event1));

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



    @Test
    public void testHashCode(){
        EventVO partyClone = new PartyVO(1, "Party 1", new String[]{"Sound System", "Lights"}, "Club XYZ", testDate, "Buffet", "DJ John");
        assertEquals(party.hashCode(), partyClone.hashCode(), "Equal objects must have equal hash codes.");
        int initialHashCode = party.hashCode();
        assertEquals(initialHashCode, party.hashCode(), "Hash code should be consistent across invocations.");
        assertEquals(initialHashCode, party.hashCode(), "Hash code should be consistent across invocations.");
        EventVO differentParty = new PartyVO(2, "Party 2", new String[]{"Sound System", "Strobe Lights"}, "Club ZYX", testDate.plusDays(1), "Full Bar", "DJ Jane");
        assertNotEquals(party.hashCode(), differentParty.hashCode(), "It's desirable for unequal objects to have different hash codes for better hash table performance.");

        EventVO eventWithNullFields = new PartyVO(1, null, null, null, null, null, null);
        assertNotNull(eventWithNullFields.hashCode(), "Objects with null fields should still produce a hash code.");
    }



}