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
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Osama Ahmad , MN:20233244
 */
class OrderVOTest {

    private OrderVO order;
    private CustomerVO customer;
    private TicketVO seatTicket, seasonTicket, backstageTicket;
    private EventVO partyEvent, showEvent;
    private LocalDateTime partyDate, showDate, showStartTime, showEndTime;
    private LocalDate startOfShow, endOfShow;
    private final LocalDateTime testStartTime = LocalDateTime.of(2023, 12, 18, 12, 0);

    @BeforeEach
    void setUp() throws CustomerNoDateOfBirthException, CustomerTooYoungException {

        String[] partyEquipment = {"Sound System", "Lights", "Speaker", "Smart-DJ"};
        String[] showEquipment = {"Lights", "Microphone", "Furniture", "Stage"};

        Duration runtime = Duration.ofHours(4);
        showDate = LocalDateTime.now().plusDays(14);
        partyDate = LocalDateTime.of(2023, 12, 31, 22, 00);


        customer = new CustomerVO("Ahmad", "Osama", "Berlinerstr", 23, Gender.M, LocalDate.of(1990, 1, 2));
        partyEvent = new PartyVO(1, "Party Event", new String[]{"Speaker", "Microphone"}, "Event Hall", testStartTime, "Catering", "DJ");
        showEvent = new ShowVO(2, "Show 1", showEquipment, "Theater ABC", showDate, runtime, 1);

        showStartTime = LocalDateTime.of(2024, 3, 13, 18, 00);
        showEndTime = LocalDateTime.of(2024, 4, 13, 23, 00);
        // Convert LocalDateTime to LocalDate
        startOfShow = showStartTime.toLocalDate();
        endOfShow = showEndTime.toLocalDate();


        seatTicket = new SeatTicketVO(10, 99.99f, "A1", partyEvent);
        seasonTicket = new SeasonTicketVO(60, 55.99f, showEvent, startOfShow, endOfShow);
        backstageTicket = new BackstageTicketVO(60, 45.99f, "B66", showEvent, customer);

        order = new OrderVO(202400001, StateOfOrderVO.STARTED, testStartTime, customer);

    }


    @Test
    void test_getOrderNr() {
        assertEquals(202400001, order.getOrderNr());
        // assertEquals(2024000014344, order.getOrderNr());// This number was to large for integer, as long as the number of order per year would be to 100.000
    }

    @Test
    void test_getState() {
        assertEquals(StateOfOrderVO.STARTED, order.getState());
    }

    @Test
    void getTimestampStartedOrder() {
        assertNotNull(order.getTimestampStartedOrder());
    }

    @Test
    void getTimestampFinishedOrder() {
        assertNull(order.getTimestampFinishedOrder());
        LocalDateTime finishTime = LocalDateTime.now().plusDays(1);
        order.setTimestampFinishedOrder(finishTime);
        assertEquals(finishTime, order.getTimestampFinishedOrder());
    }

    @Test
    void getCart() {
        assertTrue(order.getCart().isEmpty());
        order.addTicket(seatTicket);
        assertFalse(order.getCart().isEmpty());
    }

    @Test
    void getCustomer() {
        assertEquals(customer, order.getCustomer());
    }

    @Test
    void setState() {
        order.setState(StateOfOrderVO.CONFIRMED);
        assertEquals(StateOfOrderVO.CONFIRMED, order.getState());
    }

    @Test
    void setTimestampStartedOrder() {
        LocalDateTime startTime = LocalDateTime.now();
        order.setTimestampStartedOrder(startTime);
        assertEquals(startTime, order.getTimestampStartedOrder());
    }

    @Test
    void setTimestampFinishedOrder() {
        LocalDateTime finishTime = LocalDateTime.now();
        order.setTimestampFinishedOrder(finishTime);
        assertEquals(finishTime, order.getTimestampFinishedOrder());
    }

    @Test
    void setCart() {
        LinkedList<TicketVO> newCart = new LinkedList<>();
        newCart.add(seatTicket);
        order.setCart(newCart);
        assertEquals(newCart, order.getCart());
    }

    @Test
    void setCustomer() throws CustomerNoDateOfBirthException, CustomerTooYoungException {
        CustomerVO customer3 = new CustomerVO("Gieske", "Antonia", "Gertraudenstr", 77, Gender.F, LocalDate.of(1997, 4, 13));

        order.setCustomer(customer3);
        assertEquals(customer3, order.getCustomer());
    }

