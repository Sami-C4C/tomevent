package de.thb.dim.eventTom.businessObjects.exceptions;

public class NoCustomerException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public NoCustomerException () { 
		super("No customer was indicated.");
	}
	
	public NoCustomerException(String message) {
		super(message);
	}
	
	

}
