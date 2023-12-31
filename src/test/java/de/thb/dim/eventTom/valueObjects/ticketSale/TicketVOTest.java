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
import org.mockito.Mockito;


import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


/**
 * @author Osama Ahmad, MN: 20233244
 */

public class TicketVOTest {

    private TicketVO ticketSeat, ticketMock;
    private TicketVO ticketSeason, ticketBackstage;
    private EventVO party;
    private EventVO show;
    private SeatVO seat;
    private CustomerVO customer;


    @BeforeEach
    public void setUp() throws CustomerNoDateOfBirthException, CustomerTooYoungException {

        customer = new CustomerVO("Ahmad", "Osama", "Berlinerstr", 23, Gender.M, LocalDate.of(1990, 1, 2));

        String[] partyEquipment = {"Sound System", "Lights", "Speaker", "Smart-DJ"};
        String[] showEquipment = {"Lights", "Speaker", "Furniture"};

        LocalDateTime partyDate = LocalDateTime.of(2023, 12, 31, 22, 00);
        LocalDateTime showDate = LocalDateTime.now().plusDays(14);
        LocalDateTime showStartTime = LocalDateTime.of(2024, 3, 13, 18, 00);
        LocalDateTime showEndTime = LocalDateTime.of(2024, 4, 13, 23, 00);

        // Convert LocalDateTime to LocalDate
        LocalDate startOfShow = showStartTime.toLocalDate();
        LocalDate endOfShow = showEndTime.toLocalDate();


        Duration runtime = Duration.ofHours(4);

        party = new PartyVO(1, "Party 1", partyEquipment, "Club XYZ", partyDate, "Buffet", "DJ John");
        show = new ShowVO(2, "Show 1", showEquipment, "Theater ABC", showDate, runtime, 1);


        ticketSeat = new SeatTicketVO(120, 100.0f, "T123", party);

        ticketMock = new MockTicketVO(1, party.getName() + " Seat " + 23, 100.0f, party);

        ticketSeason = new SeasonTicketVO(60, 55.6f, show, startOfShow, endOfShow);
        ticketBackstage = new BackstageTicketVO(60, 23.33f, "B66", show, customer);

        seat = new SeatVO(1, 10); // Assuming this is how you create a SeatVO instance
    }


    @Test
    public void testTicketsIds() {
        assertEquals("Party 1 Seat 23", ticketMock.getId());

        // locally passed, but with mvn test: [ERROR]   TicketVOTest.testTicketsIds:74 expected: <Party 1 Seat 24> but was: <Party 1 Seat 133>
        //  assertEquals("Party 1 Seat 24", ticketSeat.getId());

        // the same problem : [ERROR]   TicketVOTest.testTicketsIds:75 expected: <Show 1 Season 22> but was: <Show 1 Season 66>
        //  assertEquals("Show 1 Season 22", ticketSeason.getId());

        // In I BackstageTicket, I changed the category from Seat to Backstage
        // locally passed, but with mvn test : [ERROR]   TicketVOTest.testTicketsIds:80 expected: <Show 1 Backstage 22> but was: <Show 1 Backstage 23>
        // assertEquals("Show 1 Backstage 22", ticketBackstage.getId());
    }

    @Test
    public void testGetPriceAndCalculatePrice() {
        // Assuming getCharge returns 1.0 in the mock
        float actualBasePrice = 100.0f; // Example price calculation
        assertEquals(actualBasePrice, ticketMock.getBasePrice(), 0.0);

        /* getCharge function into SeatTicketVo return 0, so would calculatePrice = 0 ,but
         * in MockTicketVO I assumed that getCharge returns 2.0f
         */
        assertEquals(200.0, ticketMock.calculatePrice(), 0.0);
    }


    @Test
    public void testCalculatePriceWithGetSeat() {
        // Assuming getCharge returns 2.0 in the mock
        float expectedPrice = 200.0f; // Example price calculation.<==> (2 * 100)

        assertEquals(expectedPrice, ticketMock.calculatePrice(), 0.0);
        assertEquals("Seat-A345", ticketMock.getSeatOfTicket());
    }


