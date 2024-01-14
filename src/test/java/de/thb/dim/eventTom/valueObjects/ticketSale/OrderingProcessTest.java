package de.thb.dim.eventTom.valueObjects.ticketSale;

import de.thb.dim.eventTom.businessObjects.ITicketOrdering;

import de.thb.dim.eventTom.businessObjects.exceptions.NoCustomerException;
import de.thb.dim.eventTom.businessObjects.exceptions.NoOrderException;
import de.thb.dim.eventTom.valueObjects.customerManagement.CustomerVO;
import de.thb.dim.eventTom.valueObjects.customerManagement.Gender;
import de.thb.dim.eventTom.valueObjects.eventManagement.EventVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


/**
 * @author Osama Ahmad, MN:20233244
 * Integration-Tests for process-diagram in Project-description.
 */
class OrderingProcessTest {


    private ITicketOrdering ticketOrdering;
    private CustomerVO customer;
    private TicketVO ticket;
    private OrderVO order;

    @BeforeEach
    void setUp() {

        // Initialize your mock objects and classes here
        ticketOrdering = new TicketOrdering();
        customer = mock(CustomerVO.class);
        ticket = mock(TicketVO.class);
    }

    @Test
    void startNewOrder_ShouldCreateOrder() throws NoCustomerException {
        OrderVO newOrder = ticketOrdering.startNewOrder(customer);
        assertNotNull(newOrder, "New order should be created and not null");
    }

    @Test
    void addTicket_ShouldAddTicketToOrder() throws NoOrderException, NoCustomerException {
        OrderVO newOrder = ticketOrdering.startNewOrder(customer);
        ticketOrdering.addTicket(ticket);
        // Check if the ticket is added to the order
        assertTrue(newOrder.getCart().contains(ticket), "Ticket should be added to the order");
    }

    @Test
    void deleteTicket_ShouldRemoveTicketFromOrder() throws NoOrderException, NoCustomerException {
        OrderVO newOrder = ticketOrdering.startNewOrder(customer);
        ticketOrdering.addTicket(ticket);
        ticketOrdering.deleteTicket(ticket);
        // Check if the ticket is removed from the order
        assertFalse(newOrder.getCart().contains(ticket), "Ticket should be removed from the order");
    }

    @Test
    void confirmOrder_ShouldConfirmOrder() throws NoOrderException, NoCustomerException {
        OrderVO newOrder = ticketOrdering.startNewOrder(customer);
        ticketOrdering.addTicket(ticket);
        ticketOrdering.confirmOrder();

    }

    @Test
    void calculateTotalPrice_ShouldReturnCorrectPrice() throws NoOrderException, NoCustomerException {
        OrderVO newOrder = ticketOrdering.startNewOrder(customer);
        ticketOrdering.addTicket(ticket);
        float totalPrice = ticketOrdering.calculateTotalPrice();
    }

    @Test
    void sortCartByEvent_ShouldSortTicketsByEventName() throws NoOrderException, NoCustomerException {
        // Start a new order for the customer
        OrderVO newOrder = ticketOrdering.startNewOrder(customer);
        assertNotNull(newOrder, "New order should be created and not null");

        // Mock EventVO objects
        EventVO eventA = mock(EventVO.class);
        EventVO eventB = mock(EventVO.class);

        // Configure the mock EventVO objects to return names
        when(eventA.getName()).thenReturn("Party-Test");
        when(eventB.getName()).thenReturn("Show-Test");

        // Mock TicketVO objects
        TicketVO ticketA = mock(TicketVO.class);
        TicketVO ticketB = mock(TicketVO.class);

        // Configure TicketVO mocks to return mock EventVO objects
        when(ticketA.getEvent()).thenReturn(eventA);
        when(ticketB.getEvent()).thenReturn(eventB);

        // Add tickets to order
        ticketOrdering.addTicket(ticketB);
        ticketOrdering.addTicket(ticketA);

        // Sort tickets
        List<TicketVO> sortedTickets = ticketOrdering.sortCartByEvent();

        // Assertions
        assertEquals("Party-Test", sortedTickets.get(0).getEvent().getName(), "Tickets should be sorted by event name in ascending order");
        assertEquals("Show-Test", sortedTickets.get(1).getEvent().getName(), "Tickets should be sorted by event name in ascending order");
    }


