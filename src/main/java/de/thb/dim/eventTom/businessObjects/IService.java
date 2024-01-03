package de.thb.dim.eventTom.businessObjects;

import de.thb.dim.eventTom.businessObjects.exceptions.NoCustomerException;
import de.thb.dim.eventTom.valueObjects.ticketSale.OrderVO;

interface IService {

<<<<<<< HEAD

=======
>>>>>>> 74a3395 (init)
	public String startService(OrderVO order) throws NoCustomerException, IllegalStateException, NullPointerException;
}
