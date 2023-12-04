package de.thb.dim.eventTom.valueObjects.ticketSale;

import de.thb.dim.eventTom.valueObjects.customerManagement.CustomerVO;
import de.thb.dim.eventTom.valueObjects.eventManagement.EventVO;

public class BackstageTicketVO extends TicketVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int nextId = 1;
	private CustomerVO customer;
	
	public BackstageTicketVO(int number, float price, String seat, EventVO event, CustomerVO customer) {
		super(number, event.getName() + " Seat " + nextId++, price,  event);
		this.customer = customer;
	}
	

	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((customer == null) ? 0 : customer.hashCode());
		return result;
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
		BackstageTicketVO other = (BackstageTicketVO) obj;
		if (customer == null) {
			if (other.customer != null) {
				return false;
			}
		} else if (!customer.equals(other.customer)) {
			return false;
		}
		return true;
	}

	public CustomerVO getCustomer() {
		return customer;
	}


	public void setCustomer(CustomerVO customer) {
		this.customer = customer;
	}



	@Override
	public float getCharge() {
		return 1.2f;
	}

	@Override
	public String getSeatOfTicket() {
		StringBuilder sb = new StringBuilder();
		sb.append(getSeat() + " +B");
		return sb.toString();
	}

}