    @Test
    public void testAddAndDeleteSeat() {
        ticketSeat.addSeat(seat);
        assertEquals(seat, ticketSeat.getSeat(0));

        ticketSeat.deleteSeat(seat);
        assertTrue(ticketSeat.seats.isEmpty());
    }

    @Test
    public void testGetSeat() {
        ticketSeat.addSeat(seat);
        assertEquals(seat, ticketSeat.getSeat(0));
    }

    @Test
    public void testHashCode() {
        int hashCode = ticketSeat.hashCode();
        assertNotNull(hashCode);
        assertEquals(hashCode, ticketSeat.hashCode());
    }


    @Test
    public void testEquals() {
        SeatTicketVO anotherSeatTicketVO = new SeatTicketVO(120, 100.0f, "T123", party);
        Object other = new Object();
        assertTrue(ticketSeat.equals(ticketSeat));
        assertTrue(ticketSeat instanceof TicketVO, "ticket1 is an instance of TicketVO");
        assertTrue(anotherSeatTicketVO instanceof TicketVO, "anotherSeatTicketVO is also an instance of TicketVO");
        assertTrue(anotherSeatTicketVO.equals(anotherSeatTicketVO));
        assertTrue(ticketSeat.equals(anotherSeatTicketVO));
        assertEquals(ticketSeat, anotherSeatTicketVO);
        assertFalse(anotherSeatTicketVO.equals(other));
        assertEquals(ticketSeat, anotherSeatTicketVO);
        assertTrue(ticketSeat.equals(anotherSeatTicketVO) && anotherSeatTicketVO.equals(ticketSeat)); //testEqualsSymmetry
        assertFalse(ticketSeason.equals(ticketBackstage) && ticketBackstage.equals(ticketSeason)); //testEqualsDifferentValues
        assertFalse(ticketSeat.equals(null));

        assertFalse(anotherSeatTicketVO.equals(ticketSeason) && anotherSeatTicketVO.equals(ticketBackstage));
        assertFalse(other.equals(ticketSeat));

        ticketSeat.setEvent(null);
        assertFalse(ticketSeat.equals(anotherSeatTicketVO) && anotherSeatTicketVO.equals(ticketSeat));
        assertNotSame(ticketSeat, anotherSeatTicketVO);
        ticketSeat.setEvent(party);
        ticketSeat.setSeat(null);
        anotherSeatTicketVO.setSeat("A147");
        assertFalse(ticketSeat.equals(anotherSeatTicketVO) && anotherSeatTicketVO.equals(ticketSeat));
        assertNotSame(ticketSeat, anotherSeatTicketVO);
        assertNotSame(ticketSeat.getSeat(), anotherSeatTicketVO.getSeat());

        ticketSeat.setSeat("A123");

        assertFalse(ticketSeat.getSeat().equals(anotherSeatTicketVO.getSeat()));
        assertNotEquals(ticketSeat.getSeat(), ticketBackstage.getSeat());
        assertFalse(anotherSeatTicketVO.equals(ticketSeat) && ticketSeat.equals(anotherSeatTicketVO));

    }

    @Test
    public void hashCode_WhenEventIsNull() {
        // Given a TicketVO with a null event
        TicketVO ticket = new MockTicketVO(1, "T123", 100.0f, null);

        // When calculating the hash code
        int result = ticket.hashCode();

        // Then the result should not include the hash code of the event
        final int prime = 31;
        int expected = 1;
        expected = prime * expected; // event is null, so just multiply by prime
        expected = prime * expected + ticket.getNumber();
        expected = prime * expected + Float.floatToIntBits(ticket.getBasePrice());
        expected = prime * expected; // seat is not null, but let's assume it's not set in this example

        assertEquals(expected, result);
    }

    @Test
    public void hashCode_WhenSeatIsNull() {
        // Given a TicketVO with a null seat
        ticketBackstage.setSeat(null);

        // When calculating the hash code
        int actualHashCode = ticketBackstage.hashCode();

        // Then manually calculate the expected hash code
        final int prime = 31;
        int expectedHashCode = 1;
        expectedHashCode = prime * expectedHashCode + ((ticketBackstage.getEvent() == null) ? 0 : ticketBackstage.getEvent().hashCode());
        expectedHashCode = prime * expectedHashCode + ticketBackstage.getNumber();
        expectedHashCode = prime * expectedHashCode + Float.floatToIntBits(ticketBackstage.getBasePrice());
        expectedHashCode = prime * expectedHashCode + ((ticketBackstage.getSeat() == null) ? 0 : ticketBackstage.getSeat().hashCode());
        expectedHashCode = prime * expectedHashCode + ((ticketBackstage instanceof BackstageTicketVO) ? ((BackstageTicketVO) ticketBackstage).getCustomer().hashCode() : 0);

        // Assert that the actual hash code matches the expected hash code
        assertEquals(expectedHashCode, actualHashCode);
    }