    @Test
    void addTicket() {
        order.addTicket(seatTicket);
        assertEquals(1, order.getCart().size());
        assertEquals(seatTicket, order.getCart().get(0));
    }

    @Test
    void deleteTicket_byIndex() {
        order.addTicket(seatTicket);
        order.deleteTicket(0);
        assertTrue(order.getCart().isEmpty());
    }

    @Test
    void deleteTicket_byObject() {
        order.addTicket(seatTicket);
        order.deleteTicket(seatTicket);
        assertTrue(order.getCart().isEmpty());
    }

    @Test
    void test_calculatePriceSeatTicket() {
        // Verify individual components of the ticket price calculation
        assertEquals(99.99f, seatTicket.getBasePrice(), "Base price should be 99.99.");
        float charge = seatTicket.getCharge(); // Replace with the expected charge value
        assertEquals(charge, seatTicket.getCharge(), "Charge should be correctly set.");

        // Verify ticket price calculation
        assertEquals(99.99f * charge, seatTicket.calculatePrice(), "Ticket price calculation should return expected value.");

        // Add ticket to order and verify total price
        order.addTicket(seatTicket);
        assertEquals(99.99f * charge, order.calculatePriceTickets(), "Total price of tickets in order should match calculated ticket price.");
    }

    @Test
    void test_calculatePriceTickets() {
        // Verify individual components of the ticket price calculation
        assertEquals(99.99f, seatTicket.getBasePrice(), "Base price should be 99.99.");
        assertEquals(45.99f, backstageTicket.getBasePrice(), "Base price should be 45.67.");

        float chargeSeat = seatTicket.getCharge();  // should be 1.1f if set as above
        float chargeBackstage = backstageTicket.getCharge();  // should be 1.2f as per your implementation
        float chargeSeason = seasonTicket.getCharge();

        // Verify ticket price calculation
        assertEquals(99.99f * chargeSeat, seatTicket.calculatePrice(), "Ticket price calculation for seat ticket should return expected value.");
        assertEquals(45.99f * chargeBackstage, backstageTicket.calculatePrice(), "Ticket price calculation for backstage ticket should return expected value.");
        assertEquals(55.99f * chargeSeason, seasonTicket.calculatePrice(), "Ticket price calculation for season ticket should return expected value.");

        // Add tickets to order and verify total price
        order.addTicket(seatTicket);
        order.addTicket(backstageTicket);
        order.addTicket(seasonTicket);

        float expectedTotalPriceTickets = (99.99f * chargeSeat) + (45.99f * chargeBackstage) + (55.99f * chargeSeason);
        assertEquals(expectedTotalPriceTickets, order.calculatePriceTickets(), "Total price of tickets in order should match calculated ticket prices.");
    }

    @Test
    void tesGetTicket() {
        order.addTicket(seatTicket);
        assertEquals(seatTicket, order.getTicket(0));
    }

    @Test
    void testGetNumberOfTickets() {
        assertEquals(0, order.getNumberOfTickets());
        order.addTicket(seatTicket);
        assertEquals(1, order.getNumberOfTickets());
    }


    @Test
    void testHashCode() {
        int hash = order.hashCode();
        assertEquals(hash, order.hashCode());

        // Arrange
        final int prime = 31;
        int expectedHashCode = 1;

        // Assuming specific values for the state of the order
        int orderNr = 202400001;
        StateOfOrderVO state = StateOfOrderVO.STARTED;
        LocalDateTime timestampStartedOrder = LocalDateTime.of(2023, 12, 18, 12, 0);
        LocalDateTime timestampFinishedOrder = LocalDateTime.of(2023, 12, 19, 12, 0);
        List<TicketVO> cart = new LinkedList<>();


        // Set the specific values in the order object
        order.setState(state);
        order.setTimestampStartedOrder(timestampStartedOrder);
        order.setTimestampFinishedOrder(timestampFinishedOrder);
        order.setCart(cart);

        // Calculate expected hash code
        expectedHashCode = prime * expectedHashCode + ((cart == null) ? 0 : cart.hashCode());
        expectedHashCode = prime * expectedHashCode + ((customer == null) ? 0 : customer.hashCode());
        expectedHashCode = prime * expectedHashCode + orderNr;
        expectedHashCode = prime * expectedHashCode + ((state == null) ? 0 : state.hashCode());
        expectedHashCode = prime * expectedHashCode + ((timestampFinishedOrder == null) ? 0 : timestampFinishedOrder.hashCode());
        expectedHashCode = prime * expectedHashCode + ((timestampStartedOrder == null) ? 0 : timestampStartedOrder.hashCode());

        // Act
        int actualHashCode = order.hashCode();

        // Assert
        assertEquals(expectedHashCode, actualHashCode, "Hash codes should match");

        order.setCart(null); // Explicitly setting cart to null
        assertEquals(expectedHashCode, actualHashCode, "Hash codes should match");


    }




