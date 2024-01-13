package de.thb.dim.eventTom.valueObjects.ticketSale;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;

import de.thb.dim.eventTom.valueObjects.eventManagement.EventVO;

public abstract class TicketVO implements Serializable, Comparable<TicketVO>, Cloneable {

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
		seats = new ArrayList<>(number);
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

	/**
	 * Osama Ahmad, MN: 20233244
	 * Bug discovered while writing the test testAddSeatNull() into TicketVOTest.
	 *
	 * @param seat
	 */
	public void addSeat(SeatVO seat) {
		if (seat == null) {
			throw new NullPointerException("Invalid!, seatVO should not be null");
		}
		seats.add(seat);
	}

	public void deleteSeat(SeatVO s) {
		seats.remove(s);
	}

	public SeatVO getSeat(int index) {
		return seats.get(index);
	}




	/**
	 * CloneNotSupportedException is never thrown and this function.
	 * because this class already implements the Cloneable interface
	 * doesn't handle the deep cloning of mutable objects. If the
	 * EventVO or seats are mutable, this could lead to shared references between cloned instances.
	 * @return
	 */
	/*@Override
	public Object clone() {
		TicketVO ticket = null;
		try {
			ticket = (TicketVO) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError();
		}
		return ticket;
	}*/


	/**
	 * Fixed by Osama Ahmad, MN:20233244
	 * @return
	 */

	/**
	 * @author Osama Ahmad
	 * @return
	 * @throws CloneNotSupportedException
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		// This call to super.clone() might throw CloneNotSupportedException
		// which should never happen since this class implements Cloneable
		TicketVO clonedTicket = (TicketVO) super.clone();

		// Perform deep cloning of mutable fields if necessary
		// For example, if we have a list of seats and you want to clone it as well
		clonedTicket.seats = new ArrayList<>(this.seats);

		// If EventVO is mutable and you need a deep copy, you might need to clone it as well
		// Otherwise, a shallow copy (just assigning the reference) might be enough
		// clonedTicket.event = (EventVO) this.event.clone();

		return clonedTicket;
	}

	public String toString() {
		DecimalFormat dFormat = new DecimalFormat(".00"); // format of the ticket price
		StringBuffer sb = new StringBuffer();

		sb.append(getNumber() + " - ");
		sb.append(getSeatOfTicket());
		sb.append(" for " + getEvent().getName());
		sb.append("\n\tPrice:\t\t\t" + dFormat.format(calculatePrice()) + "?");
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

	// rework (include Charge)
	public float getBasePrice() {
		//Osama Ahmad
//		return basePrice * getCharge(); // I got 0.0 instead of base-Price of seat-ticket see → calculatePriceSeatTicket() into OrderVOTest.
		return basePrice;
	}


	public String getSeat() {
		return seat;
	}


	public void setPrice(float basePrice) {
		this.basePrice = basePrice;
	}


	public void setSeat(String seat) {
		this.seat = seat;
	}

	/**
	 * Osama Ahmad, MN: 20233244
	 * I have overriden setEvent in BackstageTicketVO and SeasonTicketVO, because the Season-ticket and Backstage-ticket are not available for party.
	 * therefore I have implemented both function into all ticketcategories to check the suitable event-type for each ticket-category
	 * @return
	 */

	public void setEvent(EventVO event) {
		this.event = event;
	}

	public EventVO getEvent() {
		return event;
	}





	public abstract float getCharge();

	public abstract String getSeatOfTicket();

}