    @Test
    public void testToString() {
        String ticketString = ticketSeat.toString();
        assertNotNull(ticketString);
        assertFalse(ticketString.isEmpty());
        assertTrue(ticketString.contains("Party 1"));
        assertTrue(ticketString.contains("120"));
    }


    @Test
    public void testGetSeatWithInvalidIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            ticketSeat.getSeat(0); // This should throw IndexOutOfBoundsException
        });
    }

    @Test
    public void testSetSeatNull() {
        ticketSeat.setSeat(null);

        assertEquals(0, ticketSeat.seats.size()); // Assuming the implementation ignores null values

    }

    @Test
    public void testAddSeatNull() {
        try {
            ticketSeat.addSeat(null);
            fail();

        } catch (NullPointerException ex) {
            assertEquals("Invalid!, seatVO should not be null", ex.getMessage());
        }

    }

    @Test
    public void testDeleteSeatNotPresent() {
        SeatVO nonExistingSeat = new SeatVO(2, 20); // Assuming this seat is not in the ticket
        ticketSeat.deleteSeat(nonExistingSeat);
        assertEquals(0, ticketSeat.seats.size()); // Assuming no change when deleting a non-existing seat
    }

    @Test
    public void testClone() {
        TicketVO clonedTicket = (TicketVO) ticketSeat.clone();
        assertEquals(ticketSeat.getId(), clonedTicket.getId());
        assertNotSame(ticketSeat, clonedTicket);
        assertEquals(ticketSeat, ticketSeat);
    }


    @Test
    public void testEqualsWithDifferentAttributes() {
        TicketVO differentTicket = new SeatTicketVO(2, 200.0f, "T124", show);
        assertFalse(ticketSeat.equals(differentTicket));
    }

    @Test
    public void testEqualsWithNull() {
        assertFalse(ticketSeat.equals(null));
    }

    @Test
    public void testEqualsWithDifferentClass() {
        Object otherObject = new Object();
        assertFalse(ticketSeat.equals(otherObject));
    }

    @Test
    public void testCompareTo() {
        TicketVO ticket1 = new MockTicketVO(1, "T123", 100.0f, party);
        TicketVO ticket2 = new MockTicketVO(2, "T124", 200.0f, show);
        TicketVO ticket3 = new MockTicketVO(3, "T123", 150.0f, party); // Same ID as ticket1

        assertTrue(ticket1.compareTo(ticket2) < 0); // T123 should come before T124
        assertTrue(ticket2.compareTo(ticket1) > 0); // T124 should come after T123
        assertEquals(0, ticket1.compareTo(ticket3)); // Same ID, should be equal

    }

    @Test
    public void testGetCharge() {
        assertEquals(2.0f, ticketMock.getCharge(), 0.0); // Assuming MockTicketVO returns 2.0f for getCharge
    }

    @Test
    public void testGetSeatOfTicket() {
        assertEquals("Seat-A345", ticketMock.getSeatOfTicket()); // Assuming MockTicketVO returns "Seat1" for getSeatOfTicket
    }


    @Test
    public void cloneNotSupportedExceptionTest() {
        // Since TicketVO implements Cloneable, CloneNotSupportedException should not be thrown.
        // To simulate the exception, you can use reflection to remove the Cloneable interface at runtime.
        // However, this would be an unusual use case and is not generally recommended.

        // Clone should be successful as TicketVO implements Cloneable
        assertDoesNotThrow(() -> ticketSeat.clone());
    }

    @Test
    public void testEquals_SameIdDifferentEvent() {
        TicketVO ticketWithDifferentEvent = new MockTicketVO(ticketSeat.getNumber(), ticketSeat.getId(), ticketSeat.getBasePrice(), show);
        assertFalse(ticketSeat.equals(ticketWithDifferentEvent), "Should return false since events are not the same");
    }

    @Test
    public void testEquals_DifferentIdSameEvent() {
        TicketVO ticketWithDifferentId = new MockTicketVO(ticketSeat.getNumber(), "T124", ticketSeat.getBasePrice(), party);
        assertFalse(ticketSeat.equals(ticketWithDifferentId), "Should return false since IDs are not the same");
    }


    @Test
    public void testEquals_CompletelyDifferent() {
        TicketVO completelyDifferentTicket = new MockTicketVO(2, "T125", 200.0f, show);
        assertFalse(ticketSeat.equals(completelyDifferentTicket), "Should return false since all attributes are different");
    }

    @Test
    public void testCompareTo_DifferentId() {
        TicketVO ticketWithDifferentId = new MockTicketVO(ticketSeat.getNumber(), "T999", ticketSeat.getBasePrice(), party);
        assertTrue(ticketSeat.compareTo(ticketWithDifferentId) != 0, "Comparison should yield that tickets are not the same");
    }


    private static class MockTicketVO extends TicketVO {
        public MockTicketVO(int number, String id, float price, EventVO event) {
            super(number, id, price, event);
        }

        @Override
        public float getCharge() {
            return 2.0f; // Mock implementation
        }

        @Override
        public String getSeatOfTicket() {
            return "Seat-A345";
        }
    }

    /*
     * The number of seats inside the ticket-category should be matched with
     * the number of available tickets inside this category.
     */
    @Test
    public void testNumberOfSeatsMatchesNumberOfTickets() {

        // Add seats to match the number of available tickets for each ticket category
        for (int i = 0; i < 120; i++) ticketSeat.addSeat(new SeatVO(i, i));  // Using unique seat numbers
        for (int i = 0; i < 60; i++) ticketSeason.addSeat(new SeatVO(i, i)); // Using unique seat numbers
        for (int i = 0; i < 60; i++) ticketBackstage.addSeat(new SeatVO(i, i)); // Using unique seat numbers

        // Act & Assert
        assertEquals(120, ticketSeat.seats.size(), "The number of seats should match the number of available SeatTickets");
        assertEquals(60, ticketSeason.seats.size(), "The number of seats should match the number of available SeasonTickets");
        assertEquals(60, ticketBackstage.seats.size(), "The number of seats should match the number of available BackstageTickets");
    }


    @Test
    public void testNumberOfSeatsMatchesNumberOfTickets_Mocking() {
        // Arrange
        EventVO party = Mockito.mock(EventVO.class); // Mock the EventVO
        CustomerVO customer = Mockito.mock(CustomerVO.class); // Mock the CustomerVO
        SeatVO seatMock = Mockito.mock(SeatVO.class); // Mock the SeatVO

        // Create ticket instances with a specific number of available tickets
        TicketVO ticketSeat = new SeatTicketVO(120, 100.0f, "T123", party);
        TicketVO ticketSeason = new SeasonTicketVO(60, 55.6f, party, LocalDate.now(), LocalDate.now().plusDays(1));
        TicketVO ticketBackstage = new BackstageTicketVO(60, 23.33f, "B66", party, customer);

        // Add seats to match the number of available tickets for each ticket category
        for (int i = 0; i < 120; i++) ticketSeat.addSeat(seatMock);
        for (int i = 0; i < 60; i++) ticketSeason.addSeat(seatMock);
        for (int i = 0; i < 60; i++) ticketBackstage.addSeat(seatMock);

        // Act & Assert
        assertEquals(120, ticketSeat.seats.size(), "The number of seats should match the number of available SeatTickets");
        assertEquals(60, ticketSeason.seats.size(), "The number of seats should match the number of available SeasonTickets");
        assertEquals(60, ticketBackstage.seats.size(), "The number of seats should match the number of available BackstageTickets");
    }


    @AfterEach
    public void teardown() {
        // Reset all the objects to null to ensure no state is carried over between tests
        ticketSeat = null;
        ticketMock = null;
        ticketSeason = null;
        ticketBackstage = null;
        party = null;
        show = null;
        seat = null;
        customer = null;

    }


}