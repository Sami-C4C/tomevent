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

    private BackstageTicketVO backstageTicket1, backStageTicket2;
    private TicketVO seatTicket;
    private CustomerVO customer1, customer2;
    private EventVO party;
    private EventVO show;

    Duration runtime;

    @BeforeEach
    public void setup() throws CustomerNoDateOfBirthException, CustomerTooYoungException {

        LocalDateTime showDate = LocalDateTime.of(2024, 5, 1, 13, 00);
        LocalDateTime partyDate = LocalDateTime.of(2023, 12, 31, 22, 00);


        runtime = Duration.ofHours(7);

        customer1 = new CustomerVO("Schneider", "Tom", "Brunnenstr", 55, Gender.M, LocalDate.of(1994, 10, 22));
        customer2 = new CustomerVO("Ahmad", "Osama", "Berlinerstr", 23, Gender.M, LocalDate.of(1990, 1, 2));


        String[] showEquipment = {"Lights", "Speaker", "Furniture"};

        String[] partyEquipment = {"Sound System", "Lights", "Speaker", "Smart-DJ"};

        // Initialize common objects used in tests here
        show = new ShowVO(7, "Show 1", showEquipment, "Theater ABC", showDate, runtime, 1);
        party = new PartyVO(1, "Party 1", partyEquipment, "Club XYZ", partyDate, "Buffet", "DJ John");

        backstageTicket1 = new BackstageTicketVO(224, 45.60f, "B23", show, customer1);
        backStageTicket2 = new BackstageTicketVO(212, 45.60f, "B21", show, customer2);
        seatTicket = new SeatTicketVO(123, 40.45f, "A20", party);
    }


    @Test
    void test_equals_withSameObject_returnsTrue() {
        assertTrue(backstageTicket1.equals(backstageTicket1));
    }

    @Test
    void test_equals_withNull_returnsFalse() {
        assertFalse(backstageTicket1.equals(null));
    }

    @Test
    void test_equals_for_SuperAndSubClass_objects() {
        assertFalse(backstageTicket1.equals(backStageTicket2));
        assertFalse(backStageTicket2.equals(backstageTicket1));
    }

    @Test
    void test_equals_withDifferentClass_returnsFalse() {
        assertFalse(backstageTicket1.equals(new Object()));
        assertFalse(backStageTicket2.equals(new Object()));
    }

    @Test
    void test_equals_withNullCustomer_returnFalse() throws CustomerNoDateOfBirthException, CustomerTooYoungException {
        CustomerVO anotherCustomer = new CustomerVO("Ahmad", "Osama", "Brunnenstr", 52, Gender.M, LocalDate.of(1990, 1, 2));
        BackstageTicketVO anotherTicket = new BackstageTicketVO(1, 100.0f, "E44", show, anotherCustomer);
        backstageTicket1.setCustomer(null);
        assertFalse(anotherTicket.getCustomer().equals(backstageTicket1.getCustomer()));
    }

    @Test
    void test_equals_withDifferentCustomer_returnsFalse() throws CustomerNoDateOfBirthException, CustomerTooYoungException {
        CustomerVO anotherCustomer = new CustomerVO("Ahmad", "Osama", "Brunnenstr", 52, Gender.M, LocalDate.of(1990, 1, 2));

        BackstageTicketVO anotherTicket = new BackstageTicketVO(1, 100.0f, "E33", show, anotherCustomer);
        assertFalse(backstageTicket1.getCustomer().equals(anotherTicket.getCustomer()));
    }

    @Test
    void test_equals_withSameCustomer_returnsTrue() throws CustomerNoDateOfBirthException, CustomerTooYoungException {
        CustomerVO anotherCustomer = new CustomerVO("Ahmad", "Osama", "Brunnenstr", 52, Gender.M, LocalDate.of(1990, 1, 2));

        BackstageTicketVO anotherTicket = new BackstageTicketVO(1, 100.0f, "E33", show, anotherCustomer);

        anotherTicket.setCustomer(backStageTicket2.getCustomer());
        assertEquals(anotherTicket.getCustomer(), backStageTicket2.getCustomer());
    }


    @Test
    void test_equals_withDifferentNumber_returnsFalse() {
        BackstageTicketVO differentNumberTicket = new BackstageTicketVO(2, 100.0f, "1A", show, customer1);
        assertFalse(backstageTicket1.equals(differentNumberTicket));
    }

    @Test
    void test_equals_withDifferentPrice_returnsFalse() {
        BackstageTicketVO differentPriceTicket = new BackstageTicketVO(1, 150.0f, "1A", show, customer1);
        assertFalse(backstageTicket1.equals(differentPriceTicket));
    }

    @Test
    void test_equals_withDifferentSeat_returnsFalse() {
        BackstageTicketVO differentSeatTicket = new BackstageTicketVO(1, 100.0f, "2B", show, customer1);
        assertFalse(backstageTicket1.equals(differentSeatTicket));
    }

    @Test
    void test_equals_withDifferentEvent_returnsFalse() {
        EventVO differentEvent = new ShowVO(2, "Event Name", new String[]{"Sound", "Lighting"}, "Club Treptower-Park", LocalDateTime.now(), runtime, 3);
        BackstageTicketVO differentEventTicket = new BackstageTicketVO(1, 100.0f, "1A", differentEvent, customer1);
        assertFalse(backstageTicket1.equals(differentEventTicket));
    }

    @Test
    void test_equals_withDifferentEventName_returnsFalse() {
        EventVO differentEventName = new ShowVO(2, "Matrix-Berlin", new String[]{"Sound", "Lighting"}, "Club Treptower-Park", LocalDateTime.now(), runtime, 3);
        BackstageTicketVO differentEventNameTicket = new BackstageTicketVO(1, 100.0f, "1A", differentEventName, customer1);
        assertFalse(backstageTicket1.equals(differentEventNameTicket));
    }

    @Test
    void test_equals_withDifferentEventDate_returnsFalse() {
        EventVO differentEventDate = new ShowVO(1, "Event Name", new String[]{"Equipment"}, "Venue", LocalDateTime.now().plusDays(1), runtime, 3);
        BackstageTicketVO differentEventDateTicket = new BackstageTicketVO(1, 100.0f, "1A", differentEventDate, customer1);
        assertFalse(backstageTicket1.equals(differentEventDateTicket));
    }

    @Test
    void test_equals_withNullCustomer_returnsFalse() {
        BackstageTicketVO nullCustomerTicket = new BackstageTicketVO(1, 100.0f, "1A", show, null);
        assertFalse(backstageTicket1.equals(nullCustomerTicket));
    }


    @Test
    void test_equals_withDifferentCustomerAttributes_returnsFalse() throws CustomerNoDateOfBirthException, CustomerTooYoungException {
        CustomerVO differentCustomerAttributes = new CustomerVO("Schneider", "Tom", "Brunnenstr", 55, Gender.M, LocalDate.of(1990, 1, 2));

        BackstageTicketVO differentCustomerAttributesTicket = new BackstageTicketVO(1, 100.0f, "1A", show, differentCustomerAttributes);
        assertFalse(backstageTicket1.equals(differentCustomerAttributesTicket));
    }

    @Test
    void test_equals_withSubclassOfTicketVO_returnsFalse() {
        TicketVO subclassTicket = new BackstageTicketVO(1, 100.0f, "1A", show, customer1) {
            // Anonymous subclass with no changes
        };
        assertFalse(backstageTicket1.equals(subclassTicket));
    }


    @Test
    void test_equals_withSelfAndNullCustomer_returnsTrue() {
        backstageTicket1.setCustomer(null);
        assertTrue(backstageTicket1.equals(backstageTicket1));
    }

    @Test
    void test_equals_withBothNullCustomers_returnsTrue() {
        backstageTicket1.setCustomer(null);
        BackstageTicketVO anotherTicketWithNullCustomer = new BackstageTicketVO(224, 45.60f, "E23", show, null);
        assertTrue(backstageTicket1.equals(anotherTicketWithNullCustomer));
    }

    @Test
    void test_equals_withOneNullCustomer_returnsFalse() {
        backstageTicket1.setCustomer(null);
        assertFalse(backstageTicket1.equals(backStageTicket2));
    }

    @Test
    void test_equals_withDifferentNonNullCustomers_returnsFalse() {
        // Assuming customer2 is already initialized and different from customer1
        backStageTicket2.setCustomer(customer2);
        assertFalse(backstageTicket1.equals(backStageTicket2));
    }

    @Test
    void test_equals_withSameNonNullCustomers_returnsTrue() {
        // Assuming customer1 is already initialized and set in ticket1
        BackstageTicketVO anotherTicketWithSameCustomer = new BackstageTicketVO(224, 45.60f, "E23", show, customer1);
        assertTrue(backstageTicket1.equals(anotherTicketWithSameCustomer));
    }

    @Test
    void test_equals_withAllFieldsEqual_returnsTrue() {
        // Assuming customer1 and party are already initialized and used in ticket1
        BackstageTicketVO anotherTicket = new BackstageTicketVO(224, 45.60f, "E23", show, customer1);
        assertTrue(backstageTicket1.equals(anotherTicket));
    }


    @Test
    void test_equals_withOneNullAndOneNonNullCustomer_returnsFalse() {
        BackstageTicketVO ticketWithNullCustomer = new BackstageTicketVO(224, 45.60f, "E23", show, null);
        assertFalse(ticketWithNullCustomer.equals(backstageTicket1), "Tickets should not be equal if one customer is null and the other is not.");
    }

    @Test
    void test_equals_DifferentClass_ReturnsFalse() {

        assertFalse(backstageTicket1.equals(seatTicket), "Should return false when comparing with a different class object.");
        Object obj = new Object();
        assertFalse(obj instanceof BackstageTicketVO);
    }

    @Test
    void test_equals_withDifferentNonNullCustomers_returnsFalse2() throws CustomerNoDateOfBirthException, CustomerTooYoungException {
        CustomerVO differentCustomer = new CustomerVO("Doe", "Jane", "Some Street", 123, Gender.F, LocalDate.of(1995, 5, 15));
        BackstageTicketVO ticketWithDifferentCustomer = new BackstageTicketVO(224, 45.60f, "E23", show, differentCustomer);
        assertFalse(backstageTicket1.equals(ticketWithDifferentCustomer), "Tickets should not be equal if customers are different.");
    }

    @Test
    void test_equals_withSameNonNullCustomers_returnsTrue2() {
        CustomerVO sameCustomer = customer1; // Assuming customer1 is already initialized in the setup
        BackstageTicketVO ticketWithSameCustomer = new BackstageTicketVO(224, 45.60f, "E23", show, sameCustomer);
        assertTrue(backstageTicket1.equals(ticketWithSameCustomer), "Tickets should be equal if all attributes including customers are equal.");
    }


    @Test
    void test_equals_withObjectOfDifferentClass_returnsFalse() {
        // Assuming there's another class that extends TicketVO but is not BackstageTicketVO
        TicketVO differentClassObject = new TicketVO(seatTicket.getNumber(), seatTicket.getId(), seatTicket.getBasePrice(), show) {
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

        assertFalse(backStageTicket2.equals(differentClassObject), "Should return false when compared with an object of a different class");
    }


    @Test
    void test_setAndGetCustomer_returnsCorrectCustomer() throws CustomerNoDateOfBirthException, CustomerTooYoungException {
        CustomerVO newCustomer = new CustomerVO("Schneider", "Tom", "Brunnenstr", 55, Gender.M, LocalDate.of(1990, 1, 2));
        backstageTicket1.setCustomer(newCustomer);
        assertEquals(newCustomer, backstageTicket1.getCustomer());
    }


    @Test
    void test_clone_whenCloneThrows_throwsInternalError() {
        // Since we're not expecting CloneNotSupportedException to be thrown, the test case would fail if it was.
        assertDoesNotThrow(() -> backstageTicket1.clone());
    }


    @Test
    void test_compareTo_withHigherId_returnsPositive() {

        BackstageTicketVO higherIdTicket = new BackstageTicketVO(400, 100.0f, "1A", show, customer1);
        assertTrue(higherIdTicket.compareTo(backStageTicket2) > 0);
    }

    @Test
    void test_compareTo_withHigherId_returnsNegative() {


        BackstageTicketVO lowerIdTicket = new BackstageTicketVO(1, 100.0f, "1A", show, customer1);
        BackstageTicketVO higherIdTicket = new BackstageTicketVO(2, 100.0f, "1A", show, customer1);

        // Debug output
        System.out.println("Ticket1 ID: " + lowerIdTicket.getId());
        System.out.println("Higher ID Ticket: " + higherIdTicket.getId());

        // Test assertion
        assertTrue(lowerIdTicket.compareTo(higherIdTicket) < 0, "ticket1 should have a lower ID compared to higherIdTicket");
    }


    @Test
    void test_hashCode_sameAttributes_returnsSameHashCode() {
        BackstageTicketVO anotherTicket = new BackstageTicketVO(backstageTicket1.getNumber(), backstageTicket1.getBasePrice(), backstageTicket1.getSeat(), show, customer1);
        assertEquals(backstageTicket1.hashCode(), anotherTicket.hashCode(), "Hashcodes should match for tickets with identical attributes");
    }

    @Test
    void test_hashCode_differentAttributes_returnsDifferentHashCode() {
        BackstageTicketVO differentTicket = new BackstageTicketVO(backStageTicket2.getNumber(), backStageTicket2.getBasePrice(), "Different Seat", show, customer2);
        assertNotEquals(backstageTicket1.hashCode(), differentTicket.hashCode(), "Hashcodes should not match for tickets with different attributes");
    }

    @Test
    void test_hashCode_changesWithDifferentCustomer() throws CustomerNoDateOfBirthException, CustomerTooYoungException {
        int originalHashCode = backstageTicket1.hashCode();
        CustomerVO newCustomer = new CustomerVO("Doe", "Jane", "Some Street", 123, Gender.F, LocalDate.of(1995, 5, 15));
        backstageTicket1.setCustomer(newCustomer);
        int newHashCode = backstageTicket1.hashCode();
        assertNotEquals(originalHashCode, newHashCode, "Hashcode should change when customer changes.");
    }

    @Test
    void test_hashCode_WithNullCustomer() throws CustomerNoDateOfBirthException, CustomerTooYoungException {
        int oldHashCode = backstageTicket1.hashCode();

        backstageTicket1.setCustomer(null);
        int newHashCode = backstageTicket1.hashCode();
        assertNotEquals(oldHashCode, newHashCode, "Hashcode should change when customer changes.");
    }


    @Test
    void test_getCharge_alwaysReturnsExpectedValue() {
        final float expectedCharge = 1.2f; // The charge defined in my BackstageTicketVO class
        assertEquals(expectedCharge, backstageTicket1.getCharge(), "getCharge should return the predefined charge value");
    }


    @Test
    void test_getSeatOfTicket_returnsFormattedSeatString() {
        final String expectedSeatString = backstageTicket1.getSeat() + " +B"; // The expected format defined in your BackstageTicketVO class
        assertEquals(expectedSeatString, backstageTicket1.getSeatOfTicket(), "getSeatOfTicket should return the seat followed by ' +B'");
    }




    /*********************************************************************************
     * Test setEvent of BackstageTicketVO
     *********************************************************************************/
    @Test
    void test_setShowEvent_withBackstageTicket_shouldSucceed() {
        assertDoesNotThrow(() -> backStageTicket2.setEvent(show));
    }

    @Test
    void test_setPartyEvent_withBackstageTicket_shouldThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> backstageTicket1.setEvent(party));
        assertEquals("Event shouldn't be null,also Backstage-ticket not available for Party!", exception.getMessage());
    }

    @Test
    void test_setNullEvent_withBackstageTicket_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> backstageTicket1.setEvent(null));

    }







    @AfterEach
    public void teardown() {
        // Reset all the objects to null to ensure no state is carried over between tests
        backstageTicket1 = null;
        backStageTicket2 = null;
        seatTicket = null;
        customer1 = null;
        customer2 = null;
        party = null;
        show = null;

    }


}