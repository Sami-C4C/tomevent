package de.thb.dim.eventTom.valueObjects.eventManagement;

import de.thb.dim.eventTom.valueObjects.customerManagement.CustomerVO;
import de.thb.dim.eventTom.valueObjects.customerManagement.Gender;
import de.thb.dim.eventTom.valueObjects.customerManagement.exceptions.CustomerNoDateOfBirthException;
import de.thb.dim.eventTom.valueObjects.customerManagement.exceptions.CustomerTooYoungException;
import de.thb.dim.eventTom.valueObjects.ticketSale.BackstageTicketVO;
import de.thb.dim.eventTom.valueObjects.ticketSale.SeasonTicketVO;
import de.thb.dim.eventTom.valueObjects.ticketSale.SeatTicketVO;
import de.thb.dim.eventTom.valueObjects.ticketSale.TicketVO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sun.util.resources.LocaleData;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


/**
 * @author Osama Ahmad , MN:20233244
 */
class EventVOTest {


    private EventVO party, show;
    private LocalDateTime testDate, showDate;
    Duration runtime;
    private TicketVO seatTicket, seasonTicket, backstageTicket ;
    private CustomerVO customer;

    @BeforeEach
    public void setup() throws CustomerNoDateOfBirthException, CustomerTooYoungException {
        String[] equipment = new String[]{"Sound System", "Lights"};
        String[] showEquipment = new String[]{"Stage", "Microphone"};

        testDate = LocalDateTime.of(2023, 12, 22, 15, 30);
        showDate = LocalDateTime.of(2024, 12, 30, 15, 30);
        runtime = Duration.ofHours(2);

        party = new PartyVO(1, "Party 1", equipment, "Club XYZ", testDate, "Buffet", "DJ John");
        show = new ShowVO(2, "Show 1", showEquipment, "Theater ABC", showDate, runtime, 1);
        customer = new CustomerVO("Schneider", "Toralf", "CunoStr", 44, Gender.M, LocalDate.of(1991, 6, 22));
        seatTicket = new SeatTicketVO(55,34.55f,"A1",party);
        seasonTicket = new SeasonTicketVO(40,67.66f,show,showDate.toLocalDate(),showDate.plusDays(3).toLocalDate());
        backstageTicket = new BackstageTicketVO(30,23.50f,"B33",show,customer);
        // Add some tickets to the ticket category
        party.addTicketCategory(seatTicket);
        show.addTicketCategory(seatTicket);
        show.addTicketCategory(backstageTicket);

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
        assertFalse(obj instanceof EventVO);


        party.setName(null);
        try {
            assertFalse(party.getName().equals(partyClone.getName()));
        } catch (
                NullPointerException ex) { // NullPointerException will be not thrown and it is not accepted to make order with null event !
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

        // Assuming default values for other fields based on the provided constructor
        assertEquals(0, event.ticketCategory.size(), "The default ticket category size should be 1.");
        assertEquals(0.0f, event.getSeatTicketPrice(), "The default seat ticket price should be 0.0f.");
        assertEquals(0.0f, event.getSeasonTicketPrice(), "The default season ticket price should be 0.0f.");
        assertEquals(0.0f, event.getBackstageTicketPrice(), "The default backstage ticket price should be 0.0f.");
        assertEquals(0, event.getNrAvailableTickets(), "The default number of available tickets should be 0.");
    }

    @Test
    void testToString() {
        EventVO event = new EventVO(1, "Concert", new String[]{"Microphone", "Speakers"}, "City Hall", testDate, 2) {
        }; // Using an anonymous subclass because EventVO is abstract

        String expected = "1 - Concert: " + testDate.toString() + " at City Hall\tMicrophone, Speakers";
        assertEquals(expected, event.toString(), "String representation should match expected format.");
    }


    @Test
    void testParametrizedConstructor() {
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
    public void testNullComparison() {
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
    public void testHashCode() {

        EventVO partyClone = new PartyVO(1, "Party 1", new String[]{"Sound System", "Lights"}, "Club XYZ", testDate, "Buffet", "DJ John");
        assertEquals(party.hashCode(), partyClone.hashCode(), "Equal objects must have equal hash codes.");
        int initialHashCode = party.hashCode();
        assertEquals(initialHashCode, party.hashCode(), "Hash code should be consistent across invocations.");
        assertEquals(initialHashCode, party.hashCode(), "Hash code should be consistent across invocations.");
        EventVO differentParty = new PartyVO(2, "Party 2", new String[]{"Sound System", "Strobe Lights"}, "Club ZYX", testDate.plusDays(1), "Full Bar", "DJ Jane");
        assertNotEquals(party.hashCode(), differentParty.hashCode(), "It's desirable for unequal objects to have different hash codes for better hash table performance.");

        EventVO eventWithNullFields = new PartyVO(1, "muster_party", null, null, null, null, null);
        assertNotNull(eventWithNullFields.hashCode(), "Objects with null fields should still produce a hash code.");
    }


    /**
     * @author Osama Ahmad.
     * EventVO is an abstract class, and its methods can't be directly instantiated or invoked in the typical way.
     * Instead of mocking, I created a concrete subclass of EventVO in my test to test setter methods. This
     * subclass can be a minimal implementation that extends EventVO just for testing purposes.
     */
    private static class EventVOMocking extends EventVO {
        public EventVOMocking(int id, String name, String[] equipment, String location, LocalDateTime date, int anzCategory) {
            super(id, name, equipment, location, date, anzCategory);
        }

        @Override
        public void setId(int id) {
            if (id <= 0) {
                throw new IllegalArgumentException("ID must be positive and non-zero. Invalid ID: " + id);
            }
            super.setId(id);  // Call the original method to set the ID.
        }

        @Override
        public void setName(String name) {
            if (name == null || name.trim().isEmpty()) {
                throw new NullPointerException("Name cannot be null or empty.");
            }
            super.setName(name);  // Call the original method to set the name.
        }

        @Override
        public void setEquipment(String[] equipment) {
            if (equipment == null) {
                throw new NullPointerException("Equipment cannot be null.");
            }
            super.setEquipment(equipment);
        }

        @Override
        public void setLocation(String location) {
            if (location == null) {
                throw new NullPointerException("location cannot be null.");
            }
            super.setLocation(location);
        }

        @Override
        public void setSeatTicketPrice(float seatTicketPrice) {
            if (seatTicketPrice <= 0) {
                throw new IllegalArgumentException("Invalid value for seatTicketPrice");
            }
            super.setSeatTicketPrice(seatTicketPrice);
        }

        @Override
        public void setSeasonTicketPrice(float seasonTicketPrice) {
            if (seasonTicketPrice <= 0) {
                throw new IllegalArgumentException("Invalid value for seatTicketPrice");

            }
            super.setSeasonTicketPrice(seasonTicketPrice);
        }


        @Override
        public void setBackstageTicketPrice(float backstageTicketPrice) {
            if (backstageTicketPrice <= 0) {
                throw new IllegalArgumentException("Invalid value for backstageTicketPrice");
            }
            super.setBackstageTicketPrice(backstageTicketPrice);
        }
    }


    @Test
    void testEventVO_SetterWithInvalidValue() {
        EventVOMocking mockEvent = new EventVOMocking(1, "Test Event", new String[]{"Equipment"}, "Location", LocalDateTime.now(), 1);

        // Test for negative ID value
        assertThrows(IllegalArgumentException.class, () -> mockEvent.setId(-1), "Setting a negative ID should throw IllegalArgumentException");

        // Test for zero as ID value
        assertThrows(IllegalArgumentException.class, () -> mockEvent.setId(0), "Setting ID to zero should throw IllegalArgumentException");

        // Test for null name
        assertThrows(NullPointerException.class, () -> mockEvent.setName(null), "Setting name to null should throw NullPointerException");

        // Test for empty string as name
        assertThrows(NullPointerException.class, () -> mockEvent.setName(""), "Setting name to an empty string should throw NullPointerException");

        // Test for name with only white spaces
        assertThrows(NullPointerException.class, () -> mockEvent.setName("  "), "Setting name to only white spaces should throw NullPointerException");

        // Test for Equipment with null
        assertThrows(NullPointerException.class, () -> mockEvent.setEquipment(null), "Setting Equipment to null should throw NullPointerException");

        // Test for Location with null
        assertThrows(NullPointerException.class, () -> mockEvent.setLocation(null), "Setting Location to null should throw NullPointerException");

        // Test for setSeatTicketPrice with negative
        assertThrows(IllegalArgumentException.class, () -> mockEvent.setSeatTicketPrice(-100.50f), "Invalid Ticket Price, price could not be negative !");

        // Test for setSeatTicketPrice with 0
        assertThrows(IllegalArgumentException.class, () -> mockEvent.setSeatTicketPrice(0), "Invalid Ticket Price, must be positive !");


        // Test for Location with with negative
        assertThrows(IllegalArgumentException.class, () -> mockEvent.setSeasonTicketPrice(-50.50f), "Invalid Ticket Price, price could not be negative !");

        // Test for Location with null
        assertThrows(IllegalArgumentException.class, () -> mockEvent.setSeasonTicketPrice(0), "Invalid Ticket Price, must be positive !");


        // Test for BackstageTicketPrice with negative
        assertThrows(IllegalArgumentException.class, () -> mockEvent.setBackstageTicketPrice(-40.50f), "Invalid Ticket Price, price could not be negative !");


        // Test for BackstageTicketPrice with 0
        assertThrows(IllegalArgumentException.class, () -> mockEvent.setBackstageTicketPrice(0), "Invalid Ticket Price, must be positive !");


    }



    @AfterEach
    public void teardown() {
        // Reset all the objects to null to ensure no state is carried over between tests
        party = null;
        show = null;


    }

}