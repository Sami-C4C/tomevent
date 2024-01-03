package de.thb.dim.eventTom.valueObjects.ticketSale;


import de.thb.dim.eventTom.valueObjects.customerManagement.exceptions.CustomerNoDateOfBirthException;
import de.thb.dim.eventTom.valueObjects.customerManagement.exceptions.CustomerTooYoungException;
import de.thb.dim.eventTom.valueObjects.eventManagement.EventVO;
import de.thb.dim.eventTom.valueObjects.eventManagement.PartyVO;
import de.thb.dim.eventTom.valueObjects.eventManagement.ShowVO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotSame;

/**
 * @author Osama Ahmad , MN:20233244
 */
class SeatTicketVOTest {

    private SeatTicketVO ticket1, ticket2, ticket3;
    private SeasonTicketVO ticket4;
    private PartyVO event1;
    private PartyVO event2;
    private ShowVO event3;
    private PartyVO event4;
//    private CustomerVO customer3;



    @BeforeEach
    public void setUp(){

        String[] partyEquipment = {"Sound System", "Lights", "Speaker", "Smart-DJ"};
        String[] showEquipment = {"Lights", "Speaker", "Furniture"};

        LocalDateTime partyDate1 = LocalDateTime.of(2023, 12, 31, 22, 00);
        LocalDateTime partyDate2 = LocalDateTime.of(2024, 1, 1, 22, 00);
        LocalDateTime partyDate4 = LocalDateTime.of(2024, 1, 2, 21, 00);

        LocalDateTime showDate = LocalDateTime.of(2024, 5, 1, 13, 00);
        LocalDateTime showStartTime = LocalDateTime.of(2024, 3, 13, 18, 00);
        LocalDateTime showEndTime = LocalDateTime.of(2024, 3, 18, 23, 00);


        Duration runtime = Duration.ofHours(4);

        event1 = new PartyVO(1, "Event Name 1", partyEquipment, "Stars Club - Berlin", partyDate1, "Catering 1", "Performer 1");
        event2 = new PartyVO(2, "Event Name 2", partyEquipment, "Disco Havana - Potsdam", partyDate2, "Catering 2", "Performer 2");
        event3 = new ShowVO(1, "Show1", showEquipment, "Berlin", showDate, runtime, 4);
        event4 = new PartyVO(4, "Event Name 4", partyEquipment, "Moabit Club - Berlin", partyDate4, "Catering", "Performer 4");

        ticket1 = new SeatTicketVO(100, 100.0f, "A1", event4);
        ticket2 = new SeatTicketVO(100, 100.0f, "A1", event4);
        ticket3 = new SeatTicketVO(100, 200.0f, "A2", event4);
        ticket4 = new SeasonTicketVO(100, 100.0f, event3, showStartTime.toLocalDate(), showEndTime.toLocalDate());

//        customer3 = new CustomerVO("Gieske", "Antonia", "Gertraudenstr", 77, Gender.F, LocalDate.of(1997, 4, 13));

    }


    @Test
    public void testEqualsOfSeatTicketVO() {

        // testEqualsSameObject
        assertTrue(ticket1.equals(ticket1), "A ticket should be equal to itself");

        // testEqualsDifferentSubClass
        assertFalse(ticket2.equals(ticket4), "A ticket of SeatTicketVO should not be equal to another ticket of SeasonTicketVO");
        assertFalse(ticket1.equals(ticket4));


        Object otherObject = new Object();
        assertFalse(ticket1.equals(otherObject), "A ticket should not be equal to an object of a different class");
        assertNotSame(ticket1, otherObject);


        // testEqualsNull
        assertFalse(ticket1.equals(null), "A ticket should not be equal to null");

        // testEqualsDifferentTickets
        assertFalse(ticket1.equals(ticket3), "Different tickets should not be considered equal");

        // testEqualsSimilarTickets
        assertTrue(ticket1.equals(ticket2), "Tickets with the same attributes should be considered equal");


        // Use the same class for comparison
        TicketVO anotherTicket = new SeatTicketVO(100, 100.0f, "A1", event4);
        assertTrue(ticket1.equals(anotherTicket));


    }




