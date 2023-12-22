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


/**
 * @author Osama Ahmad
 * MN: 20233244
 */

public class TicketVOTest {

    private TicketVO ticket1, ticketMock;
    private TicketVO ticket2, ticket3;
    private EventVO party;
    private EventVO show;
    private SeatVO seat;
    private CustomerVO customer;


    @BeforeEach
    public void setUp() throws CustomerNoDateOfBirthException, CustomerTooYoungException {
        // Creating a mocked EventVO instance (e.g., PartyVO)

        // Use MockTicketVO instead of SeatTicketVO
        customer = new CustomerVO("Ahmad", "Osama", "Berlinerstr", 23, Gender.M, LocalDate.of(1990, 1, 2));

        String[] equipment = {"Sound System", "Lights"};
        LocalDateTime partyDate = LocalDateTime.now().plusDays(7);
        LocalDateTime showDate = LocalDateTime.now().plusDays(14);
        LocalDateTime showStartTime = LocalDateTime.of(2024, 3, 13, 18, 0);
        LocalDateTime showEndTime = LocalDateTime.of(2024, 3, 13, 23, 0);

        // Convert LocalDateTime to LocalDate
        LocalDate startOfSeason = showStartTime.toLocalDate();
        LocalDate endOfSeason = showEndTime.toLocalDate();


        Duration runtime = Duration.ofHours(2);

        party = new PartyVO(1, "Party 1", equipment, "Club XYZ", partyDate, "Buffet", "DJ John");
        show = new ShowVO(2, "Show 1", equipment, "Theater ABC", showDate, runtime, 1);

        // Initialize your TicketVO with the mocked event
        ticket1 = new SeatTicketVO(169, 100.0f, "T123", party);
//        mockSeatTicketVO = new SeatTicketVO(1, 100.0f, "T123", party);
        ticketMock = new MockTicketVO(1, "123", 100.0f, party);
        ticket2 = new SeasonTicketVO(27, 55.6f, show, startOfSeason, endOfSeason);
        ticket3 = new BackstageTicketVO(51, 23.33f, "B66", party, customer);

        seat = new SeatVO(1, 10); // Assuming this is how you create a SeatVO instance
    }

    @Test
    public void testGetPrice() {
        // Assuming getCharge returns 1.0 in the mock
        float expectedPrice = 100.0f; // Example price calculation
        assertEquals(expectedPrice, ticket1.getBasePrice(), 0.0);
    }

    @Test
    public void testCalculatePrice() {
        // Assuming getCharge returns 1.0 in the mock
        float expectedPrice = 100.0f; // Example price calculation
        assertEquals(expectedPrice, ticketMock.calculatePrice(), 0.0);
    }


    @Test
    public void testAddAndDeleteSeat() {
        ticket1.addSeat(seat);
        assertEquals(seat, ticket1.getSeat(0));

        ticket1.deleteSeat(seat);
        assertTrue(ticket1.seats.isEmpty());
    }

    @Test
    public void testGetSeat() {
        ticket1.addSeat(seat);
        assertEquals(seat, ticket1.getSeat(0));
    }

    @Test
    public void testHashCode() {
        int hashCode = ticket1.hashCode();
        assertNotNull(hashCode);
        assertEquals(hashCode, ticket1.hashCode());
    }



    @Test
    public void testEquals() {
        SeatTicketVO anotherSeatTicketVO = new SeatTicketVO(169, 100.0f, "T123", party);
        Object other = new Object();
        assertTrue(ticket1.equals(ticket1));
        assertTrue(ticket1 instanceof TicketVO, "ticket1 is an instance of TicketVO");
        assertTrue(anotherSeatTicketVO instanceof TicketVO, "anotherSeatTicketVO is also an instance of TicketVO");
        assertTrue(anotherSeatTicketVO.equals(anotherSeatTicketVO));
        assertTrue(ticket1.equals(anotherSeatTicketVO));
        assertEquals(ticket1, anotherSeatTicketVO);
        assertFalse(anotherSeatTicketVO.equals(other));
        assertEquals(ticket1, anotherSeatTicketVO);
        assertTrue(ticket1.equals(anotherSeatTicketVO) && anotherSeatTicketVO.equals(ticket1)); //testEqualsSymmetry
        assertFalse(ticket2.equals(ticket3) && ticket3.equals(ticket2)); //testEqualsDifferentValues
        assertFalse(ticket1.equals(null));

        assertFalse(anotherSeatTicketVO.equals(ticket2) && anotherSeatTicketVO.equals(ticket3));
        assertFalse(other.equals(ticket1));

        ticket1.setEvent(null);
        assertFalse(ticket1.equals(anotherSeatTicketVO) && anotherSeatTicketVO.equals(ticket1));
        assertNotSame(ticket1, anotherSeatTicketVO);
        ticket1.setEvent(party);
        ticket1.setSeat(null);
        anotherSeatTicketVO.setSeat("A147");
        assertFalse(ticket1.equals(anotherSeatTicketVO) && anotherSeatTicketVO.equals(ticket1));
        assertNotSame(ticket1, anotherSeatTicketVO);
        assertNotSame(ticket1.getSeat(), anotherSeatTicketVO.getSeat());

        ticket1.setSeat("A123");

        assertFalse(ticket1.getSeat().equals(anotherSeatTicketVO.getSeat()));
        assertNotEquals(ticket1.getSeat(), ticket3.getSeat());
        assertFalse(anotherSeatTicketVO.equals(ticket1) && ticket1.equals(anotherSeatTicketVO));

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
        ticket3.setSeat(null);

        // When calculating the hash code
        int actualHashCode = ticket3.hashCode();

        // Then manually calculate the expected hash code
        final int prime = 31;
        int expectedHashCode = 1;
        expectedHashCode = prime * expectedHashCode + ((ticket3.getEvent() == null) ? 0 : ticket3.getEvent().hashCode());
        expectedHashCode = prime * expectedHashCode + ticket3.getNumber();
        expectedHashCode = prime * expectedHashCode + Float.floatToIntBits(ticket3.getBasePrice());
        expectedHashCode = prime * expectedHashCode + ((ticket3.getSeat() == null) ? 0 : ticket3.getSeat().hashCode());
        expectedHashCode = prime * expectedHashCode + ((ticket3 instanceof BackstageTicketVO) ? ((BackstageTicketVO) ticket3).getCustomer().hashCode() : 0);

        // Assert that the actual hash code matches the expected hash code
        assertEquals(expectedHashCode, actualHashCode);
    }





