package de.thb.dim.eventTom.valueObjects.ticketSale;

import de.thb.dim.eventTom.valueObjects.customerManagement.CustomerVO;
import de.thb.dim.eventTom.valueObjects.customerManagement.Gender;
import de.thb.dim.eventTom.valueObjects.customerManagement.exceptions.CustomerNoDateOfBirthException;
import de.thb.dim.eventTom.valueObjects.customerManagement.exceptions.CustomerTooYoungException;
import de.thb.dim.eventTom.valueObjects.eventManagement.EventVO;
import de.thb.dim.eventTom.valueObjects.eventManagement.PartyVO;
import de.thb.dim.eventTom.valueObjects.eventManagement.ShowVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class SeatTicketVOTest {

    private SeatTicketVO ticket1;
    private SeatTicketVO ticket2;
    private SeatTicketVO ticket3;
    private SeasonTicketVO ticket4;
    private PartyVO event;
    private PartyVO event1;
    private PartyVO event2;
    private ShowVO event3;
    private CustomerVO customer3;


    @BeforeEach
    public void setUp() throws CustomerNoDateOfBirthException, CustomerTooYoungException {

        LocalDateTime eventDate = LocalDateTime.now();
        LocalDateTime eventDate1 = LocalDateTime.now();
        LocalDateTime eventDate2 = LocalDateTime.now().plusDays(1);
        Duration runtime = Duration.ofHours(4);
        event = new PartyVO(1, "Event Name", new String[]{"Equipment"}, "Venue", eventDate, "Catering", "Performer");
        event1 = new PartyVO(1, "Event Name 1", new String[]{"Equipment 1"}, "Venue 1", eventDate1, "Catering 1", "Performer 1");
        event2 = new PartyVO(2, "Event Name 2", new String[]{"Equipment 2"}, "Venue 2", eventDate2, "Catering 2", "Performer 2");
        event3 = new ShowVO(1, "Show1", new String[]{"Drums", "Clarinet"}, "Berlin", eventDate2, runtime, 4);
        ticket1 = new SeatTicketVO(1, 100.0f, "A1", event);
        ticket2 = new SeatTicketVO(1, 100.0f, "A1", event);
        ticket3 = new SeatTicketVO(2, 200.0f, "A2", event);
        ticket4 = new SeasonTicketVO(1, 100.0f, event, eventDate.toLocalDate(), eventDate1.toLocalDate());

        customer3 = new CustomerVO("Gieske", "Antonia","Gertraudenstr",77, Gender.F, LocalDate.of(1997, 4,13));

    }


    @Test
    public void testEqualsSameObject() {
        assertTrue(ticket1.equals(ticket1), "A ticket should be equal to itself");
    }

    @Test
    public void testEqualsDifferentClass() {
        Object otherObject = new Object();
        assertFalse(ticket1.equals(otherObject), "A ticket should not be equal to an object of a different class");
    }

    @Test
    public void testEqualsNull() {
        assertFalse(ticket1.equals(null), "A ticket should not be equal to null");
    }

    @Test
    public void testEqualsDifferentTickets() {
        assertFalse(ticket1.equals(ticket3), "Different tickets should not be considered equal");
    }

    @Test
    public void testEqualsSimilarTickets() {
        assertTrue(ticket1.equals(ticket2), "Tickets with the same attributes should be considered equal");
    }


    @Test
    public void testEqualsWithSuperNotEqual() {
        // Create a ticket with the same ID but different superclass field values
        // This assumes TicketVO's superclass implements equals that compares certain fields
        TicketVO ticketWithDifferentSuperValues = new SeatTicketVO(1, 100.0f, "A1", event) {
            // Override a method to make super.equals return false
            @Override
            public String getId() {
                return "Different ID";
            }
        };
        assertFalse(ticket1.equals(ticketWithDifferentSuperValues));
    }


    @Test
    public void testEqualsWithSameClass() {
        // Use the same class for comparison
        TicketVO anotherTicket = new SeatTicketVO(1, 100.0f, "A1", event);
        assertTrue(ticket1.equals(anotherTicket));
    }


    @Test
    void equals_DifferentClass_ReturnsFalse() {

        SeatTicketVO seatTicket = new SeatTicketVO(11, 67.40f, "A1", event);

        // Create an object of a different class for comparison
        TicketVO differentClassTicket = new BackstageTicketVO(18, 48.55f, "B1", event, customer3);


        assertFalse(seatTicket.equals(differentClassTicket), "Should return false when comparing with an object of a different class.");

        Object differentClassObject = new Object();

        // Act & Assert: Assert that equals returns false when classes are not the same.
        assertFalse(ticket1.equals(differentClassObject));
        assertFalse(differentClassObject instanceof TicketVO);
    }



    @Test
    public void testEqualsWithDifferentClass() {
        Object object = new Object();
        // Use the same class for comparison
        TicketVO anotherTicket = new SeatTicketVO(1, 100.0f, "A1", event);
        assertFalse(anotherTicket.equals(object));
        assertNotSame(object, anotherTicket);
    }

    @Test
    public void testEqualsWithDifferentEvent() {
        SeatTicketVO ticketWithDifferentEvent = new SeatTicketVO(1, 100.0f, "A1", event2);
        assertFalse(ticket1.equals(ticketWithDifferentEvent), "Tickets with different events should not be considered equal");
    }

    @Test
    public void testEqualsWithDifferentSeat() {
        SeatTicketVO ticketWithDifferentSeat = new SeatTicketVO(1, 100.0f, "B1", event1);
        assertFalse(ticket1.equals(ticketWithDifferentSeat), "Tickets with different seats should not be considered equal");
    }

    @Test
    public void testEqualsWithDifferentPrice() {
        SeatTicketVO ticketWithDifferentPrice = new SeatTicketVO(1, 150.0f, "A1", event1);
        assertFalse(ticket1.equals(ticketWithDifferentPrice), "Tickets with different prices should not be considered equal");
    }

    @Test
    public void testEqualsWithDifferentNumber() {
        SeatTicketVO ticketWithDifferentNumber = new SeatTicketVO(2, 100.0f, "A1", event1);
        assertFalse(ticket1.equals(ticketWithDifferentNumber), "Tickets with different numbers should not be considered equal");

    }



    @Test
    public void testEquals_DifferentSubclassType() {

        assertFalse(ticket4.equals(ticket2), "Ticket4 must not be equal to a ticket2 of a different subclass type");
    }

    @Test
    public void testInstanceofSeatTicketVO() {
        assertTrue(ticket1 instanceof SeatTicketVO, "ticket1 should be an instance of SeatTicketVO");
    }

    @Test
    public void testInstanceofTicketVO() {
        assertTrue(ticket1 instanceof TicketVO, "ticket1 should be an instance of TicketVO");
    }

    @Test
    public void testInstanceofSeasonTicketVO() {
        assertTrue(ticket4 instanceof SeasonTicketVO, "ticket4 is an instance of SeasonTicketVO");
    }

    @Test
    public void testInstanceofEvents() {
        assertTrue(event1 instanceof PartyVO, "event1 is an instance of PartyVO");
        assertTrue(event1 instanceof EventVO, "event1 is also an instance of EventVO");
        assertNotSame(event3, event2);
    }

    @Test
    public void testEqualsWithDifferentTickets() {
        assertFalse(ticket1.equals(ticket4));
    }

    @Test
    public void testEqualsWithDifferentEvents() {

        assertFalse(event1.equals(event3), "Should return false since obj is not an instance of SeatTicketVO");
    }

    @Test
    public void testEqualsWithDifferentClasses() {

        assertFalse(event1.equals(event3), "Should return false since event1 is not an instance of ShowVO");
    }


}