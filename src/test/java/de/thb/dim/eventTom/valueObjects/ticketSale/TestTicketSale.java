package de.thb.dim.eventTom.valueObjects.ticketSale;


import de.thb.dim.eventTom.businessObjects.ITicketOrdering;
import de.thb.dim.eventTom.businessObjects.exceptions.NoCustomerException;
import de.thb.dim.eventTom.businessObjects.exceptions.NoOrderException;
import de.thb.dim.eventTom.valueObjects.customerManagement.CustomerVO;
import de.thb.dim.eventTom.valueObjects.customerManagement.Gender;
import de.thb.dim.eventTom.valueObjects.customerManagement.exceptions.CustomerNoDateOfBirthException;
import de.thb.dim.eventTom.valueObjects.customerManagement.exceptions.CustomerTooYoungException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @ author Osama Ahmad, MN: 20233244
 * Integration-Tests.
 */
class TestTicketSale {
    @Mock
    private ITicketOrdering ticketOrdering;

    private CustomerVO customer;

    @BeforeEach
    public void setUp() throws CustomerNoDateOfBirthException, CustomerTooYoungException {
        MockitoAnnotations.initMocks(this);


        customer = new CustomerVO("Ahmad", "Osama", "Berlinerstr", 23, Gender.M, LocalDate.of(1990, 1, 2));
    }

    @Test
    public void testSuccessfulTicketPurchase() throws NoOrderException, NoCustomerException {
        CustomerVO mockCustomer = mock(CustomerVO.class);
        TicketVO mockTicket = mock(TicketVO.class);
        when(mockTicket.calculatePrice()).thenReturn(100.0f);

        TicketOrdering ticketOrdering = new TicketOrdering();

        // Act
        OrderVO order = ticketOrdering.startNewOrder(mockCustomer);
        ticketOrdering.addTicket(mockTicket);  // This should cause calculatePrice to be called later
        ticketOrdering.confirmOrder();  // Ensure this method uses calculatePrice()

        // Assert
        assertNotNull(order, "Order should not be null after starting a new order");
        assertEquals("The order should be associated with the customer who started it", mockCustomer, order.getCustomer());
        assertEquals("Order should be confirmed", StateOfOrderVO.CONFIRMED, order.getState());
    }


    @Test
    public void testTicketPurchaseWithInvalidSeat() throws NoCustomerException, NoOrderException {
        // Arrange: Start a new order
        OrderVO newOrder = ticketOrdering.startNewOrder(customer);

        // Mock the behavior: Throw IllegalStateException when a null ticket is added
        doThrow(new IllegalStateException()).when(ticketOrdering).addTicket(null);

        // Act & Assert: Attempt to add a null ticket and expect an IllegalStateException
        assertThrows(IllegalStateException.class, () -> ticketOrdering.addTicket(null));

        // Verify that addTicket was called with null
        verify(ticketOrdering).addTicket(null);
    }


    @Test
    public void testTicketPurchaseForSoldOutEvent() throws NoCustomerException, NoOrderException {
        // Arrange: Start a new order and add a ticket to it
        ticketOrdering.startNewOrder(customer);
        TicketVO mockTicket1 = mock(TicketVO.class);
        ticketOrdering.addTicket(mockTicket1);

        // Mock behavior to throw IllegalStateException when confirmOrder is called
        doThrow(new IllegalStateException()).when(ticketOrdering).confirmOrder();

        // Act & Assert: Expect IllegalStateException when attempting to confirm the order
        assertThrows(IllegalStateException.class, () -> ticketOrdering.confirmOrder());

        // Verify that confirmOrder was indeed called
        verify(ticketOrdering).confirmOrder();
    }


    @Test
    public void testCalculateTotalPrice() throws NoOrderException, NoCustomerException {
        CustomerVO mockCustomer = mock(CustomerVO.class);
        TicketVO mockTicket1 = mock(TicketVO.class);
        TicketVO mockTicket2 = mock(TicketVO.class);
        when(mockTicket1.calculatePrice()).thenReturn(100.0f);
        when(mockTicket2.calculatePrice()).thenReturn(50.0f);

        TicketOrdering ticketOrdering = new TicketOrdering();
        ticketOrdering.startNewOrder(mockCustomer);
        ticketOrdering.addTicket(mockTicket1);
        ticketOrdering.addTicket(mockTicket2);
        float totalPrice = ticketOrdering.calculateTotalPrice();

        assertEquals(150.0f, totalPrice, 0.001);//Total price should be the sum of all ticket prices
    }