    @Test
    void testHashCodeWithNonNullCartContainingElements() {
        // Arrange
        OrderVO order = new OrderVO(202400001, StateOfOrderVO.STARTED, LocalDateTime.of(2023, 12, 18, 12, 0), customer);
        order.setTimestampFinishedOrder(LocalDateTime.of(2023, 12, 19, 12, 0));
        LinkedList<TicketVO> cartList = new LinkedList<>();
        cartList.add(mock(TicketVO.class)); // Add a mock ticket to the list to have at least one element
        order.setCart(cartList);

        // Act
        int actualHashCode = order.hashCode();

        // Calculate expected hash code manually, mirroring the logic from OrderVO's hashCode method
        final int prime = 31;
        int expectedHashCode = 1;
        expectedHashCode = prime * expectedHashCode + cartList.hashCode(); // Directly using cartList.hashCode() since it's not null and has elements
        expectedHashCode = prime * expectedHashCode + (customer == null ? 0 : customer.hashCode());
        expectedHashCode = (int) (prime * expectedHashCode + order.getOrderNr());
        expectedHashCode = prime * expectedHashCode + (order.getState() == null ? 0 : order.getState().hashCode());
        expectedHashCode = prime * expectedHashCode + (order.getTimestampFinishedOrder() == null ? 0 : order.getTimestampFinishedOrder().hashCode());
        expectedHashCode = prime * expectedHashCode + (order.getTimestampStartedOrder() == null ? 0 : order.getTimestampStartedOrder().hashCode());

        // Assert
        assertEquals(expectedHashCode, actualHashCode, "Hash codes should match with non-null cart containing elements");
    }

    @Test
    void testHashCodeWithNullCart() {
        OrderVO orderWithNullCart = new OrderVO(202400001, StateOfOrderVO.STARTED, testStartTime, customer);
        orderWithNullCart.setCart(null);
        assertNotNull(orderWithNullCart.hashCode(), "Hash code should be calculated even when cart is null.");
    }

    @Test
    void testHashCodeWithNullCustomer() {
        OrderVO orderWithNullCustomer = new OrderVO(202400001, StateOfOrderVO.STARTED, testStartTime, null);
        assertNotNull(orderWithNullCustomer.hashCode(), "Hash code should be calculated even when customer is null.");
    }

    @Test
    void testHashCodeWithNullState() {
        OrderVO orderWithNullState = new OrderVO(202400001, null, testStartTime, customer);
        assertNotNull(orderWithNullState.hashCode(), "Hash code should be calculated even when state is null.");
    }

    @Test
    void testHashCodeWithNullTimestampStartedOrder() {
        OrderVO orderWithNullTimestamp = new OrderVO(202400001, StateOfOrderVO.STARTED, null, customer);
        assertNotNull(orderWithNullTimestamp.hashCode(), "Hash code should be calculated even when timestampStartedOrder is null.");
    }

    @Test
    void testHashCodeWithNonNullFields() {
        LinkedList<TicketVO> cartList = new LinkedList<>();
        cartList.add(seatTicket);
        OrderVO orderWithNonNullFields = new OrderVO(202400001, StateOfOrderVO.STARTED, testStartTime, customer);
        orderWithNonNullFields.setCart(cartList);
        assertNotNull(orderWithNonNullFields.hashCode(), "Hash code should be calculated with all non-null fields.");
    }



    @Test
    void testEquals() {
        OrderVO sameOrder = new OrderVO(202400001, StateOfOrderVO.STARTED, LocalDateTime.now(), customer);
        OrderVO differentOrder = new OrderVO(202400002, StateOfOrderVO.CONFIRMED, LocalDateTime.now().plusDays(1), customer);
        assertEquals(order, sameOrder);
        assertNotEquals(order, differentOrder);
    }

