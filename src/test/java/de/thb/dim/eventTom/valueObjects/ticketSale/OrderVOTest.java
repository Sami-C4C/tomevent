package de.thb.dim.eventTom.valueObjects.ticketSale;

import de.thb.dim.eventTom.valueObjects.customerManagement.CustomerVO;
import de.thb.dim.eventTom.valueObjects.customerManagement.Gender;
import de.thb.dim.eventTom.valueObjects.customerManagement.exceptions.CustomerNoDateOfBirthException;
import de.thb.dim.eventTom.valueObjects.customerManagement.exceptions.CustomerTooYoungException;
import de.thb.dim.eventTom.valueObjects.eventManagement.EventVO;
import de.thb.dim.eventTom.valueObjects.eventManagement.PartyVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

    private OrderVO order, order2;
    private CustomerVO customer;
    private TicketVO ticket;
    private EventVO event;
    private final LocalDateTime testStartTime = LocalDateTime.of(2023, 12, 18, 12, 0);

    @BeforeEach
    void setUp() throws CustomerNoDateOfBirthException, CustomerTooYoungException {
        customer = new CustomerVO("Ahmad", "Osama", "Berlinerstr", 23, Gender.M, LocalDate.of(1990, 1, 2));
        event = new PartyVO(1, "Party Event", new String[]{"Speaker", "Microphone"}, "Event Hall", testStartTime, "Catering", "DJ");
        ticket = new SeatTicketVO(10, 99.99f, "A1", event);
        order = new OrderVO(1, StateOfOrderVO.STARTED, testStartTime, customer);

    }

    @Test
    void getOrderNr() {
        assertEquals(1, order.getOrderNr());
    }

    @Test
    void getState() {
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
        order.addTicket(ticket);
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
        newCart.add(ticket);
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
        order.addTicket(ticket);
        assertEquals(1, order.getCart().size());
        assertEquals(ticket, order.getCart().get(0));
    }

    @Test
    void deleteTicket_byIndex() {
        order.addTicket(ticket);
        order.deleteTicket(0);
        assertTrue(order.getCart().isEmpty());
    }

    @Test
    void deleteTicket_byObject() {
        order.addTicket(ticket);
        order.deleteTicket(ticket);
        assertTrue(order.getCart().isEmpty());
    }

    @Test
    void calculatePriceTickets() {
        // Verify individual components of the ticket price calculation
        assertEquals(99.99f, ticket.getBasePrice(), "Base price should be 99.99.");
        float charge = ticket.getCharge(); // Replace with the expected charge value
        assertEquals(charge, ticket.getCharge(), "Charge should be correctly set.");

        // Verify ticket price calculation
        assertEquals(99.99f * charge, ticket.calculatePrice(), "Ticket price calculation should return expected value.");

        // Add ticket to order and verify total price
        order.addTicket(ticket);
        assertEquals(99.99f * charge, order.calculatePriceTickets(), "Total price of tickets in order should match calculated ticket price.");
    }

    @Test
    void getTicket() {
        order.addTicket(ticket);
        assertEquals(ticket, order.getTicket(0));
    }

    @Test
    void getNumberOfTickets() {
        assertEquals(0, order.getNumberOfTickets());
        order.addTicket(ticket);
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
        int orderNr = 1;
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
    void testEquals() {
        OrderVO sameOrder = new OrderVO(1, StateOfOrderVO.STARTED, LocalDateTime.now(), customer);
        OrderVO differentOrder = new OrderVO(2, StateOfOrderVO.CONFIRMED, LocalDateTime.now().plusDays(1), customer);
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
    void equals_ReflexiveProperty_ReturnsTrue() {
        assertTrue(order.equals(order));
    }

    @Test
    void equals_SymmetricProperty_ReturnsTrueOrFalseAccordingly() {
        OrderVO anotherOrder = new OrderVO(1, StateOfOrderVO.STARTED, testStartTime, customer);
        assertTrue(order.equals(anotherOrder));
        assertTrue(anotherOrder.equals(order));

        OrderVO differentOrder = new OrderVO(2, StateOfOrderVO.STARTED, testStartTime, customer);
        assertFalse(order.equals(differentOrder));
        assertFalse(differentOrder.equals(order));
    }

    @Test
    void equals_Null_ReturnsFalse() {
        assertFalse(order.equals(null));
    }

    @Test
    void equals_DifferentClass_ReturnsFalse() {
        assertFalse(order.equals(new Object()));
    }

    @Test
    void hashCode_EqualObjects_ReturnSameHashCode() {
        OrderVO anotherOrder = new OrderVO(1, StateOfOrderVO.STARTED, testStartTime, customer);
        assertEquals(order.hashCode(), anotherOrder.hashCode());
    }

    @Test
    void hashCode_DifferentObjects_ReturnDifferentHashCodes() {
        OrderVO differentOrder = new OrderVO(2, StateOfOrderVO.STARTED, testStartTime, customer);
        assertNotEquals(order.hashCode(), differentOrder.hashCode());
    }

    @Test
    void toString_ContainsCorrectInformation() {
        String orderString = order.toString();
        assertNotNull(orderString);
        assertTrue(orderString.contains("OrderVO " + order.getOrderNr()));
        assertTrue(orderString.contains(customer.getLastName()));
        assertTrue(orderString.contains(customer.getFirstName()));
        assertTrue(orderString.contains("from " + testStartTime.format(DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm"))));
    }

    @Test
    void toString_EmptyCart_ReturnsExpectedString() {
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
    void addTicket_AddsTicketToOrder() {
        order.addTicket(ticket);
        assertTrue(order.getCart().contains(ticket));
    }

    @Test
    void deleteTicket_RemovesTicketByIndex() {
        order.addTicket(ticket);
        order.deleteTicket(0);
        assertFalse(order.getCart().contains(ticket));
    }

    @Test
    void deleteTicket_RemovesTicketByReference() {
        order.addTicket(ticket);
        order.deleteTicket(ticket);
        assertFalse(order.getCart().contains(ticket));
    }

    @Test
    void calculatePriceTickets_CalculatesTotalPrice() {
        order.addTicket(ticket);
        assertEquals(99.99f * ticket.getCharge(), order.calculatePriceTickets());
    }

    @Test
    void calculatePriceTickets_WithMultipleTickets_CalculatesCorrectTotal() {
        order.addTicket(ticket);
        TicketVO anotherTicket = new SeatTicketVO(10, 49.99f, "A2", event);
        order.addTicket(anotherTicket);
        float expectedTotal = (99.99f * ticket.getCharge()) + (49.99f * anotherTicket.getCharge());
        assertEquals(expectedTotal, order.calculatePriceTickets());
    }


    @Test
    void toString_CartWithNonNullTickets_ReturnsExpectedString() {
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
        OrderVO orderWithMocks = new OrderVO(1, StateOfOrderVO.STARTED, testStartTime, customer);
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
    void hashCode_EqualObjects_HaveSameHashCode() {
        // Setup a second order object that should be equal to the first
        OrderVO anotherOrder = new OrderVO(order.getOrderNr(), order.getState(), order.getTimestampStartedOrder(), order.getCustomer());

        // Assert that the two objects have the same hash code
        assertEquals(order.hashCode(), anotherOrder.hashCode(), "Equal objects must have equal hash codes.");
    }

    @Test
    void hashCode_UnequalObjects_HaveDifferentHashCodes() {
        // Setup a second order object that is not equal to the first
        OrderVO differentOrder = new OrderVO(2, StateOfOrderVO.CONFIRMED, LocalDateTime.now().plusDays(1), customer);

        // Assert that the two objects do not have the same hash code
        assertNotEquals(order.hashCode(), differentOrder.hashCode(), "Non-equal objects are likely to have different hash codes.");
    }

    @Test
    void hashCode_NullFields_HandledCorrectly() {
        // Set the nullable fields of order to null
        order.setTimestampFinishedOrder(null);

        // Create an order with the same id, state, and customer, but with a non-null finished timestamp
        OrderVO orderWithFinishedTimestamp = new OrderVO(order.getOrderNr(), order.getState(), order.getTimestampStartedOrder(), order.getCustomer());
        orderWithFinishedTimestamp.setTimestampFinishedOrder(LocalDateTime.now().plusDays(1));

        // Assert that the hash codes are different
        assertNotEquals(order.hashCode(), orderWithFinishedTimestamp.hashCode(), "Objects with different state of nullable fields should have different hash codes.");
    }


}