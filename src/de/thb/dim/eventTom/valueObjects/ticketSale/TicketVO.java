package de.thb.dim.eventTom.valueObjects.ticketSale;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;

import de.thb.dim.eventTom.valueObjects.eventManagement.EventVO;

public abstract class TicketVO implements Serializable, Comparable<TicketVO>, Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final String id;
	protected final int number;
	protected float basePrice; // price not influenced by charges
	protected String seat;
	protected EventVO event;
	protected ArrayList<SeatVO> seats;

	public TicketVO(int number, String id, float price, EventVO event) {
		this.number = number;
		this.id = id;
		seats = new ArrayList<SeatVO>(number);
		setPrice(price);
		setEvent(event);
	}
	
	public float calculatePrice() {
		return (getBasePrice() * getCharge());
	}

	@Override
	public int compareTo(TicketVO t) {
		return getId().compareTo(t.getId());
	}
	
	public void addSeat(SeatVO s) {
		seats.add(s);
	}
	
	public void deleteSeat(SeatVO s) {
		seats.remove(s);
	}
	
	public SeatVO getSeat(int index) {
		return seats.get(index);
	}
	
	@Override
	public Object clone() {
		TicketVO ticket = null;
		try {
			ticket = (TicketVO) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError(); 
		}
		return ticket;
	}
	
	public String toString() {
		DecimalFormat dFormat = new DecimalFormat(".00"); // format of the ticket price
		StringBuffer sb = new StringBuffer(); 

		sb.append(getNumber() + " - ");
		sb.append(getSeatOfTicket());
		sb.append(" for " + getEvent().getName());
		sb.append("\n\tPrice:\t\t\t" + dFormat.format(calculatePrice()) + "€");
		sb.append("\n");

		return sb.toString();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((event == null) ? 0 : event.hashCode());
		result = prime * result + number;
		result = prime * result + Float.floatToIntBits(basePrice);
		result = prime * result + ((seat == null) ? 0 : seat.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		TicketVO other = (TicketVO) obj;
		if (event == null) {
			if (other.event != null) {
				return false;
			}
		} else if (!event.equals(other.event)) {
			return false;
		}
		if (number != other.number) {
			return false;
		}
		if (Float.floatToIntBits(basePrice) != Float.floatToIntBits(other.basePrice)) {
			return false;
		}
		if (seat == null) {
			if (other.seat != null) {
				return false;
			}
		} else if (!seat.equals(other.seat)) {
			return false;
		}
		return true;
	}
	
	public String getId() {
		return id;
	}
	
	public int getNumber() {
		return number;
	}

	// rework (nclude Charge)
	public float getBasePrice() {
		return basePrice;
	}
 

	public String getSeat() {
		return seat;
	}


	public EventVO getEvent() {
		return event;
	}


	public void setPrice(float basePrice) {
		this.basePrice = basePrice;
	}


	public void setSeat(String seat) {
		this.seat = seat;
	}


	public void setEvent(EventVO event) {
		this.event = event;
	}


	public abstract float getCharge();
	
	public abstract String getSeatOfTicket();
	
}
