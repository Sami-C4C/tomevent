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

	/**
	 * setEvent and getEvent are implemented by Osama ahmad, MN: 20233244.
	 *  SeatTickets are available for both event-types party and show.
	 * @return
	 */





	/**
	 * Fixed by Osama Ahmad
	 * I get error while testing test_calculatePriceSeatTicket() in OrderVOTest.
	 * @return
	 */
	@Override
	public float getCharge() {
		return 1.1f; // A hypothetical charge rate for seat tickets
	}


	@Override
	public String getSeatOfTicket() {
		return getSeat();
	}



}