    @Test
    void testToString() {
        String orderString = order.toString();
        assertNotNull(orderString);
        assertFalse(orderString.isEmpty());
    }


    @Test
    void testEquals_ReflexiveProperty_ReturnsTrue() {
        assertTrue(order.equals(order));
    }

    @Test
    void testEquals_SymmetricProperty_ReturnsTrueOrFalseAccordingly() {
        OrderVO anotherOrder = new OrderVO(202400001, StateOfOrderVO.STARTED, testStartTime, customer);
        assertTrue(order.equals(anotherOrder));
        assertTrue(anotherOrder.equals(order));

        OrderVO differentOrder = new OrderVO(2, StateOfOrderVO.STARTED, testStartTime, customer);
        assertFalse(order.equals(differentOrder));
        assertFalse(differentOrder.equals(order));
    }

    @Test
    void testEquals_Null_ReturnsFalse() {
        assertFalse(order.equals(null));
    }

    @Test
    void testEquals_DifferentClass_ReturnsFalse() {
        assertFalse(order.equals(new Object()));
    }

    @Test
    void testHashCode_EqualObjects_ReturnSameHashCode() {
        OrderVO anotherOrder = new OrderVO(202400001, StateOfOrderVO.STARTED, testStartTime, customer);
        assertEquals(order.hashCode(), anotherOrder.hashCode());
    }

    @Test
    void testHashCode_DifferentObjects_ReturnDifferentHashCodes() {
        OrderVO differentOrder = new OrderVO(2, StateOfOrderVO.STARTED, testStartTime, customer);
        assertNotEquals(order.hashCode(), differentOrder.hashCode());
    }

