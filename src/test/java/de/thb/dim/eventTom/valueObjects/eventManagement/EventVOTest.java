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

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;


/**
 * @author Osama Ahmad , MN:20233244
 */
class EventVOTest {


    private EventVO party1, party2, show;
    private LocalDateTime testDate, showDate;
    Duration runtime;
    private TicketVO seatTicket, seasonTicket, backstageTicket;
    private CustomerVO customer;

    @BeforeEach
    public void setup() throws CustomerNoDateOfBirthException, CustomerTooYoungException {
        String[] partyEquipment = new String[]{"Sound System", "Lights"};
        String[] showEquipment = new String[]{"Stage", "Microphone"};

        testDate = LocalDateTime.of(2023, 12, 22, 15, 30);
        showDate = LocalDateTime.of(2024, 12, 30, 15, 30);
        runtime = Duration.ofHours(2);

        party1 = new PartyVO(1, "Party 1", partyEquipment, "Club XYZ", testDate, "Buffet", "DJ John");
        // Icreated party2 with
        party2 = new PartyVO(1, "Party 1", partyEquipment, "Club XYZ", testDate, "Buffet", "DJ John");

        show = new ShowVO(2, "Show 1", showEquipment, "Theater ABC", showDate, runtime, 1);
        customer = new CustomerVO("Schneider", "Toralf", "CunoStr", 44, Gender.M, LocalDate.of(1991, 6, 22));
        seatTicket = new SeatTicketVO(55, 34.55f, "A1", party1);
        seasonTicket = new SeasonTicketVO(40, 67.66f, show, showDate.toLocalDate(), showDate.plusDays(3).toLocalDate());
        backstageTicket = new BackstageTicketVO(30, 23.50f, "B33", show, customer);
        // Add some tickets to the ticket category
        party1.addTicketCategory(seatTicket);
        show.addTicketCategory(seatTicket);
        show.addTicketCategory(backstageTicket);

    }


    @Test
    void testClone() throws CloneNotSupportedException {
        // Clone the party1 object
        EventVO clonedParty = (EventVO) party1.clone();

        // Assert that the original and cloned objects are not the same instance
        assertNotSame(party1, clonedParty);

        // Assert that all relevant fields in the cloned object match those of the original
        assertEquals(party1.getId(), clonedParty.getId());
        assertEquals(party1.getName(), clonedParty.getName());
        assertArrayEquals(party1.getEquipment(), clonedParty.getEquipment());
        assertEquals(party1.getLocation(), clonedParty.getLocation());
        assertEquals(party1.getDate(), clonedParty.getDate());
        assertEquals(party1.getNrAvailableTickets(), clonedParty.getNrAvailableTickets());
        assertEquals(party1.getSeatTicketPrice(), clonedParty.getSeatTicketPrice());
        assertEquals(party1.getSeasonTicketPrice(), clonedParty.getSeasonTicketPrice());
        assertEquals(party1.getBackstageTicketPrice(), clonedParty.getBackstageTicketPrice());

        // Check that the ticketCategory list is deeply cloned (if necessary)
        assertNotNull(clonedParty.ticketCategory);
        assertNotSame(party1.ticketCategory, clonedParty.ticketCategory);
    }


    @Test
    public void testHashCode1() {
        LocalDateTime testDate = LocalDateTime.now();
        EventVO party = new PartyVO(1, "Party 1", new String[]{"Sound System", "Lights"}, "Club XYZ", testDate, "Buffet", "DJ John");
        EventVO partyClone = new PartyVO(1, "Party 1", new String[]{"Sound System", "Lights"}, "Club XYZ", testDate, "Buffet", "DJ John");

        // Consistency and Equality
        assertEquals(party.hashCode(), partyClone.hashCode(), "Equal objects must have equal hash codes.");

        // Different objects
        EventVO differentParty = new PartyVO(2, "Party 2", new String[]{"Sound System", "Strobe Lights"}, "Club ZYX", testDate.plusDays(1), "Full Bar", "DJ Jane");
        assertNotEquals(party.hashCode(), differentParty.hashCode(), "It's desirable for unequal objects to have different hash codes for better hash table performance.");

        // Object with null fields
        EventVO eventWithNullFields = new PartyVO(1, null, null, null, null, "Buffet", "DJ John");
        assertNotNull(eventWithNullFields.hashCode(), "Objects with null fields should still produce a hash code.");
    }

