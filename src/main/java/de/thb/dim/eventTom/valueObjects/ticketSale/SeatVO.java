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
     * we have to test also the invalid seating area
     * @param seatingArea
     */
    public void setSeatingArea(int seatingArea) {
        if (seatingArea < 0) {
            throw new IllegalArgumentException("Invalid settingArea");
        }
        this.seatingArea = seatingArea;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    /**
     * To Test also the invalid seat number
     * @param seatNumber
     */
    public void setSeatNumber(int seatNumber) {
        if (seatNumber < 0) {
            throw new IllegalArgumentException("Invalid seatNumber");
        }
        this.seatNumber = seatNumber;
    }

}
