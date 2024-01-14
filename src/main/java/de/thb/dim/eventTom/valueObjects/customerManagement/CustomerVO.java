package de.thb.dim.eventTom.valueObjects.customerManagement;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import de.thb.dim.eventTom.valueObjects.customerManagement.exceptions.CustomerNoDateOfBirthException;
import de.thb.dim.eventTom.valueObjects.customerManagement.exceptions.CustomerTooYoungException;
import de.thb.dim.eventTom.valueObjects.ticketSale.OrderVO;


/**
 * Osama Ahmad:
 * Constructor for CustomerVO.
 * <p>
 * Note: This class does not implement the Cloneable interface. This is a deliberate design choice to ensure the security and integrity
 * of sensitive customer information. Cloning objects that contain sensitive data can lead to inadvertent exposure or mismanagement of
 * such data. Therefore, to prevent the risks associated with the standard object cloning process in Java, this class refrains from
 * providing a cloning mechanism.
 * <p>
 * If a use case arises where duplicating a CustomerVO object is necessary, consider implementing a secure and controlled cloning mechanism
 * that adheres to data protection regulations and best practices. This might involve deep copying mutable fields and ensuring all sensitive
 * information is handled securely throughout the application. Any such implementation should be thoroughly reviewed and tested to prevent
 * unintentional data leaks or breaches.
 */

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




    /**
     * fixed by Tobi Emma, MN:20216374
     * In Java, NullPointerException is a runtime exception (unchecked exception) that typically doesn't
     * need to be declared in a method or constructor's throws clause.
     *
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



    /**
     * Fixed by Tobi Emma Nankpan
     *
     * @return
     * @throws CustomerNoDateOfBirthException
     */
    public short calculateAge() throws CustomerNoDateOfBirthException {
        if (dateOfBirth == null) {
            throw new CustomerNoDateOfBirthException("Internal error: No date of birth.");
        }

        return (short) Period.between(dateOfBirth, LocalDate.now()).getYears();
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



    /**
     * Fixed by Tobi Emma Nankpan.
     *
     * @param dateOfBirth
     * @throws CustomerNoDateOfBirthException
     * @throws CustomerTooYoungException
     */
    public void setDateOfBirth(LocalDate dateOfBirth) throws CustomerNoDateOfBirthException, CustomerTooYoungException {
        if (dateOfBirth == null) {
            throw new CustomerNoDateOfBirthException("Date of Birth cannot be null");
        }
        if (LocalDate.now().isBefore(dateOfBirth)) {
            throw new CustomerNoDateOfBirthException("Date of Birth cannot be in the future");
        }

        Period age = Period.between(dateOfBirth, LocalDate.now());
        if (age.getYears() < 18) {
            throw new CustomerTooYoungException("Customer is under the age of 18");
        }

        this.dateOfBirth = dateOfBirth;
    }


    public void setOrder(OrderVO order) {
        this.order = order;
    }

    public boolean hasOrder() {
        return (order != null);
    }

    /**
     * Fixed by Tobi Emma Nankpan
     *
     * @return
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((dateOfBirth == null) ? 0 : dateOfBirth.hashCode());
        result = prime * result + ((gender == null) ? 0 : gender.hashCode());
        result = prime * result + ((order == null) ? 0 : order.hashCode());
        // removed the ID from hash code computation
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

    /**
     * Fixed by Tobi Emma Nankpan
     * changed to public to test dobToString,see into CustomerVOTest: test_dobToString_WithNullDateOfBirth_ShouldThrowException
     *
     * @return
     * @throws CustomerNoDateOfBirthException
     */
    public String dobToString() throws CustomerNoDateOfBirthException {
        if (dateOfBirth == null) {
            throw new CustomerNoDateOfBirthException("Internal error: No date of birth.");
        }
        return dateOfBirth.format(DateTimeFormatter.ofPattern("dd MM yyyy"));
    }







    /**
     * Fixed by Tobi Emma Nankpan
     *
     * @return
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("ID: ").append(getId());
        sb.append("\t").append(super.toString());
        sb.append("\t").append(this.getGender());

        try {
            sb.append("\tDate of Birth: ").append(dobToString());
            sb.append("\tAge: ").append(calculateAge());
        } catch (CustomerNoDateOfBirthException e) {
            sb.append("\t").append(e.getMessage());
        }

        if (hasOrder()) {
            sb.append("\nOrder available: \n").append(order.toString());
        } else {
            sb.append("\nNo order available\n");
        }

        return sb.toString();
    }

}