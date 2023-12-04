package de.thb.dim.eventTom.valueObjects.customerManagement;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import de.thb.dim.eventTom.valueObjects.customerManagement.exceptions.CustomerNoDateOfBirthException;
import de.thb.dim.eventTom.valueObjects.customerManagement.exceptions.CustomerTooYoungException;
import de.thb.dim.eventTom.valueObjects.ticketSale.OrderVO;

public class CustomerVO extends PersonVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int nextId = 0;
	
	private int id;
	private Gender gender;
	private LocalDate dateOfBirth;
	private OrderVO order;



	/*public CustomerVO(String lastName, String firstName, String street, int houseNr, Gender gender, LocalDate dob) throws NullPointerException, CustomerTooYoungException {
		super(lastName, firstName, street, houseNr);
		id = nextId++;
		setGender(gender);
		setDateOfBirth(dob);
	}*/

	/**
	 * fixed by Osama Ahmad
	 * @param lastName
	 * @param firstName
	 * @param street
	 * @param houseNr
	 * @param gender
	 * @param dob
	 * @throws CustomerNoDateOfBirthException
	 * @throws CustomerTooYoungException
	 */
	public CustomerVO(String lastName, String firstName, String street, int houseNr, Gender gender, LocalDate dob) throws CustomerNoDateOfBirthException, CustomerTooYoungException {
		super(lastName, firstName, street, houseNr);
		id = nextId++;
		setGender(gender);
		setDateOfBirth(dob);
	}


	public CustomerVO(String lastName, String firstName, LocalDate dob) throws CustomerNoDateOfBirthException, CustomerTooYoungException {
		this(lastName, firstName, null, 0, Gender.U, dob);
	}

	public short calculateAge() throws CustomerNoDateOfBirthException {
		short alter = -1;
		Period age;
		LocalDate today = LocalDate.now();
		
		if (dateOfBirth == null) {
			throw new CustomerNoDateOfBirthException("Internal error: No date of birth.");
		}

		if (dateOfBirth != null) {
			age = Period.between(dateOfBirth, today);
			alter = (short) age.getYears();
		}
		return alter;
	}
	
	public static int getNextid() {
		return nextId;
	}

	public int getId() {
		return id;
	}

	public Gender getGender() {
		return gender;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public OrderVO getOrder() {
		return order;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	/*public void setDateOfBirth(LocalDate dateOfBirth) throws NullPointerException, CustomerTooYoungException {
		Objects.requireNonNull(dateOfBirth, "Date of Birth cannot be null");
		
		this.dateOfBirth = dateOfBirth;
		try { 
			if (calculateAge() < 18) {
				throw new CustomerTooYoungException("customer is under the age of 18");
			}
		} catch (CustomerNoDateOfBirthException e) {
			System.err.println(e.getMessage());
		}
	}*/

	/**
	 * fixed
	 * @param dateOfBirth
	 * @throws CustomerNoDateOfBirthException
	 * @throws CustomerTooYoungException
	 */
	public void setDateOfBirth(LocalDate dateOfBirth) throws CustomerNoDateOfBirthException, CustomerTooYoungException {
		if (dateOfBirth == null) {
			throw new CustomerNoDateOfBirthException("Date of Birth cannot be null");
		}

		this.dateOfBirth = dateOfBirth;
		if (calculateAge() < 18) {
			throw new CustomerTooYoungException("Customer is under the age of 18");
		}
	}


	public void setOrder(OrderVO order) {
		this.order = order;
	}
	
	public boolean hasOrder() {
		return (order != null);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((dateOfBirth == null) ? 0 : dateOfBirth.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + id;
		result = prime * result + ((order == null) ? 0 : order.hashCode());
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
		CustomerVO other = (CustomerVO) obj;
		if (dateOfBirth == null) {
			if (other.dateOfBirth != null) {
				return false;
			}
		} else if (!dateOfBirth.equals(other.dateOfBirth)) {
			return false;
		}
		return true;
	}
	
	private String dobToString() throws CustomerNoDateOfBirthException {
		String s = null;
		if (dateOfBirth == null) {
			throw new CustomerNoDateOfBirthException("Internal error: No date of birth.");
		}
		else s = dateOfBirth.format(DateTimeFormatter.ofPattern("dd MM yyyy"));
		return s;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("ID: " + getId());
		
		sb.append("\t" + super.toString());
		
		sb.append("\t" + this.getGender().toString());
		
		try {
			sb.append("\tDate of Birth: " + dobToString());
			sb.append("\tAge: " + calculateAge());
		} catch (CustomerNoDateOfBirthException e) {
			System.err.println(e.getMessage());
		}
		
		if (hasOrder()) {
			sb.append("\nOrder available: \n");
			sb.append(order.toString());
		}
		else sb.append("\nNo order available\n");
		
		return sb.toString();
	}

	

}
