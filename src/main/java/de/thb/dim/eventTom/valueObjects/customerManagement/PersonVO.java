package de.thb.dim.eventTom.valueObjects.customerManagement;

import java.io.Serializable;

abstract class PersonVO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    protected String lastName;
    protected String firstName;
    protected String street;
    protected int houseNr;

    public PersonVO(String lastName, String firstName, String street, int houseNr) {
        setLastName(lastName);
        setFirstName(firstName);
        setStreet(street);
        setHouseNr(houseNr);
    }

    public PersonVO() {
        this(null, null, null, 0);
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getStreet() {
        return street;
    }

    public int getHouseNr() {
        return houseNr;
    }

<<<<<<< HEAD

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
=======
    /*public void setLastName(String lastName) {
        if (lastName == null || lastName == "") {
            throw new NullPointerException("lastName cannot be null");
        }
        this.lastName = lastName;
    }*/

	public void setLastName(String lastName) {
        this.lastName = lastName;
    }
>>>>>>> 74a3395 (init)

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setHouseNr(int houseNr) {
        this.houseNr = houseNr;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result + houseNr;
        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
        result = prime * result + ((street == null) ? 0 : street.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PersonVO)) {
            return false;
        }
        PersonVO other = (PersonVO) obj;
        if (firstName == null) {
            if (other.firstName != null) {
                return false;
            }
        } else if (!firstName.equals(other.firstName)) {
            return false;
        }
        if (houseNr != other.houseNr) {
            return false;
        }
        if (lastName == null) {
            if (other.lastName != null) {
                return false;
            }
        } else if (!lastName.equals(other.lastName)) {
            return false;
        }
        if (street == null) {
            if (other.street != null) {
                return false;
            }
        } else if (!street.equals(other.street)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format("Name: %s %s\n\tStreet: %s %s\n", firstName, lastName, street, houseNr);
    }

}