    @Test
    public void testToString() {
        String ticketString = ticket1.toString();
        assertNotNull(ticketString);
        assertFalse(ticketString.isEmpty());
        assertTrue(ticketString.contains("Party 1"));
        assertTrue(ticketString.contains("169"));
    }


    @Test
    public void testGetSeatWithInvalidIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            ticket1.getSeat(0); // This should throw IndexOutOfBoundsException
        });
    }

    @Test
    public void testSetSeatNull() {
        ticket1.setSeat(null);

        assertEquals(0, ticket1.seats.size()); // Assuming the implementation ignores null values

    }

    @Test
    public void testAddSeatNull() {
        try {
            ticket1.addSeat(null);
            fail();

        } catch (NullPointerException ex) {
            assertEquals("Invalid!, seatVO should not be null", ex.getMessage());
        }

    }

    @Test
    public void testDeleteSeatNotPresent() {
        SeatVO nonExistingSeat = new SeatVO(2, 20); // Assuming this seat is not in the ticket
        ticket1.deleteSeat(nonExistingSeat);
        assertEquals(0, ticket1.seats.size()); // Assuming no change when deleting a non-existing seat
    }

    @Test
    public void testClone(){
        TicketVO clonedTicket = (TicketVO) ticket1.clone();
        assertEquals(ticket1.getId(), clonedTicket.getId());
        assertNotSame(ticket1, clonedTicket);
        assertEquals(ticket1, ticket1);
    }


    @Test
    public void testEqualsWithDifferentAttributes() {
        TicketVO differentTicket = new SeatTicketVO(2, 200.0f, "T124", show);
        assertFalse(ticket1.equals(differentTicket));
    }

    @Test
    public void testEqualsWithNull() {
        assertFalse(ticket1.equals(null));
    }

    @Test
    public void testEqualsWithDifferentClass() {
        Object otherObject = new Object();
        assertFalse(ticket1.equals(otherObject));
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
        assertEquals(1.0f, ticketMock.getCharge(), 0.0); // Assuming MockTicketVO returns 1.0f for getCharge
    }

    @Test
    public void testGetSeatOfTicket() {
        assertEquals("Seat-A345", ticketMock.getSeatOfTicket()); // Assuming MockTicketVO returns "Seat1" for getSeatOfTicket
    }


/*    @Test
    public void testCloneIsDeepCopy() {

        TicketVO clonedTicket = (TicketVO) ticket.clone();
        assertEquals(ticket.getId(), clonedTicket.getId());
        assertEquals(ticket.getNumber(), clonedTicket.getNumber());
        assertEquals(ticket.getBasePrice(), clonedTicket.getBasePrice(), 0.0);

        for (int i = 0; i < ticket.getSeats().size(); i++) {
            assertEquals(ticket.getSeats().get(i), clonedTicket.getSeats().get(i));
            assertNotSame(ticket.getSeats().get(i), clonedTicket.getSeats().get(i));
        }
    }*/

    @Test
    public void cloneNotSupportedExceptionTest() {
        // Since TicketVO implements Cloneable, CloneNotSupportedException should not be thrown.
        // To simulate the exception, you can use reflection to remove the Cloneable interface at runtime.
        // However, this would be an unusual use case and is not generally recommended.
        // This test case is here for educational purposes only.

        // Clone should be successful as TicketVO implements Cloneable
        assertDoesNotThrow(() -> ticket1.clone());
    }

    @Test
    public void testEquals_SameIdDifferentEvent() {
        TicketVO ticketWithDifferentEvent = new MockTicketVO(ticket1.getNumber(), ticket1.getId(), ticket1.getBasePrice(), show);
        assertFalse(ticket1.equals(ticketWithDifferentEvent), "Should return false since events are not the same");
    }

    @Test
    public void testEquals_DifferentIdSameEvent() {
        TicketVO ticketWithDifferentId = new MockTicketVO(ticket1.getNumber(), "T124", ticket1.getBasePrice(), party);
        assertFalse(ticket1.equals(ticketWithDifferentId), "Should return false since IDs are not the same");
    }


    @Test
    public void testEquals_CompletelyDifferent() {
        TicketVO completelyDifferentTicket = new MockTicketVO(2, "T125", 200.0f, show);
        assertFalse(ticket1.equals(completelyDifferentTicket), "Should return false since all attributes are different");
    }

    @Test
    public void testCompareTo_DifferentId() {
        TicketVO ticketWithDifferentId = new MockTicketVO(ticket1.getNumber(), "T999", ticket1.getBasePrice(), party);
        assertTrue(ticket1.compareTo(ticketWithDifferentId) != 0, "Comparison should yield that tickets are not the same");
    }


    private static class MockTicketVO extends TicketVO {
        public MockTicketVO(int number, String id, float price, EventVO event) {
            super(number, id, price, event);
        }

        @Override
        public float getCharge() {
            return 1.0f; // Mock implementation
        }

        @Override
        public String getSeatOfTicket() {
            return "Seat-A345";
        }
    }
}