package de.thb.dim.eventTom.businessObjects;

import de.thb.dim.eventTom.businessObjects.exceptions.NoCustomerException;
import de.thb.dim.eventTom.valueObjects.ticketSale.OrderVO;

interface IService {


	public String startService(OrderVO order) throws NoCustomerException, IllegalStateException, NullPointerException;
}