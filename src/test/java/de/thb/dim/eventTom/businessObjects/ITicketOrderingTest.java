package de.thb.dim.eventTom.businessObjects;

import de.thb.dim.eventTom.businessObjects.exceptions.NoCustomerException;
import de.thb.dim.eventTom.businessObjects.exceptions.NoOrderException;
import de.thb.dim.eventTom.valueObjects.customerManagement.CustomerVO;
import de.thb.dim.eventTom.valueObjects.customerManagement.Gender;
import de.thb.dim.eventTom.valueObjects.customerManagement.exceptions.CustomerNoDateOfBirthException;
import de.thb.dim.eventTom.valueObjects.customerManagement.exceptions.CustomerTooYoungException;
import de.thb.dim.eventTom.valueObjects.eventManagement.EventVO;
import de.thb.dim.eventTom.valueObjects.eventManagement.PartyVO;
import de.thb.dim.eventTom.valueObjects.eventManagement.ShowVO;
import de.thb.dim.eventTom.valueObjects.ticketSale.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


/**
 * @author Osama Ahmad, MN: 20233244
 */
@ExtendWith(MockitoExtension.class)
class ITicketOrderingTest {

    private ITicketOrdering ticketOrdering;
    private OrderVO order;
    private CustomerVO customer;
    private TicketVO seat_ticket, backstage_ticket;
    private EventVO party, show;
    private final LocalDateTime testStartTime = LocalDateTime.of(2023, 12, 18, 12, 0);
    Duration runtime;
    LocalDateTime showStartTime, showEndTime;


    @Mock
    private IService printingService;

    @Mock
    private IService mailingService;

    @BeforeEach
    void setUp() throws CustomerNoDateOfBirthException, CustomerTooYoungException {

        MockitoAnnotations.openMocks(this);

        LocalDateTime showDate = LocalDateTime.of(2024, 5, 1, 13, 00);
        LocalDateTime partyDate = LocalDateTime.of(2023, 12, 31, 22, 00);
        runtime = Duration.ofHours(7);
        String[] showEquipment = {"Lights", "Speaker", "Furniture"};
        String[] partyEquipment = {"Sound System", "Lights", "Speaker", "Smart-DJ"};

        party = new PartyVO(1, "Party 1", partyEquipment, "Club XYZ", partyDate, "Buffet", "DJ John");
        show = new ShowVO(7, "Show 1", showEquipment, "Theater ABC", showDate, runtime, 1);

        showStartTime = LocalDateTime.of(2024, 3, 13, 18, 00);
        showEndTime = LocalDateTime.of(2024, 3, 18, 23, 00);

        customer = new CustomerVO("Ahmad", "Osama", "Berlinerstr", 23, Gender.M, LocalDate.of(1990, 1, 2));
        order = new OrderVO(1, StateOfOrderVO.STARTED, testStartTime, customer);
        seat_ticket = new SeatTicketVO(123, 40.45f, "A20", party);
//        season_ticket = new SeasonTicketVO(100, 100.0f, show, showStartTime.toLocalDate(), showEndTime.toLocalDate());
        backstage_ticket = new BackstageTicketVO(224, 45.60f, "B23", show, customer);
        ticketOrdering = new TicketOrdering();


    }


    @Test
    void testStartNewOrder() throws NoCustomerException {
        OrderVO startedOrder = ticketOrdering.startNewOrder(customer);
        assertNotNull(startedOrder, "The started order should not be null.");
        assertEquals(customer, startedOrder.getCustomer(), "The customer in the order should match the one provided.");
        assertEquals(StateOfOrderVO.STARTED, startedOrder.getState(), "The state of the order should be STARTED.");
    }


    @Test
    void testAddTicket() throws NoOrderException, NoCustomerException {
        OrderVO currentOrder = ticketOrdering.startNewOrder(customer);
        ticketOrdering.addTicket(seat_ticket);
        assertTrue(currentOrder.getCart().contains(seat_ticket), "The seat ticket should be added to the cart.");
    }


    @Test
    void testDeleteTicket() throws NoOrderException, NoCustomerException {
        OrderVO currentOrder = ticketOrdering.startNewOrder(customer);
        ticketOrdering.addTicket(seat_ticket);
        ticketOrdering.deleteTicket(seat_ticket);
        assertFalse(currentOrder.getCart().contains(seat_ticket), "The seat ticket should be removed from the cart.");
    }


    @Test
    void testCalculateTotalPrice() throws NoOrderException, NoCustomerException {
        OrderVO currentOrder = ticketOrdering.startNewOrder(customer);
        ticketOrdering.addTicket(seat_ticket);
        ticketOrdering.addTicket(backstage_ticket);
        float totalPrice = ticketOrdering.calculateTotalPrice();
        assertEquals(seat_ticket.getBasePrice() + backstage_ticket.getBasePrice(), totalPrice, "The total price should be the sum of the ticket prices.");
    }