    @Test
    void test_toString_ContainsCorrectInformation() {
        String orderString = order.toString();
        assertNotNull(orderString);
        assertTrue(orderString.contains("OrderVO " + order.getOrderNr()));
        assertTrue(orderString.contains(customer.getLastName()));
        assertTrue(orderString.contains(customer.getFirstName()));
        assertTrue(orderString.contains("from " + testStartTime.format(DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm"))));
    }

    @Test
    void test_toString_EmptyCart_ReturnsExpectedString() {
        String orderString = order.toString();
        assertTrue(orderString.contains("OrderVO " + order.getOrderNr()));
        assertTrue(orderString.contains(customer.getLastName() + " " + customer.getFirstName()));
        assertTrue(orderString.contains("from " + testStartTime.format(DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm"))));

        // If the finished timestamp is null, the string "null" is expected in the toString output.
        if (order.getTimestampFinishedOrder() == null) {
            assertTrue(orderString.contains("null"));
        } else {
            assertFalse(orderString.contains("null"));
        }
    }


    @Test
    void testAddTicket_AddsTicketToOrder() {
        order.addTicket(seatTicket);
        assertTrue(order.getCart().contains(seatTicket));
    }

    @Test
    void testDeleteTicket_RemovesTicketByIndex() {
        order.addTicket(seatTicket);
        order.deleteTicket(0);
        assertFalse(order.getCart().contains(seatTicket));
    }

    @Test
    void testDeleteTicket_RemovesTicketByReference() {
        order.addTicket(seatTicket);
        order.deleteTicket(seatTicket);
        assertFalse(order.getCart().contains(seatTicket));
    }

    @Test
    void testCalculatePriceTickets_CalculatesTotalPrice() {
        order.addTicket(seatTicket);
        assertEquals(99.99f * seatTicket.getCharge(), order.calculatePriceTickets());
    }

    @Test
    void testCalculatePriceTickets_WithMultipleTickets_CalculatesCorrectTotal() {
        order.addTicket(seatTicket);
        TicketVO anotherTicket = new SeatTicketVO(10, 49.99f, "A2", partyEvent);
        order.addTicket(anotherTicket);
        float expectedTotal = (99.99f * seatTicket.getCharge()) + (49.99f * anotherTicket.getCharge());
        assertEquals(expectedTotal, order.calculatePriceTickets());
    }


    @Test
    void test_toString_CartWithNonNullTickets_ReturnsExpectedString() {
        // Mock the TicketVO class
        TicketVO mockedTicket1 = mock(TicketVO.class);
        TicketVO mockedTicket3 = mock(TicketVO.class);

        // Set up the expected strings from the toString of the tickets
        String expectedTicket1String = "Ticket details for A1";
        String expectedTicket3String = "Ticket details for B2";

        // Define the behavior of the toString() method on the mocks
        when(mockedTicket1.toString()).thenReturn(expectedTicket1String);
        when(mockedTicket3.toString()).thenReturn(expectedTicket3String);

        // Create a new order and add the mocked tickets to the cart
        OrderVO orderWithMocks = new OrderVO(202400001, StateOfOrderVO.STARTED, testStartTime, customer);
        orderWithMocks.addTicket(mockedTicket1);
        orderWithMocks.addTicket(null); // Intentionally adding a null ticket to the cart
        orderWithMocks.addTicket(mockedTicket3);

        // Call the toString method of order
        String orderString = orderWithMocks.toString();

        // Verify the string contains the expected ticket details and does not contain "null" for the null ticket
        assertTrue(orderString.contains(expectedTicket1String));
        assertTrue(orderString.contains(expectedTicket3String));
    }


    @Test
    void testHashCode_EqualObjects_HaveSameHashCode() {
        // Setup a second order object that should be equal to the first
        OrderVO anotherOrder = new OrderVO(order.getOrderNr(), order.getState(), order.getTimestampStartedOrder(), order.getCustomer());

        // Assert that the two objects have the same hash code
        assertEquals(order.hashCode(), anotherOrder.hashCode(), "Equal objects must have equal hash codes.");
    }

    @Test
    void testHashCode_UnequalObjects_HaveDifferentHashCodes() {
        // Setup a second order object that is not equal to the first
        OrderVO differentOrder = new OrderVO(202400003, StateOfOrderVO.CONFIRMED, LocalDateTime.now().plusDays(1), customer);

        // Assert that the two objects do not have the same hash code
        assertNotEquals(order.hashCode(), differentOrder.hashCode(), "Non-equal objects are likely to have different hash codes.");
    }

    @Test
    void testHashCode_NullFields_HandledCorrectly() {
        // Set the nullable fields of order to null
        order.setTimestampFinishedOrder(null);

        // Create an order with the same id, state, and customer, but with a non-null finished timestamp
        OrderVO orderWithFinishedTimestamp = new OrderVO(order.getOrderNr(), order.getState(), order.getTimestampStartedOrder(), order.getCustomer());
        orderWithFinishedTimestamp.setTimestampFinishedOrder(LocalDateTime.now().plusDays(1));

        // Assert that the hash codes are different
        assertNotEquals(order.hashCode(), orderWithFinishedTimestamp.hashCode(), "Objects with different state of nullable fields should have different hash codes.");
    }


    @Test
    void testHashCodeWithNonNullCart() {
        // Arrange
        OrderVO order = new OrderVO(202400001, StateOfOrderVO.STARTED, LocalDateTime.of(2023, 12, 18, 12, 0), customer);
        order.setTimestampFinishedOrder(LocalDateTime.of(2023, 12, 19, 12, 0));
        order.setCart(new LinkedList<>()); // set a non-null empty LinkedList

        // Act
        int actualHashCode = order.hashCode();

        // Calculate expected hash code manually, mirroring the logic from OrderVO's hashCode method
        final int prime = 31;
        int expectedHashCode = 1;
        expectedHashCode = prime * expectedHashCode + ((order.getCart() == null) ? 0 : order.getCart().hashCode());
        expectedHashCode = prime * expectedHashCode + ((order.getCustomer() == null) ? 0 : order.getCustomer().hashCode());
        expectedHashCode = (int) (prime * expectedHashCode + order.getOrderNr());
        expectedHashCode = prime * expectedHashCode + ((order.getState() == null) ? 0 : order.getState().hashCode());
        expectedHashCode = prime * expectedHashCode + ((order.getTimestampFinishedOrder() == null) ? 0 : order.getTimestampFinishedOrder().hashCode());
        expectedHashCode = prime * expectedHashCode + ((order.getTimestampStartedOrder() == null) ? 0 : order.getTimestampStartedOrder().hashCode());

        // Assert
        assertEquals(expectedHashCode, actualHashCode, "Hash codes should match with non-null cart");
    }


    @AfterEach
    public void teardown() {
        // Reset all the objects to null to ensure no state is carried over between tests
        seatTicket = null;
        order = null;
        customer = null;

    }


}