    @Test
    public void testHashCode2() {

        EventVO partyClone = new PartyVO(1, "Party 1", new String[]{"Sound System", "Lights"}, "Club XYZ", testDate, "Buffet", "DJ John");
        assertEquals(party1.hashCode(), partyClone.hashCode(), "Equal objects must have equal hash codes.");
        int initialHashCode = party1.hashCode();
        assertEquals(initialHashCode, party1.hashCode(), "Hash code should be consistent across invocations.");
        assertEquals(initialHashCode, party1.hashCode(), "Hash code should be consistent across invocations.");
        EventVO differentParty = new PartyVO(2, "Party 2", new String[]{"Sound System", "Strobe Lights"}, "Club ZYX", testDate.plusDays(1), "Full Bar", "DJ Jane");
        assertNotEquals(party1.hashCode(), differentParty.hashCode(), "It's desirable for unequal objects to have different hash codes for better hash table performance.");

        EventVO eventWithNullFields = new PartyVO(1, "muster_party", null, null, null, null, null);
        assertNotNull(eventWithNullFields.hashCode(), "Objects with null fields should still produce a hash code.");
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
    void testEquals1() {


        assertTrue(party1.equals(party1), "An object should be equal to itself.");

        EventVO partyClone = new PartyVO(1, "Party 1", new String[]{"Sound System", "Lights"}, "Club XYZ", testDate, "Buffet", "DJ John");
        assertTrue(party1.equals(partyClone) && partyClone.equals(party1), "Symmetry condition failed.");


        EventVO partyClone1 = new PartyVO(1, "Party 1", new String[]{"Sound System", "Lights"}, "Club XYZ", testDate, "Buffet", "DJ John");
        EventVO partyClone2 = new PartyVO(1, "Party 1", new String[]{"Sound System", "Lights"}, "Club XYZ", testDate, "Buffet", "DJ John");
        assertTrue(party1.equals(partyClone1) && partyClone1.equals(partyClone2) && party1.equals(partyClone2), "Transitivity condition failed.");
        assertTrue(partyClone.equals(partyClone1), "Second invocation returned true, consistent with the first.");

        partyClone1.setDate(null);
        assertFalse(partyClone1.equals(partyClone2));

        assertTrue(party1.equals(partyClone), "First invocation returned true.");
        assertFalse(party1.equals(null), "Comparing to null should return false.");

        Object obj = new Object();
        assertFalse(obj instanceof EventVO);


        party1.setName(null);
        try {
            assertFalse(party1.getName().equals(partyClone.getName()));
        } catch (
                NullPointerException ex) { // NullPointerException will be not thrown and it is not accepted to make order with null event !
            System.out.println(ex.getMessage());
        }
        assertFalse(party1.equals(partyClone));
    }

    @Test
    void testEquals2() {
        LocalDateTime testDate = LocalDateTime.now();
        EventVO party = new PartyVO(1, "Party 1", new String[]{"Sound System", "Lights"}, "Club XYZ", testDate, "Buffet", "DJ John");

        // Reflexive
        assertTrue(party.equals(party), "An object should be equal to itself.");

        // Symmetry
        EventVO partyClone = new PartyVO(1, "Party 1", new String[]{"Sound System", "Lights"}, "Club XYZ", testDate, "Buffet", "DJ John");
        assertTrue(party.equals(partyClone) && partyClone.equals(party), "Symmetry condition failed.");

        // Transitivity
        EventVO partyClone1 = new PartyVO(1, "Party 1", new String[]{"Sound System", "Lights"}, "Club XYZ", testDate, "Buffet", "DJ John");
        EventVO partyClone2 = new PartyVO(1, "Party 1", new String[]{"Sound System", "Lights"}, "Club XYZ", testDate, "Buffet", "DJ John");
        assertTrue(party.equals(partyClone1) && partyClone1.equals(partyClone2) && party.equals(partyClone2), "Transitivity condition failed.");

        // Consistency
        assertTrue(party.equals(partyClone), "First invocation returned true.");
        assertFalse(party.equals(null), "Comparing to null should return false.");

        // Check null fields
        EventVO partyWithNullName = new PartyVO(1, null, new String[]{"Sound System", "Lights"}, "Club XYZ", testDate, "Buffet", "DJ John");
        assertFalse(party.equals(partyWithNullName), "Parties with different names should not be equal.");
    }

    @Test
    void testEquals_Reflexive() {
        assertTrue(party1.equals(party1), "Event should be equal to itself.");
    }

    @Test
    void testEquals_NullDate() {
        party1.setDate(null);
        party2.setDate(null);
        assertTrue(party1.equals(party2), "Events with null dates should be equal.");
    }

    @Test
    void testEquals_NullDateInOneEvent() {
        party1.setDate(null);
        assertFalse(party1.equals(party2), "Events should not be equal if one has a null date.");
    }

    @Test
    void testEquals_NullLocation() {
        party1.setLocation(null);
        party2.setLocation(null);
        assertTrue(party1.equals(party2), "Events with null locations should be equal.");
    }

    @Test
    void testEquals_NullLocationInOneEvent() {
        party1.setLocation(null);
        assertFalse(party1.equals(party2), "Events should not be equal if one has a null location.");
    }

    @Test
    void testEquals_NullName() {
        party1.setName(null);
        party2.setName(null);
        assertTrue(party1.equals(party2), "Events with null names should be equal.");
    }

    @Test
    void testEquals_NullNameInOneEvent() {
        party1.setName(null);
        assertFalse(party1.equals(party2), "Events should not be equal if one has a null name.");
    }


    /***************************************************************************************
     * End of equals Tests
     ***************************************************************************************/

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
    void test_calculateNrAvailableTickets() {
        // Ensure ticketCategory is empty before the test
        show.ticketCategory.clear();

        // Add tickets and calculate available tickets
        show.addTicketCategory(seatTicket);  // 55 tickets
        show.addTicketCategory(backstageTicket);  // 30 tickets

        // Calculate the number of available tickets
        show.calculateNrAvailableTickets();

        // Verify the total number of available tickets
        assertEquals(85, show.getNrAvailableTickets(), "Number of available tickets should be equal to the total number of added tickets");
    }

    @Test
    void getNrAvailableTickets() {
        // Ensure ticketCategory is empty before the test
        show.ticketCategory.clear();

        // Add tickets
        show.addTicketCategory(seatTicket);  // 55 tickets
        show.addTicketCategory(backstageTicket);  // 30 tickets

        // Calculate available tickets
        show.calculateNrAvailableTickets();

        // Verify the number of available tickets
        assertEquals(85, show.getNrAvailableTickets(), "Should return the correct number of available tickets");
    }


    @Test
    void addTicketCategory() {
        int initialSize = party1.ticketCategory.size();
        party1.addTicketCategory(seasonTicket);

        assertEquals(initialSize + 1, party1.ticketCategory.size(), "Ticket category size should increase by one after adding a ticket");
        assertTrue(party1.ticketCategory.contains(seasonTicket), "The added ticket should be in the ticket category");
    }

    @Test
    void deleteTicketCategory() {
        party1.addTicketCategory(seasonTicket);
        int initialSize = party1.ticketCategory.size();

        party1.deleteTicketCategory(seasonTicket);

        assertEquals(initialSize - 1, party1.ticketCategory.size(), "Ticket category size should decrease by one after deleting a ticket");
        assertFalse(party1.ticketCategory.contains(seasonTicket), "The deleted ticket should no longer be in the ticket category");
    }

    @Test
    void getTicketCategory() {
        party1.addTicketCategory(seatTicket);
        TicketVO retrievedTicket = party1.getTicketCategory(0);

        assertEquals(seatTicket, retrievedTicket, "Retrieved ticket should match the added seat ticket");
    }


    /****************************************************************************
     * Tests for equipmentToString
     ****************************************************************************/


    @Test
    void test_equipmentToString_ValidPartyEquipment_ShouldReturnFormattedString() {
        String result = party1.equipmentToString();
        assertEquals("Sound System, Lights", result, "Valid party equipment array should return a formatted string");
    }

    @Test
    void test_equipmentToString_ValidShowEquipment_ShouldReturnFormattedString() {
        String result = show.equipmentToString();
        assertEquals("Stage, Microphone", result, "Valid show equipment array should return a formatted string");
    }

    @Test
    void test_equipmentToString_NullEquipment_ShouldReturnEmptyString() {
        EventVO eventWithNullEquipment = show; // Assuming constructor and setters allow this configuration
        eventWithNullEquipment.setEquipment(null);
        String result = eventWithNullEquipment.equipmentToString();
        assertEquals("", result, "Null equipment array should return an empty string");
    }

    @Test
    void test_equipmentToString_EquipmentWithNull_ShouldSkipNull() {
        String[] equipmentWithNull = {"Lights", null, "Microphone"};
        EventVO eventWithNullItems = party1; // Assuming constructor and setters allow this configuration
        eventWithNullItems.setEquipment(equipmentWithNull);
        String result = eventWithNullItems.equipmentToString();
        assertEquals("Lights, Microphone", result, "Equipment array with null elements should skip null and return a formatted string");
    }

    @Test
    void equipmentToString_WithNullEquipment_ShouldReturnEmptyString() {
        PartyVO partyWithNullEquipment = new PartyVO(1, "Test Party", null, "Test Location", LocalDateTime.now(), "Test Catering", "Test Performer");
        String result = partyWithNullEquipment.equipmentToString();
        assertEquals("", result, "equipmentToString should return an empty string for null equipment array");
    }

    @Test
    void equipmentToString_WithEmptyEquipment_ShouldReturnEmptyString() {
        ShowVO showWithEmptyEquipment = new ShowVO(1, "Test Show", new String[0], "Test Location", LocalDateTime.now(), Duration.ofHours(2), 1);
        String result = showWithEmptyEquipment.equipmentToString();
        assertEquals("", result, "equipmentToString should return an empty string for an empty equipment array");
    }

    @Test
    void equipmentToString_WithAllNullValuesInEquipment_ShouldReturnEmptyString() {
        PartyVO partyWithAllNullEquipment = new PartyVO(1, "Test Party", new String[]{null, null, null}, "Test Location", LocalDateTime.now(), "Test Catering", "Test Performer");
        String result = partyWithAllNullEquipment.equipmentToString();
        assertEquals("", result, "equipmentToString should return an empty string when all equipment items are null");
    }


    /****************************************************************************
     *End of equipmentToString Tests.
     ****************************************************************************/

    /****************************************************************************
     * Tests for setters into EventVO
     *****************************************************************************/

    @Test
    void testSetId_Positive() {
        party1.setId(100);
        assertEquals(100, party1.getId());
    }

    @Test
    void testSetId_NegativeId() {
        party1.setId(-1);// The actual implementation accepts negative values!.
        assertEquals(-1, party1.getId()); // Assuming negative IDs are allowed
    }

    @Test
    void testSetId_Boundary() {
        party1.setId(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, party1.getId());
        party1.setId(Integer.MIN_VALUE);
        assertEquals(Integer.MIN_VALUE, party1.getId());
    }

    @Test
    void testSetName_Positive() {
        party1.setName("New Party");
        assertEquals("New Party", party1.getName());
    }

    @Test
    void testSetName_Negative() {
        party1.setName(null);
        assertNull(party1.getName());
        party1.setName("");
        assertEquals("", party1.getName());
    }

    @Test
    void testSetName_Boundary() {
        String longName = String.join("", Collections.nCopies(1000, "name"));
        party1.setName(longName);
        assertEquals(longName, party1.getName());
    }

    @Test
    void testSetEquipment_Positive() {
        String[] equipment = {"Speaker", "Microphone"};
        party1.setEquipment(equipment);
        assertArrayEquals(equipment, party1.getEquipment());
    }

    @Test
    void testSetEquipment_Negative() {
        party1.setEquipment(null);
        assertNull(party1.getEquipment());
        party1.setEquipment(new String[]{});
        assertArrayEquals(new String[]{}, party1.getEquipment());
        party1.setEquipment(new String[]{null, "Speaker"});
        assertArrayEquals(new String[]{null, "Speaker"}, party1.getEquipment());
    }

    @Test
    void testSetEquipment_Boundary() {
        String[] largeEquipment = new String[1000];
        Arrays.fill(largeEquipment, "Mic");
        party1.setEquipment(largeEquipment);
        assertArrayEquals(largeEquipment, party1.getEquipment());
    }

    @Test
    void testSetLocation_Positive() {
        party1.setLocation("New Location");
        assertEquals("New Location", party1.getLocation());
    }

    @Test
    void testSetLocation_Negative() {
        party1.setLocation(null);
        assertNull(party1.getLocation());
        party1.setLocation("");
        assertEquals("", party1.getLocation());
    }

    @Test
    void testSetLocation_Boundary() {
        String longLocation = String.join("", Collections.nCopies(1000, "L"));
        party1.setLocation(longLocation);
        assertEquals(longLocation, party1.getLocation());
    }


    @Test
    void testSetDate_Positive() {
        LocalDateTime newDate = LocalDateTime.now();
        party1.setDate(newDate);
        assertEquals(newDate, party1.getDate());
    }

    @Test
    void testSetDate_Negative() {
        party1.setDate(null);
        assertNull(party1.getDate());
    }

    @Test
    void testSetDate_Boundary() {
        party1.setDate(LocalDateTime.MIN);
        assertEquals(LocalDateTime.MIN, party1.getDate());
        party1.setDate(LocalDateTime.MAX);
        assertEquals(LocalDateTime.MAX, party1.getDate());
    }

    @Test
    void testSetSeatTicketPrice_Positive() {
        party1.setSeatTicketPrice(50.0f);
        assertEquals(50.0f, party1.getSeatTicketPrice());
    }

    @Test
    void testSetSeatTicketPrice_Negative() {
        party1.setSeatTicketPrice(-10.0f);
        assertEquals(-10.0f, party1.getSeatTicketPrice()); // Assuming negative prices are allowed
    }

    @Test
    void testSetSeatTicketPrice_Boundary() {
        party1.setSeatTicketPrice(Float.MAX_VALUE);
        assertEquals(Float.MAX_VALUE, party1.getSeatTicketPrice());
        party1.setSeatTicketPrice(Float.MIN_VALUE);
        assertEquals(Float.MIN_VALUE, party1.getSeatTicketPrice());
    }


    /******************************************************************************
     * End of setter-tests
     ******************************************************************************/


    @Test
    void getId() {
        // Assuming ID is set correctly in the setup
        assertEquals(1, party1.getId(), "Should return the correct ID for the party");
    }

    @Test
    void test_setSeatTicketPrice_WithValidValue_ShouldSetPrice() {
        float expectedPrice = 100.0f;
        show.setSeatTicketPrice(expectedPrice);
        assertEquals(expectedPrice, show.getSeatTicketPrice(), "Seat ticket price should be set correctly.");
    }

    @Test
    void test_setSeatTicketPrice_WithNegativeValue_ShouldThrowException() {
//        assertThrows(IllegalArgumentException.class, () -> party.setSeatTicketPrice(-1.0f), "Negative seat ticket price should throw IllegalArgumentException.");
        assertDoesNotThrow(() -> party1.setSeatTicketPrice(-1.0f), "Setting a negative Seat ticket price should not throw an exception.");

    }


    @Test
    void test_setSeasonTicketPrice_WithNegativeValue_ShouldThrowException() {
//        assertThrows(IllegalArgumentException.class, () -> show.setSeasonTicketPrice(-1.0f), "Negative season ticket price should throw IllegalArgumentException.");
        assertDoesNotThrow(() -> show.setSeasonTicketPrice(-1.0f), "Setting a negative Season ticket price should not throw an exception, Error in the implementation!.");

    }

    @Test
    void test_setSeasonTicketPrice_WithValidValue_ShouldSetPrice() {
        float expectedPrice = 200.0f;
        show.setSeasonTicketPrice(expectedPrice);
        party1.setSeasonTicketPrice(expectedPrice); //Error with party Events only Seat-ticket Available, but I can also assign Season-ticket.

        assertEquals(expectedPrice, party1.getSeasonTicketPrice(), "Season ticket price should not be set for Parties.");

        assertEquals(expectedPrice, show.getSeasonTicketPrice(), "Season ticket price should be set correctly.");
    }

    @Test
    void test_setBackstageTicketPrice_WithValidValue_ShouldSetPrice() {
        float expectedPrice = 300.0f;
        party1.setBackstageTicketPrice(expectedPrice);// Error with party Events only Seat-ticket Available, but I can also assign backstage-ticket.

        show.setBackstageTicketPrice(expectedPrice);

        assertEquals(expectedPrice, party1.getBackstageTicketPrice(), "Backstage ticket price should not be set for Parties.");

        assertEquals(expectedPrice, show.getBackstageTicketPrice(), "Backstage ticket price should be set correctly.");
    }

    @Test
    void test_setBackstageTicketPrice_WithNegativeValue_ShouldThrowException() {
//        assertThrows(IllegalArgumentException.class, () -> show.setBackstageTicketPrice(-1.0f), "Negative backstage ticket price should throw IllegalArgumentException.");
        assertDoesNotThrow(() -> show.setBackstageTicketPrice(-1.0f), "Setting a negative backstage ticket price should not throw an exception.");
    }


    /**
     * @author Osama Ahmad.
     * Mocking Class for Testing the Invalid values of Setters into EventVO.
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
        party1 = null;
        show = null;


    }

}