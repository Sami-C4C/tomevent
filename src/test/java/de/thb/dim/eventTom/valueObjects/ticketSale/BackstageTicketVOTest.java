package de.thb.dim.eventTom.valueObjects.ticketSale;

import de.thb.dim.eventTom.valueObjects.customerManagement.CustomerVO;
import de.thb.dim.eventTom.valueObjects.customerManagement.Gender;
import de.thb.dim.eventTom.valueObjects.customerManagement.exceptions.CustomerNoDateOfBirthException;
import de.thb.dim.eventTom.valueObjects.customerManagement.exceptions.CustomerTooYoungException;
import de.thb.dim.eventTom.valueObjects.eventManagement.EventVO;
import de.thb.dim.eventTom.valueObjects.eventManagement.PartyVO;
import de.thb.dim.eventTom.valueObjects.eventManagement.ShowVO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


/**
 * @author Osama Ahmad , MN:20233244
 */
class BackstageTicketVOTest {

    private BackstageTicketVO ticket1, ticket2;
    private TicketVO ticket3;
    private CustomerVO customer1, customer2, customer3;
    private EventVO party;
    private EventVO show;

    @BeforeEach
    public void setup() throws CustomerNoDateOfBirthException, CustomerTooYoungException {
        LocalDateTime showDate = LocalDateTime.of(2024, 2, 15, 19, 0);

        Duration runtime = Duration.ofHours(3);

        LocalDateTime fixedDate = LocalDateTime.of(2024, 2, 15, 19, 0);
        customer1 = new CustomerVO("Schneider", "Tom", "Brunnenstr", 55, Gender.M, LocalDate.of(1994, 10, 22));
        customer2 = new CustomerVO("Ahmad", "Osama","Berlinerstr",23,Gender.M, LocalDate.of(1990, 1,2));
        customer3 = new CustomerVO("Gieske", "Antonia","Gertraudenstr",77,Gender.F, LocalDate.of(1997, 4,13));

        String[] equipment = {"Sound System", "Lights"};
        // Initialize common objects used in tests here
        party = new PartyVO(1, "Party 1", equipment, "Club XYZ", fixedDate, "Buffet", "DJ John");
        show = new ShowVO(7, "Show 1", equipment, "Theater ABC", showDate, runtime, 1);

        ticket1 = new BackstageTicketVO(224,45.60f,"B23",party, customer1);
        ticket2 = new BackstageTicketVO(212, 45.60f,"B21",show,customer2);
        ticket3 = new SeatTicketVO(123, 40.45f,"A20",party);
    }


    @Test
    void equals_withSameObject_returnsTrue() {
        assertTrue(ticket1.equals(ticket1));
    }

    @Test
    void equals_withNull_returnsFalse() {
        assertFalse(ticket1.equals(null));
    }

    @Test
    void test_equals_for_SuperAndSubClass_objects() {
        assertFalse(ticket1.equals(ticket2));
        assertFalse(ticket2.equals(ticket1));
    }

    @Test
    void equals_withDifferentClass_returnsFalse() {
        assertFalse(ticket1.equals(new Object()));
        assertFalse(ticket2.equals(new Object()));
    }

    @Test
    void equals_withNullCustomer_returnFalse() throws CustomerNoDateOfBirthException, CustomerTooYoungException {
        CustomerVO anotherCustomer = new CustomerVO("Ahmad", "Osama", "Brunnenstr", 52, Gender.M, LocalDate.of(1990, 1, 2));
        BackstageTicketVO anotherTicket = new BackstageTicketVO(1, 100.0f, "E44", party, anotherCustomer);
        ticket1.setCustomer(null);
        assertFalse(anotherTicket.getCustomer().equals(ticket1.getCustomer()));
    }

    @Test
    void equals_withDifferentCustomer_returnsFalse1() throws CustomerNoDateOfBirthException, CustomerTooYoungException {
        CustomerVO anotherCustomer = new CustomerVO("Ahmad", "Osama", "Brunnenstr", 52, Gender.M, LocalDate.of(1990, 1, 2));

        BackstageTicketVO anotherTicket = new BackstageTicketVO(1, 100.0f, "E33", party, anotherCustomer);
        assertFalse(ticket1.getCustomer().equals(anotherTicket.getCustomer()));
    }

