package de.thb.dim.eventTom.businessObjects.exceptions;

public class NoOrderException extends Exception {

	private static final long serialVersionUID = 1L;


	public NoOrderException () { 
		super("Order could not be found.");
	}


	public NoOrderException(String message) {
		super(message);
	}

}
