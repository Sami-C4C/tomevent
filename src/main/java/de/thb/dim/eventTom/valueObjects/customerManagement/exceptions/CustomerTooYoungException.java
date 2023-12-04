package de.thb.dim.eventTom.valueObjects.customerManagement.exceptions;

public class CustomerTooYoungException extends Exception {

		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CustomerTooYoungException () {
		super("The Customer is not an adult");
	}
		
	public CustomerTooYoungException (String message) {
		super(message);
	}
}
