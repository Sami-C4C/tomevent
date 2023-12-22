package de.thb.dim.eventTom.valueObjects.ticketSale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Osama Ahmad, MN: 20233244
 */
class SeatVOTest {

    private SeatVO seat;

    @BeforeEach
    public void setUp() {
        seat = new SeatVO(1, 10);
    }

    @Test
    public void testGetAndSetSeatingArea() {
        // Test initial value
        assertEquals(1, seat.getSeatingArea(), "Initial seating area should be 1");

        // Test setter and getter
        seat.setSeatingArea(2);
        assertEquals(2, seat.getSeatingArea(), "Seating area should be updated to 2");
    }

    @Test
    public void testGetAndSetSeatNumber() {
        // Test initial value
        assertEquals(10, seat.getSeatNumber(), "Initial seat number should be 10");

        // Test setter and getter
        seat.setSeatNumber(20);
        assertEquals(20, seat.getSeatNumber(), "Seat number should be updated to 20");
    }

    @Test
    public void testSeatingAreaWithNegativeValue() {
        // Assuming the seating area should not be negative
        assertThrows(IllegalArgumentException.class, () -> {
            seat.setSeatingArea(-1);
        }, "Setting a negative seating area should throw an IllegalArgumentException");
    }

    @Test
    public void testSeatNumberWithNegativeValue() {
        // Assuming the seat number should not be negative
        assertThrows(IllegalArgumentException.class, () -> {
            seat.setSeatNumber(-10);
        }, "Setting a negative seat number should throw an IllegalArgumentException");
    }

    @Test
    public void testSeatingAreaBoundaryValue() {
        // Test for a boundary value if applicable
        seat.setSeatingArea(0);
        assertEquals(0, seat.getSeatingArea(), "Seating area should accept boundary value 0");
    }

    @Test
    public void testSeatNumberBoundaryValue() {
        // Test for a boundary value if applicable
        seat.setSeatNumber(0);
        assertEquals(0, seat.getSeatNumber(), "Seat number should accept boundary value 0");
    }

}