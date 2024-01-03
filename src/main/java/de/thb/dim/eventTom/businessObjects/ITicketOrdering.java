package de.thb.dim.eventTom.businessObjects;

import java.util.List;

import de.thb.dim.eventTom.businessObjects.exceptions.NoCustomerException;
import de.thb.dim.eventTom.businessObjects.exceptions.NoOrderException;
import de.thb.dim.eventTom.valueObjects.customerManagement.CustomerVO;
import de.thb.dim.eventTom.valueObjects.ticketSale.OrderVO;
import de.thb.dim.eventTom.valueObjects.ticketSale.TicketVO;

public interface ITicketOrdering {

	public OrderVO startNewOrder(CustomerVO customer);
	public void addTicket(TicketVO ticket) throws NoOrderException, IllegalStateException;
	public void deleteTicket(TicketVO ticket) throws NoOrderException, IllegalStateException;
	public float calculateTotalPrice() throws NoOrderException;
	public void confirmOrder() throws NoOrderException, NoCustomerException, IllegalStateException;
	public List<TicketVO> sortCart() throws NoOrderException;
	public List<TicketVO> sortCartByEvent() throws NoOrderException;
	public List<TicketVO> sortCartByPrice() throws NoOrderException;


}