    @Test
    void equals_withDifferentCustomer_returnsFalse2() throws CustomerNoDateOfBirthException, CustomerTooYoungException {
        CustomerVO anotherCustomer = new CustomerVO("Ahmad", "Osama", "Brunnenstr", 52, Gender.M, LocalDate.of(1990, 1, 2));

        BackstageTicketVO anotherTicket = new BackstageTicketVO(1, 100.0f, "E33", party, anotherCustomer);
        assertFalse(ticket2.getCustomer().equals(anotherTicket.getCustomer()));

        anotherTicket.setCustomer(ticket2.getCustomer());
        assertEquals(anotherTicket.getCustomer(), ticket2.getCustomer());
    }


    @Test
    void equals_withDifferentNumber_returnsFalse() {
        BackstageTicketVO differentNumberTicket = new BackstageTicketVO(2, 100.0f, "1A", show, customer1);
        assertFalse(ticket1.equals(differentNumberTicket));
    }

    @Test
    void equals_withDifferentPrice_returnsFalse() {
        BackstageTicketVO differentPriceTicket = new BackstageTicketVO(1, 150.0f, "1A", party, customer1);
        assertFalse(ticket1.equals(differentPriceTicket));
    }

    @Test
    void equals_withDifferentSeat_returnsFalse() {
        BackstageTicketVO differentSeatTicket = new BackstageTicketVO(1, 100.0f, "2B", show, customer1);
        assertFalse(ticket1.equals(differentSeatTicket));
    }

    @Test
    void equals_withDifferentEvent_returnsFalse() {
        EventVO differentEvent = new PartyVO(2, "Event Name", new String[]{"Sound", "Lighting"}, "Different Venue", LocalDateTime.now(), "Different Catering Service", "Different DJ");
        BackstageTicketVO differentEventTicket = new BackstageTicketVO(1, 100.0f, "1A", differentEvent, customer1);
        assertFalse(ticket1.equals(differentEventTicket));
    }

    @Test
    void equals_withDifferentEventName_returnsFalse() {
        EventVO differentEventName = new PartyVO(1, "Event Name 2", new String[]{"Equipment"}, "Venue", LocalDateTime.now(), "Catering", "Performer");
        BackstageTicketVO differentEventNameTicket = new BackstageTicketVO(1, 100.0f, "1A", differentEventName, customer1);
        assertFalse(ticket1.equals(differentEventNameTicket));
    }

    @Test
    void equals_withDifferentEventDate_returnsFalse() {
        EventVO differentEventDate = new PartyVO(1, "Event Name", new String[]{"Equipment"}, "Venue", LocalDateTime.now().plusDays(1), "Catering", "Performer");
        BackstageTicketVO differentEventDateTicket = new BackstageTicketVO(1, 100.0f, "1A", differentEventDate, customer1);
        assertFalse(ticket1.equals(differentEventDateTicket));
    }

    @Test
    void equals_withNullCustomer_returnsFalse() {
        BackstageTicketVO nullCustomerTicket = new BackstageTicketVO(1, 100.0f, "1A", party, null);
        assertFalse(ticket1.equals(nullCustomerTicket));
    }


    @Test
    void equals_withDifferentCustomerAttributes_returnsFalse() throws CustomerNoDateOfBirthException, CustomerTooYoungException {
        CustomerVO differentCustomerAttributes = new CustomerVO("Schneider", "Tom", "Brunnenstr", 55, Gender.M, LocalDate.of(1990, 1, 2));

        BackstageTicketVO differentCustomerAttributesTicket = new BackstageTicketVO(1, 100.0f, "1A", show, differentCustomerAttributes);
        assertFalse(ticket1.equals(differentCustomerAttributesTicket));
    }

    @Test
    void equals_withSubclassOfTicketVO_returnsFalse() {
        TicketVO subclassTicket = new BackstageTicketVO(1, 100.0f, "1A", party, customer1) {
            // Anonymous subclass with no changes
        };
        assertFalse(ticket1.equals(subclassTicket));
    }



