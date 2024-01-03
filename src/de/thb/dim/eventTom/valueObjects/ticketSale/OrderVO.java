package de.thb.dim.eventTom.valueObjects.ticketSale;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import de.thb.dim.eventTom.valueObjects.customerManagement.CustomerVO;

public class OrderVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int orderNr;
	private StateOfOrderVO state;
	private LocalDateTime timestampStartedOrder;
	private LocalDateTime timestampFinishedOrder;
	private List<TicketVO> cart;
	private CustomerVO customer;
	
	// LinkedList: faster adding and deleting of objects in the list
	// ArrayList: faster access objects at certain index
	
	public OrderVO(int orderNr, StateOfOrderVO state, LocalDateTime timestampStartedOrder, CustomerVO customer) {
		this.orderNr = orderNr;
		setState(state);
		setTimestampStartedOrder(timestampStartedOrder);
		setCustomer(customer);
		cart = new LinkedList<TicketVO>();
	}

	public int getOrderNr() {
		return orderNr;
	}

	public StateOfOrderVO getState() {
		return state;
	}

	public LocalDateTime getTimestampStartedOrder() {
		return timestampStartedOrder;
	}

	public LocalDateTime getTimestampFinishedOrder() {
		return timestampFinishedOrder;
	}

	public List<TicketVO> getCart() {
		return cart;
	}

	public CustomerVO getCustomer() {
		return customer;
	}

	public void setState(StateOfOrderVO state) {
		this.state = state;
	}

	public void setTimestampStartedOrder(LocalDateTime timestampStartedOrder) {
		this.timestampStartedOrder = timestampStartedOrder;
	}

	public void setTimestampFinishedOrder(LocalDateTime timestampFinishedOrder) {
		this.timestampFinishedOrder = timestampFinishedOrder;
	}

	public void setCart(List<TicketVO> cart) {
		this.cart = cart;
	}

	public void setCustomer(CustomerVO customer) {
		this.customer = customer;
	}
	
	public void addTicket(TicketVO ticket) {
		cart.add(ticket);
	}
	
	public void deleteTicket(int index) {
		cart.remove(index);
	}
	
	public void deleteTicket(TicketVO ticket) {
		cart.remove(ticket);
	}
	
	public float calculatePriceTickets() {
		float totalPrice = 0.0f;
		for(TicketVO ticket : cart) {
			totalPrice += ticket.calculatePrice();
		}
		return totalPrice;
	}
	
	public TicketVO getTicket(int index) {
		return cart.get(index);
	}
	
	public int getNumberOfTickets() {
		return cart.size();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cart == null) ? 0 : cart.hashCode());
		result = prime * result + ((customer == null) ? 0 : customer.hashCode());
		result = prime * result + orderNr;
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((timestampFinishedOrder == null) ? 0 : timestampFinishedOrder.hashCode());
		result = prime * result + ((timestampStartedOrder == null) ? 0 : timestampStartedOrder.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null)
			return false;
		if (getClass() != obj.getClass()) {
			return false;
		}
		OrderVO other = (OrderVO) obj;
		if (orderNr != other.getOrderNr()) {
			return false;
		}
		return true;
	}
	
	public String toString() {

		StringBuilder text = new StringBuilder(String.format(
				"OrderVO " + getOrderNr()
						+ " from %1$tm/%1$td/%1$tY %1$tH:%1$tM with delivery at  %2$tm/%2$td/%2$tY %2$tH:%2$tM\n",
				timestampStartedOrder, timestampFinishedOrder));

		text.append("of customer: " + customer.getLastName() + " " + customer.getFirstName() + ", ID of customer: "
				+ customer.getId() + "\n");

		for (int i = 0; i < cart.size(); i++) {
			if (cart.get(i) != null) {
				text.append(cart.get(i).toString());
				text.append("\n");
			}
		}

		return text.toString();

	}
	
	

}