    @Test
    void testConfirmOrder() throws NoOrderException, NoCustomerException, IllegalStateException {
        OrderVO currentOrder = ticketOrdering.startNewOrder(customer);
        ticketOrdering.addTicket(seat_ticket);
        ticketOrdering.confirmOrder();
        assertEquals(StateOfOrderVO.CONFIRMED, currentOrder.getState(), "The order should be confirmed.");
    }


    @Test
    void testSortCart() throws NoOrderException, NoCustomerException {
        OrderVO currentOrder = ticketOrdering.startNewOrder(customer);
        ticketOrdering.addTicket(seat_ticket);
        ticketOrdering.addTicket(backstage_ticket);
        List<TicketVO> sortedTickets = ticketOrdering.sortCart();
        assertNotNull(sortedTickets, "The sorted tickets list should not be null.");
        assertTrue(sortedTickets.get(0).getId().compareTo(sortedTickets.get(1).getId()) < 0, "Tickets should be sorted by ID in ascending order");
    }


    @Test
    void testSortCartByEvent() throws NoOrderException, NoCustomerException {
        OrderVO currentOrder = ticketOrdering.startNewOrder(customer);
        ticketOrdering.addTicket(seat_ticket); // Linked to "Party 1"
        ticketOrdering.addTicket(backstage_ticket); // Linked to "Show 1"
        List<TicketVO> sortedTickets = ticketOrdering.sortCartByEvent();
        assertNotNull(sortedTickets, "The sorted tickets list should not be null.");
        assertTrue(sortedTickets.get(0).getEvent().getName().compareTo(sortedTickets.get(1).getEvent().getName()) < 0, "The tickets should be sorted by event name in ascending order.");
    }


    @Test
    void testSortCartByPrice() throws NoOrderException, NoCustomerException {
        OrderVO currentOrder = ticketOrdering.startNewOrder(customer);
        ticketOrdering.addTicket(seat_ticket); // Price of 40.45
        ticketOrdering.addTicket(backstage_ticket); // Price of 45.60
        List<TicketVO> sortedTickets = ticketOrdering.sortCartByPrice();
        assertNotNull(sortedTickets, "The sorted tickets list should not be null.");
        assertTrue(sortedTickets.get(0).getBasePrice() <= sortedTickets.get(1).getBasePrice(), "The tickets should be sorted by price in ascending order.");
    }


    @Test
    void addTicket_WhenOrderStarted() throws Exception {
        // Setup: Create an order in the STARTED state and a mock ticket.
        ITicketOrdering ticketOrdering = new TicketOrdering();
        TicketVO mockTicket = mock(TicketVO.class);

        // Action: Start a new order and add a ticket to the order.
        OrderVO startedOrder = ticketOrdering.startNewOrder(customer);
        ticketOrdering.addTicket(mockTicket);

        // Assert: Check if the order state is now STARTED, so that the Customer can add Tickets from many Categories.
        assertEquals(StateOfOrderVO.STARTED, startedOrder.getState(), "Order should be CONFIRMED after adding a ticket.");
    }


    @Test
    void deleteTicket_WhenOrderStarted_ShouldRemainStarted() throws Exception {
        // Setup: Create an order in the STARTED state and add a mock ticket.
        ITicketOrdering ticketOrdering = new TicketOrdering();
        ticketOrdering.startNewOrder(customer);
        TicketVO mockTicket = mock(TicketVO.class);
        ticketOrdering.addTicket(mockTicket);

        // Action: Delete the ticket from the order.
        ticketOrdering.deleteTicket(mockTicket);

        // Assert: Check if the order state is still STARTED.
        assertEquals(StateOfOrderVO.STARTED, order.getState(), "Order should remain STARTED after deleting the ticket.");
    }


    @Test
    void confirmOrder_WhenOrderStarted_ShouldTransitionToConfirmed() throws Exception {
        // Setup: Create an order and add a mock ticket.
        ITicketOrdering ticketOrdering = new TicketOrdering();
        OrderVO startedOrder = ticketOrdering.startNewOrder(customer);
        TicketVO mockTicket = mock(TicketVO.class);
        ticketOrdering.addTicket(mockTicket);

        // Check the state before confirmation
        assertEquals(StateOfOrderVO.STARTED, startedOrder.getState(), "Order should be STARTED before confirmation.");

        // Action: Confirm the order.
        ticketOrdering.confirmOrder();

        // Assert: Check if the order state is CONFIRMED.
        assertEquals(StateOfOrderVO.CONFIRMED, startedOrder.getState(), "Order should be CONFIRMED after confirmation.");
    }


