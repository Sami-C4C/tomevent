package de.thb.dim.eventTom.valueObjects.ticketSale;

import de.thb.dim.eventTom.valueObjects.eventManagement.EventVO;

public class SeatTicketVO extends TicketVO {


	private static final long serialVersionUID = 1L;

	/* Error found by Osama Ahmad
	 * is static and not reset between tests, its value could continue
	 * to increment each time a ticket is created, leading to inconsistent IDs.
	 * see testTicketsIds() into TicketVOTest class
	 */
	private static int nextId = 1;
	
	public SeatTicketVO(int number, float price, String seat, EventVO event) {
		super(number, event.getName() + " Seat " + nextId++, price,  event);
	}


	

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		return true;
	}

	@Override
	public float getCharge() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getSeatOfTicket() {
		return getSeat();
	}



}