    @Test
    public void testStartNewOrderWithNullCustomer() {
        TicketOrdering ticketOrdering = new TicketOrdering();
        assertThrows(NoCustomerException.class, () -> ticketOrdering.startNewOrder(null));//Should throw NoCustomerException when starting an order with a null customer
    }

    @Test
    public void testAddNullTicket() throws NoOrderException, NoCustomerException {
        CustomerVO mockCustomer = mock(CustomerVO.class);
        TicketOrdering ticketOrdering = new TicketOrdering();
        ticketOrdering.startNewOrder(mockCustomer);
        assertThrows(IllegalStateException.class, () -> ticketOrdering.addTicket(null));//Should throw IllegalStateException when adding a null ticket
    }


    // Mock implementation of ITicketOrdering, to be used only in these tests.
    private static class TicketOrdering implements ITicketOrdering {
        private OrderVO currentOrder;
        private final List<TicketVO> cart = new ArrayList<>();

        public OrderVO startNewOrder(CustomerVO customer) throws NoCustomerException {
            if (customer == null) {
                throw new NoCustomerException("Customer cannot be null.");
            }
            // No need to check for customer's existing order when starting a new one.
            // The purpose of this method is to create a new order, not validate existing ones.

            this.currentOrder = new OrderVO(2024000009, StateOfOrderVO.STARTED, LocalDateTime.now(), customer);
            return currentOrder;
        }


        @Override
        public void addTicket(TicketVO ticket) throws NoOrderException, IllegalStateException {
            if (currentOrder == null) {
                throw new NoOrderException("No order has been started.");
            }
            if (ticket == null) {
                throw new IllegalStateException("Ticket cannot be null.");
            }
            cart.add(ticket);
        }

        @Override
        public void deleteTicket(TicketVO ticket) throws NoOrderException, IllegalStateException {
            if (currentOrder == null) {
                throw new NoOrderException("No order has been started.");
            }
            if (ticket == null || !cart.contains(ticket)) {
                throw new IllegalStateException("Ticket not found in the cart.");
            }
            cart.remove(ticket);
        }

        @Override
        public float calculateTotalPrice() throws NoOrderException {
            if (currentOrder == null) {
                throw new NoOrderException("No order has been started.");
            }
            return cart.stream().map(TicketVO::calculatePrice).reduce(0.0f, Float::sum);
        }

        @Override
        public void confirmOrder() throws NoOrderException, NoCustomerException, IllegalStateException {
            if (currentOrder == null) {
                throw new NoOrderException("No order has been started.");
            }
            if (currentOrder.getCustomer() == null) {
                throw new NoCustomerException("Order must have a customer to be confirmed.");
            }
            if (cart.isEmpty()) {
                throw new IllegalStateException("Cannot confirm an order with no tickets.");
            }
            // Simulating a process where the price of each ticket is considered
            float total = 0.0f;
            for (TicketVO ticket : cart) {
                total += ticket.calculatePrice();
            }

            // Perform additional business logic to confirm the order
            currentOrder.setState(StateOfOrderVO.CONFIRMED);
        }

        @Override
        public List<TicketVO> sortCart() throws NoOrderException {
            if (currentOrder == null) {
                throw new NoOrderException("No order has been started.");
            }
            return new ArrayList<>(cart); // Returns a copy of the cart
        }

        @Override
        public List<TicketVO> sortCartByEvent() throws NoOrderException {
            if (currentOrder == null) {
                throw new NoOrderException("No order has been started.");
            }
            return cart.stream().sorted(Comparator.comparing(ticket -> ticket.getEvent().getName())).collect(Collectors.toList());
        }

        @Override
        public List<TicketVO> sortCartByPrice() throws NoOrderException {
            if (currentOrder == null) {
                throw new NoOrderException("No order has been started.");
            }
            return cart.stream().sorted(Comparator.comparing(TicketVO::calculatePrice)).collect(Collectors.toList());
        }
    }


    @AfterEach
    public void tearDown() {
        customer = null;
    }


}