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
	public void setSeatingArea(int seatingArea) {
		this.seatingArea = seatingArea;
	}
	public int getSeatNumber() {
		return seatNumber;
	}
	public void setSeatNumber(int seatnumber) {
		this.seatNumber = seatnumber;
	}
	
	

}
