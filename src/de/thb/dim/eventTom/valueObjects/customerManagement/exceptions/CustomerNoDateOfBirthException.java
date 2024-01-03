package de.thb.dim.eventTom.valueObjects.customerManagement.exceptions;

public class CustomerNoDateOfBirthException extends Exception {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Initialize Exception
	 */
	public CustomerNoDateOfBirthException(){
		super("There is no date of birth available.");
	}
	
	/**
	 * Initialize detailMessage
	 * 
	 * @param message 
	 */
	public CustomerNoDateOfBirthException(String message){
		super(message);
	}

}