    @Test
    void sortCartByPrice_ShouldSortTicketsByPrice() throws NoOrderException, NoCustomerException {
        OrderVO newOrder = ticketOrdering.startNewOrder(customer);
        // Assuming that tickets have prices 200.0f and 100.0f respectively
        TicketVO cheaperTicket = mock(TicketVO.class);
        TicketVO expensiveTicket = mock(TicketVO.class);
        // Configure mocks to return prices
        when(cheaperTicket.calculatePrice()).thenReturn(100.0f);
        when(expensiveTicket.calculatePrice()).thenReturn(200.0f);
        ticketOrdering.addTicket(expensiveTicket);
        ticketOrdering.addTicket(cheaperTicket);
        List<TicketVO> sortedTickets = ticketOrdering.sortCartByPrice();
        assertEquals(100.0f, sortedTickets.get(0).calculatePrice(), "Tickets should be sorted by price in ascending order");
        assertEquals(200.0f, sortedTickets.get(1).calculatePrice(), "Tickets should be sorted by price in ascending order");
    }

    @Test
    void sendOrderEmail_ShouldSendEmail() throws NoOrderException, NoCustomerException {

        // Create an instance of the mock class where additional methods are defined
        TicketOrdering ticketOrderingImpl = new TicketOrdering();

        // Start a new order
        OrderVO newOrder = ticketOrderingImpl.startNewOrder(customer);
        assertNotNull(newOrder, "New order should be created and not null");

        // Add a ticket to the order
        ticketOrderingImpl.addTicket(ticket);

        // Confirm the order
        ticketOrderingImpl.confirmOrder();

        // Now, send an email using the same ticketOrdering instance
        String emailResult = ticketOrderingImpl.sendOrderEmail(newOrder);

        // Verify the email was sent successfully
        assertEquals("Email sent successfully", emailResult, "An email should be sent successfully after confirming the order");
    }


    @Test
    void printTickets_ShouldPrintTickets() throws NoOrderException, NoCustomerException {
        // Create an instance of TicketOrdering
        TicketOrdering ticketOrderingImpl = new TicketOrdering();

        // Assuming you have a mock for CustomerVO
        when(customer.getFirstName()).thenReturn("Osama");
        when(customer.getLastName()).thenReturn("Ahmad");
        when(customer.getGender()).thenReturn(Gender.M);


        // Start a new order and add a ticket
        OrderVO newOrder = ticketOrderingImpl.startNewOrder(customer);
        ticketOrderingImpl.addTicket(ticket);

        // Confirm the order
        ticketOrderingImpl.confirmOrder();

        // Check if the order is confirmed
        assertTrue(ticketOrderingImpl.isOrderConfirmed(), "Order should be confirmed before printing tickets");

        // Print the tickets
        String printResult = ticketOrderingImpl.printTickets(newOrder);
        assertEquals("Tickets printed successfully", printResult, "Tickets should be printed successfully after confirming the order");
    }


    @Test
    void completeOrderProcess_ShouldHandleAllSteps() throws NoCustomerException, NoOrderException {
        // Create an instance of the mock class where additional methods are defined
        TicketOrdering ticketOrderingImpl = new TicketOrdering();

        // Start a new order for the customer
        OrderVO order = ticketOrderingImpl.startNewOrder(customer);
        assertNotNull(order, "New order should be created and not null");

        // Add tickets to the order
        ticketOrderingImpl.addTicket(ticket);
        assertTrue(order.getCart().contains(ticket), "Ticket should be added to the order");

        // Confirm the order
        ticketOrderingImpl.confirmOrder();

        // The order should be confirmed at this point, let's check the internal state
        assertTrue(ticketOrderingImpl.isOrderConfirmed(), "Order should be marked as confirmed.");

        // Calculate the total price of the tickets in the order
        float totalPrice = ticketOrderingImpl.calculateTotalPrice();
        // Assert the total price based on the ticket prices

        // Print the tickets using the mock class instance
        String printResult = ticketOrderingImpl.printTickets(order);
        assertEquals("Tickets printed successfully", printResult, "Tickets should be printed successfully after confirming the order");

        // Send the order email using the mock class instance
        String emailResult = ticketOrderingImpl.sendOrderEmail(order);
        assertEquals("Email sent successfully", emailResult, "An email should be sent successfully after confirming the order");

        // Mark the order as finished
        order.setState(StateOfOrderVO.FINISHED);

        // Final assertion to ensure that the order has been marked as FINISHED
        assertEquals(StateOfOrderVO.FINISHED, order.getState(), "The order should be marked as FINISHED.");
    }


    // Mock implementation of ITicketOrdering, to be used only in these tests.
    private static class TicketOrdering implements ITicketOrdering {
        private LocalDateTime testStartTime = LocalDateTime.of(2024, 1, 10, 9, 0);
        private OrderVO currentOrder;
        private boolean isOrderConfirmed;