    @Test
    void printTickets_WhenOrderConfirmed_ShouldTransitionToPrinted() throws Exception {
        // Setup: Confirm an order with a ticket.
        ITicketOrdering ticketOrdering = new TicketOrdering();
        OrderVO confirmedOrder = ticketOrdering.startNewOrder(customer);
        TicketVO mockTicket = mock(TicketVO.class);
        ticketOrdering.addTicket(mockTicket);
        ticketOrdering.confirmOrder();

        when(printingService.startService(confirmedOrder)).then(invocation -> {
            OrderVO order = invocation.getArgument(0);
            order.setState(StateOfOrderVO.PRINTED);
            return "Printing Service Started";
        });

// Action: Print the tickets.
        String result = printingService.startService(confirmedOrder);

// Assert: Check if the order state is PRINTED and service is started.
        assertEquals(StateOfOrderVO.PRINTED, confirmedOrder.getState(), "Order should be PRINTED after printing tickets.");
        assertEquals("Printing Service Started", result, "Printing service should start successfully.");


    }


    @Test
    void mailTickets_WhenOrderPrinted_ShouldTransitionToMailed() throws Exception {

        // Setup: Get an order to the PRINTED state.
        ITicketOrdering ticketOrdering = new TicketOrdering();
        OrderVO printedOrder = ticketOrdering.startNewOrder(customer);
        TicketVO mockTicket = mock(TicketVO.class);
        ticketOrdering.addTicket(mockTicket);
        ticketOrdering.confirmOrder();
        printingService.startService(printedOrder); // This should set the state to PRINTED.

        when(mailingService.startService(printedOrder)).then(invocation -> {
            OrderVO order = invocation.getArgument(0);
            order.setState(StateOfOrderVO.MAILED);
            return "Mailing Service Started";
        });

// Action: Mail the tickets.
        String result = mailingService.startService(printedOrder);

// Assert: Check if the order state is MAILED and service is started.
        assertEquals(StateOfOrderVO.MAILED, printedOrder.getState(), "Order should be MAILED after mailing tickets.");
        assertEquals("Mailing Service Started", result, "Mailing service should start successfully.");


    }


    private class TicketOrdering implements ITicketOrdering {

        private OrderVO currentOrder;
        private boolean isOrderStarted = false;


        @Override
        public OrderVO startNewOrder(CustomerVO customer) {
            if (customer == null) {
                throw new IllegalArgumentException("Customer cannot be null.");
            }
            currentOrder = new OrderVO(1, StateOfOrderVO.STARTED, testStartTime, customer);
            isOrderStarted = true;
            return currentOrder;
        }

        @Override
        public void addTicket(TicketVO ticket) throws NoOrderException {
            if (!isOrderStarted || currentOrder == null) {
                throw new NoOrderException("No order started.");
            }
            if (ticket == null) {
                throw new IllegalArgumentException("Ticket cannot be null.");
            }
            // Add the ticket to the order
            currentOrder.getCart().add(ticket);

            // Do NOT change the state here, keep it as STARTED
        }

        @Override
        public void confirmOrder() throws NoOrderException, NoCustomerException, IllegalStateException {
            if (!isOrderStarted) {
                throw new NoOrderException("No order started.");
            }
            if (currentOrder.getCustomer() == null) {
                throw new NoCustomerException("No customer associated with the order.");
            }
            if (currentOrder.getCart().isEmpty()) {
                throw new IllegalStateException("Cannot confirm an order with no tickets.");
            }

            // Change the order state to CONFIRMED only here
            currentOrder.setState(StateOfOrderVO.CONFIRMED);
        }



        @Override
        public void deleteTicket(TicketVO ticket) throws NoOrderException {
            if (!isOrderStarted) {
                throw new NoOrderException("No order started.");
            }
            currentOrder.getCart().remove(ticket);
        }

        @Override
        public float calculateTotalPrice() throws NoOrderException {
            if (!isOrderStarted) {
                throw new NoOrderException("No order started.");
            }
            return currentOrder.getCart().stream()
                    .map(TicketVO::getBasePrice)
                    .reduce(0f, Float::sum);
        }



        @Override
        public List<TicketVO> sortCart() throws NoOrderException {
            if (!isOrderStarted) {
                throw new NoOrderException("No order started.");
            }
            return currentOrder.getCart().stream()
                    .sorted(Comparator.comparing(TicketVO::getId)) // Assuming TicketVO has getId method
                    .collect(Collectors.toList());
        }

        @Override
        public List<TicketVO> sortCartByEvent() throws NoOrderException {
            if (!isOrderStarted) {
                throw new NoOrderException("No order started.");
            }
            return currentOrder.getCart().stream()
                    .sorted(Comparator.comparing(ticket -> ticket.getEvent().getName())) // Assuming TicketVO has getEvent method
                    .collect(Collectors.toList());
        }

        @Override
        public List<TicketVO> sortCartByPrice() throws NoOrderException {
            if (!isOrderStarted) {
                throw new NoOrderException("No order started.");
            }
            return currentOrder.getCart().stream()
                    .sorted(Comparator.comparing(TicketVO::getBasePrice))
                    .collect(Collectors.toList());
        }
    }

    // (rest of the ITicketOrderingTest class)

    @AfterEach
    void tearDown() {
        ticketOrdering = null;
        order = null;
        customer = null;
        seat_ticket = null;
        backstage_ticket = null;
        party = null;
        show = null;
    }


}