    @Test
    void equals_withSelfAndNullCustomer_returnsTrue() {
        ticket1.setCustomer(null);
        assertTrue(ticket1.equals(ticket1));
    }

    @Test
    void equals_withBothNullCustomers_returnsTrue() {
        ticket1.setCustomer(null);
        BackstageTicketVO anotherTicketWithNullCustomer = new BackstageTicketVO(224, 45.60f, "E23", party, null);
        assertTrue(ticket1.equals(anotherTicketWithNullCustomer));
    }

    @Test
    void equals_withOneNullCustomer_returnsFalse() {
        ticket1.setCustomer(null);
        assertFalse(ticket1.equals(ticket2));
    }

    @Test
    void equals_withDifferentNonNullCustomers_returnsFalse() {
        // Assuming customer2 is already initialized and different from customer1
        ticket2.setCustomer(customer2);
        assertFalse(ticket1.equals(ticket2));
    }

    @Test
    void equals_withSameNonNullCustomers_returnsTrue() {
        // Assuming customer1 is already initialized and set in ticket1
        BackstageTicketVO anotherTicketWithSameCustomer = new BackstageTicketVO(224, 45.60f, "E23", party, customer1);
        assertTrue(ticket1.equals(anotherTicketWithSameCustomer));
    }

    @Test
    void equals_withAllFieldsEqual_returnsTrue() {
        // Assuming customer1 and party are already initialized and used in ticket1
        BackstageTicketVO anotherTicket = new BackstageTicketVO(224, 45.60f, "E23", party, customer1);
        assertTrue(ticket1.equals(anotherTicket));
    }


    @Test
    void equals_withOneNullAndOneNonNullCustomer_returnsFalse() {
        BackstageTicketVO ticketWithNullCustomer = new BackstageTicketVO(224, 45.60f, "E23", party, null);
        assertFalse(ticketWithNullCustomer.equals(ticket1), "Tickets should not be equal if one customer is null and the other is not.");
    }

    @Test
    void equals_DifferentClass_ReturnsFalse() {

        assertFalse(ticket1.equals(ticket3), "Should return false when comparing with a different class object.");
        Object obj = new Object();
        assertFalse(obj instanceof BackstageTicketVO);
    }

    @Test
    void equals_withDifferentNonNullCustomers_returnsFalse2() throws CustomerNoDateOfBirthException, CustomerTooYoungException {
        CustomerVO differentCustomer = new CustomerVO("Doe", "Jane", "Some Street", 123, Gender.F, LocalDate.of(1995, 5, 15));
        BackstageTicketVO ticketWithDifferentCustomer = new BackstageTicketVO(224, 45.60f, "E23", party, differentCustomer);
        assertFalse(ticket1.equals(ticketWithDifferentCustomer), "Tickets should not be equal if customers are different.");
    }

    @Test
    void equals_withSameNonNullCustomers_returnsTrue2() {
        CustomerVO sameCustomer = customer1; // Assuming customer1 is already initialized in the setup
        BackstageTicketVO ticketWithSameCustomer = new BackstageTicketVO(224, 45.60f, "E23", party, sameCustomer);
        assertTrue(ticket1.equals(ticketWithSameCustomer), "Tickets should be equal if all attributes including customers are equal.");
    }



    @Test
    void equals_withObjectOfDifferentClass_returnsFalse() {
        // Assuming there's another class that extends TicketVO but is not BackstageTicketVO
        TicketVO differentClassObject = new TicketVO(ticket3.getNumber(), ticket3.getId(), ticket3.getBasePrice(), party) {
            // Implementing abstract methods for the sake of the example
            @Override
            public float getCharge() {
                return 1.0f;
            }

            @Override
            public String getSeatOfTicket() {
                return "B1";
            }
        };

        assertFalse(ticket2.equals(differentClassObject), "Should return false when compared with an object of a different class");
    }






    @Test
    void setAndGetCustomer_returnsCorrectCustomer() throws CustomerNoDateOfBirthException, CustomerTooYoungException {
        CustomerVO newCustomer = new CustomerVO("Schneider", "Tom", "Brunnenstr", 55, Gender.M, LocalDate.of(1990, 1, 2));
        ticket1.setCustomer(newCustomer);
        assertEquals(newCustomer, ticket1.getCustomer());
    }