        @Override
        public OrderVO startNewOrder(CustomerVO customer) throws NoCustomerException {
            if (customer == null) {
                throw new NoCustomerException("Customer information is required to start an order.");
            }
            currentOrder = new OrderVO(202400001, StateOfOrderVO.STARTED, testStartTime, customer); // Simulate creating a new order with a unique ID
            isOrderConfirmed = false;
            return currentOrder;
        }

        @Override
        public void addTicket(TicketVO ticket) throws NoOrderException, IllegalStateException {
            if (currentOrder == null) {
                throw new NoOrderException("No order started.");
            }
            if (ticket == null) {
                throw new IllegalStateException("Cannot add a null ticket to the order.");
            }
            currentOrder.addTicket(ticket); // Simulate adding a ticket to the order
        }

        @Override
        public void deleteTicket(TicketVO ticket) throws NoOrderException, IllegalStateException {
            if (currentOrder == null) {
                throw new NoOrderException("No order started.");
            }
            currentOrder.deleteTicket(ticket); // Simulate deleting a ticket from the order
        }

        @Override
        public float calculateTotalPrice() throws NoOrderException {
            if (currentOrder == null) {
                throw new NoOrderException("No order started.");
            }
            // Simulate calculating the total price
            return currentOrder.getCart().stream().map(TicketVO::calculatePrice).reduce(0f, Float::sum);
        }

        @Override
        public void confirmOrder() throws NoOrderException, NoCustomerException, IllegalStateException {
            if (currentOrder == null) {
                throw new NoOrderException("No order started.");
            }
            if (currentOrder.getCustomer() == null) {
                throw new NoCustomerException("No customer associated with the order.");
            }
            // Simulate the logic to confirm an order
            isOrderConfirmed = true;
        }

        @Override
        public List<TicketVO> sortCart() throws NoOrderException {
            if (currentOrder == null) {
                throw new NoOrderException("No order started.");
            }
            // Simulate sorting the cart
            return currentOrder.getCart().stream().sorted().collect(Collectors.toList());
        }

        @Override
        public List<TicketVO> sortCartByEvent() throws NoOrderException {
            if (currentOrder == null) {
                throw new NoOrderException("No order started.");
            }
            // Sort the cart by event details (e.g., event name, event date)
            return currentOrder.getCart().stream()
                    .sorted(Comparator.comparing(ticket -> ticket.getEvent().getName()))
                    .collect(Collectors.toList());
        }

        @Override
        public List<TicketVO> sortCartByPrice() throws NoOrderException {
            if (currentOrder == null) {
                throw new NoOrderException("No order started.");
            }
            // Sort the cart by ticket price
            return currentOrder.getCart().stream()
                    .sorted(Comparator.comparing(TicketVO::calculatePrice))
                    .collect(Collectors.toList());
        }


        public String sendOrderEmail(OrderVO order) throws NoOrderException {
            if (!isOrderConfirmed) {
                throw new NoOrderException("Order must be confirmed before sending email.");
            }
            // Simulate sending an order email
            StringBuilder emailBuilder = new StringBuilder();
            emailBuilder.append("Sending email...\n")
                    .append("Order successfully sent to the email of customer: ")
                    .append("Osama.Ahmad@mail.com")//order.getCustomer().getEmail()
                    .append("\n");
            String emailMessage = emailBuilder.toString();
            System.out.println(emailMessage);
            return "Email sent successfully";
        }


        public String printTickets(OrderVO order) throws NoOrderException {
            if (!isOrderConfirmed) {
                throw new NoOrderException("Order must be confirmed before printing tickets.");
            }
            // Simulate printing tickets
            StringBuilder printBuilder = new StringBuilder();
            printBuilder.append("Customer: ")
                    .append(order.getCustomer().getFirstName())
                    .append(" ")
                    .append(order.getCustomer().getLastName())
                    .append(".\nPrinting Order Information:\n")
                    .append("Firstname: ").append(order.getCustomer().getFirstName()).append(".\n")
                    .append("Lastname: ").append(order.getCustomer().getLastName()).append(".\n")
                    .append("Gender: ").append(order.getCustomer().getGender()).append(".\n")
                    .append("Bought on: ").append(order.getTimestampStartedOrder().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                    .append(".\n-------------------\n");
            String printMessage = printBuilder.toString();
            System.out.println(printMessage);
            return "Tickets printed successfully";
        }

        public boolean isOrderConfirmed() {
            return isOrderConfirmed;
        }
    }


}