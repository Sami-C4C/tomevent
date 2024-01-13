package de.thb.dim.eventTom.valueObjects.ticketSale;

public class SeatVO {
    private int seatingArea;
    private int seatNumber;

    public SeatVO(int seatingArea, int seatNumber) {
        this.seatingArea = seatingArea;
        this.seatNumber = seatNumber;
    }

    public int getSeatingArea() {
        return seatingArea;
    }

    /**
     * Fixed by Osama Ahmad.
     * we have to test also the invalid seating area
     * @param seatingArea
     */
    public void setSeatingArea(int seatingArea) {
        if (seatingArea < 0) {
            throw new IllegalArgumentException("Seating Area cannot be negative");
        }
        this.seatingArea = seatingArea;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    /**
     * Fixed by Osama Ahmad
     * To Test also the invalid seat number
     * @param seatNumber
     */
    public void setSeatNumber(int seatNumber) {
        if (seatNumber < 0) {
            throw new IllegalArgumentException("Seat number must not be negative");
        }
        this.seatNumber = seatNumber;
    }

}