    @Test
    public void testEqualsWithSuperNotEqual() {
        // Create a ticket with the same ID but different superclass field values
        // This assumes TicketVO's superclass implements equals that compares certain fields
        TicketVO ticketWithDifferentSuperValues = new SeatTicketVO(100, 100.0f, "A1", event4) {
            // Override a method to make super.equals return false
            @Override
            public String getId() {
                return "Different ID";
            }
        };
        assertFalse(ticket1.equals(ticketWithDifferentSuperValues));
    }




   /* @Test
    void equals_DifferentClass_ReturnsFalse() {

        SeatTicketVO seatTicket = new SeatTicketVO(80, 67.40f, "A1", event4);

        // Create an object of a different class for comparison
        TicketVO differentClassTicket = new BackstageTicketVO(180, 48.55f, "B1", event4, customer3);


        assertFalse(seatTicket.equals(differentClassTicket), "Should return false when comparing with an object of a different class.");

        Object differentClassObject = new Object();

        // Act & Assert: Assert that equals returns false when classes are not the same.
        assertFalse(ticket1.equals(differentClassObject));
        assertFalse(differentClassObject instanceof TicketVO);
    }*/


    @Test
    public void testEqualsWithDifferentIDs() {
        SeatTicketVO ticketWithDifferentNumber = new SeatTicketVO(70, 100.0f, "A1", event1);
        assertFalse(ticket1.getId().equals(ticketWithDifferentNumber.getId()), "Tickets with different IDs should not be considered equal");

    }


    @Test
    public void testEqualsWithDifferentEvent() {
        SeatTicketVO ticketWithDifferentEvent = new SeatTicketVO(100, 100.0f, "A1", event2);
        assertFalse(ticket1.equals(ticketWithDifferentEvent), "Tickets with different events should not be considered equal");
        assertFalse(event1.equals(event3), "Should return false since event1 is not an instance of ShowVO");


    }

    @Test
    public void testEqualsWithDifferentSeat() {
        SeatTicketVO ticketWithDifferentSeat = new SeatTicketVO(100, 100.0f, "B1", event1);
        assertFalse(ticket1.equals(ticketWithDifferentSeat), "Tickets with different seats should not be considered equal");
    }

    @Test
    public void testEqualsWithDifferentPrice() {
        SeatTicketVO ticketWithDifferentPrice = new SeatTicketVO(70, 150.0f, "A1", event1);
        assertFalse(ticket1.equals(ticketWithDifferentPrice), "Tickets with different prices should not be considered equal");
    }

    @Test
    public void testEqualsWithDifferentNumber() {
        SeatTicketVO ticketWithDifferentNumber = new SeatTicketVO(70, 100.0f, "A1", event1);
        assertFalse(ticket1.equals(ticketWithDifferentNumber), "Tickets with different numbers should not be considered equal");

    }





    @Test
    public void testInstanceofSeatTicketVO() {
        assertTrue(ticket1 instanceof SeatTicketVO, "ticket1 should be an instance of SeatTicketVO");
    }

    @Test
    public void testInstanceofTicketVO() {
        assertTrue(ticket1 instanceof TicketVO, "ticket1 should be an instance of TicketVO");
        assertTrue(ticket4 instanceof TicketVO, "ticket4 is an instance of TicketVO");


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



    @AfterEach
    public void teardown() {
        // Reset all the objects to null to ensure no state is carried over between tests
        ticket1 = null;
        ticket2 = null;
        ticket3 = null;
        ticket4 = null;
        //customer3 = null;
        event1 = null;
        event2 = null;
        event3 = null;
        event4 = null;


    }

}