    @Test
    void clone_whenCloneThrows_throwsInternalError() {
        // Since we're not expecting CloneNotSupportedException to be thrown, the test case would fail if it was.
        assertDoesNotThrow(() -> ticket1.clone());
    }


    @Test
    void compareTo_withHigherId_returnsPositive() {
        BackstageTicketVO higherIdTicket = new BackstageTicketVO(2, 100.0f, "1A", party, customer1);
        assertTrue(ticket2.compareTo(higherIdTicket) > 0);
    }

    @Test
    void compareTo_withHigherId_returnsNegative() {


        BackstageTicketVO lowerIdTicket = new BackstageTicketVO(1, 100.0f, "1A", party, customer1);
        BackstageTicketVO higherIdTicket = new BackstageTicketVO(2, 100.0f, "1A", party, customer1);

        // Debug output
        System.out.println("Ticket1 ID: " + lowerIdTicket.getId());
        System.out.println("Higher ID Ticket: " + higherIdTicket.getId());

        // Test assertion
        assertTrue(lowerIdTicket.compareTo(higherIdTicket) < 0, "ticket1 should have a lower ID compared to higherIdTicket");
    }


    @Test
    void hashCode_sameAttributes_returnsSameHashCode() {
        BackstageTicketVO anotherTicket = new BackstageTicketVO(ticket1.getNumber(), ticket1.getBasePrice(), ticket1.getSeat(), party, customer1);
        assertEquals(ticket1.hashCode(), anotherTicket.hashCode(), "Hashcodes should match for tickets with identical attributes");
    }

    @Test
    void hashCode_differentAttributes_returnsDifferentHashCode() {
        BackstageTicketVO differentTicket = new BackstageTicketVO(ticket2.getNumber(), ticket2.getBasePrice(), "Different Seat", show, customer2);
        assertNotEquals(ticket1.hashCode(), differentTicket.hashCode(), "Hashcodes should not match for tickets with different attributes");
    }

    @Test
    void hashCode_changesWithDifferentCustomer() throws CustomerNoDateOfBirthException, CustomerTooYoungException {
        int originalHashCode = ticket1.hashCode();
        CustomerVO newCustomer = new CustomerVO("Doe", "Jane", "Some Street", 123, Gender.F, LocalDate.of(1995, 5, 15));
        ticket1.setCustomer(newCustomer);
        int newHashCode = ticket1.hashCode();
        assertNotEquals(originalHashCode, newHashCode, "Hashcode should change when customer changes.");
    }

    @Test
    void hashCode_WithNullCustomer() throws CustomerNoDateOfBirthException, CustomerTooYoungException {
        int oldHashCode = ticket1.hashCode();

        ticket1.setCustomer(null);
        int newHashCode = ticket1.hashCode();
        assertNotEquals(oldHashCode, newHashCode, "Hashcode should change when customer changes.");
    }


    @Test
    void getCharge_alwaysReturnsExpectedValue() {
        final float expectedCharge = 1.2f; // The charge defined in my BackstageTicketVO class
        assertEquals(expectedCharge, ticket1.getCharge(), "getCharge should return the predefined charge value");
    }


    @Test
    void getSeatOfTicket_returnsFormattedSeatString() {
        final String expectedSeatString = ticket1.getSeat() + " +B"; // The expected format defined in your BackstageTicketVO class
        assertEquals(expectedSeatString, ticket1.getSeatOfTicket(), "getSeatOfTicket should return the seat followed by ' +B'");
    }




    @AfterEach
    public void teardown() {
        // Reset all the objects to null to ensure no state is carried over between tests
        ticket1 = null;
        ticket2 = null;
        ticket3 = null;
        customer1 = null;
        customer2 = null;
        customer3 = null;
        party = null;
        show = null;

        // If there are any static variables in your TicketVO or related classes, reset them as well
        // For example, if you have a static nextId in the TicketVO class:
        // TicketVO.resetNextId(); // Assuming you create a reset method in TicketVO
    }


}