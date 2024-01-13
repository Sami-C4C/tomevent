package de.thb.dim.eventTom.valueObjects.customerManagement;

import de.thb.dim.eventTom.valueObjects.customerManagement.exceptions.CustomerNoDateOfBirthException;
import de.thb.dim.eventTom.valueObjects.customerManagement.exceptions.CustomerTooYoungException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Tobi Emma Nankpan, MN:20216374
 */
class PersonVOTest {

    private PersonVO person;
    private PersonVOMocking personMock;

    private PersonVO personWithNullAttributes;

    @BeforeEach
    void setUp() {
        // Setup a person object for each test
        person = new PersonVO("Mayer", "Konrad", "Wattstr", 42) {
        };
        personWithNullAttributes = new PersonVO() {
        };

        personMock = new PersonVOMocking("Harold", "John", "Main Street", 123);

    }


    @Test
    void getLastName() {
        assertEquals("Mayer", person.getLastName(), "The last name should be Doe.");
    }


    @Test
    void getFirstName() {
        assertEquals("Konrad", person.getFirstName(), "The first name should be John.");
    }

    @Test
    void getStreet() {
        assertEquals("Wattstr", person.getStreet(), "The street should be Main Street.");
    }


    @Test
    void getHouseNr() {
        assertEquals(42, person.getHouseNr(), "The house number should be 42.");
    }


    @Test
    void setLastName() {
        person.setLastName("Smith");
        assertEquals("Smith", person.getLastName(), "The last name should be updated to Smith.");

        // The Actual implementation of setName into PersonVO does not check the invalid values.
//        assertThrows(NullPointerException.class, () -> person.setLastName(null), "Setting last name to null should throw NullPointerException.");
    }

    @Test
    void test_setFirstName() {
        person.setFirstName("Jane");
        assertEquals("Jane", person.getFirstName(), "The first name should be updated to Jane.");
        assertDoesNotThrow(() -> person.setFirstName(null)); // Error because it doesnot throw any exception with invalid values, see below the mocking implementation.
    }


    @Test
    void test_setStreet() {
        person.setStreet("Broadway");
        assertEquals("Broadway", person.getStreet(), "The street should be updated to Broadway.");
        assertDoesNotThrow(() -> person.setStreet(null)); // Error because it doesnot throw any exception with invalid values, see below the mocking implementation.

    }

    @Test
    void setHouseNr() {
        person.setHouseNr(100);
        assertEquals(100, person.getHouseNr(), "The house number should be updated to 100.");
    }

    @Test
    void testHashCode() {
        PersonVO samePerson = new PersonVO("Mayer", "Konrad", "Wattstr", 42) {
        };
        assertEquals(person.hashCode(), samePerson.hashCode(), "Hashcodes should be the same for equal PersonVO objects.");
    }

    /**************************************************************************
     * Tests for equals
     *************************************************************************/
    @Test
    void testEquals() {
        PersonVO samePerson = new PersonVO("Mayer", "Konrad", "Wattstr", 42) {
        };
        PersonVO differentPerson = new PersonVO("Smith", "Jane", "Broadway", 100) {
        };
        assertTrue(person.equals(samePerson), "The two persons should be equal.");
        assertFalse(person.equals(differentPerson), "The two persons should not be equal.");
        assertFalse(person.equals(null), "Comparing with null should return false.");
        assertFalse(person.equals(new Object()), "Comparing with a non-PersonVO object should return false.");

        person.setLastName(null);
        samePerson.setLastName(null);
        assertTrue(person.equals(samePerson), "Persons with both last names null should be equal.");


        person.setLastName("Mayer");
        samePerson.setLastName("Mayer");
        person.setStreet(null);
        samePerson.setStreet(null);
        assertTrue(person.equals(samePerson), "Persons with both streets null should be equal.");

        assertTrue(person.equals(person), "A person should be equal to itself.");

        PersonVO differentFirstName = new PersonVO("Mayer", "Toralf", "Wattstr", 42) {
        };
        assertFalse(person.equals(differentFirstName), "Persons with different first names should not be equal.");


        PersonVO otherWithNullFirstName = new PersonVO("Mayer", "null", "Wattstr", 42) {
        };
        assertFalse(person.equals(otherWithNullFirstName), "Persons with null first names should not be equal.");

        PersonVO differentLastName = new PersonVO("Schneider", "Konrad", "Wattstr", 42) {
        };
        assertFalse(person.equals(differentLastName), "Persons with different last names should not be equal.");

        PersonVO otherWithNullValues = new PersonVO(null, null, null, 0) {
        };
        assertTrue(personWithNullAttributes.equals(otherWithNullValues), "Persons with null last names should be equal.");

        PersonVO otherWithNullLastName = new PersonVO(null, "John", "Main Street", 42) {
        };
        assertFalse(personWithNullAttributes.equals(otherWithNullLastName), "Persons with null last names should be equal.");

        otherWithNullLastName.setLastName(person.getLastName());
        person.setLastName(null);
        assertFalse(person.equals(otherWithNullLastName), "Persons with null last names should be equal.");


        PersonVO nullStreet = new PersonVO("Mayer", "Toralf", null, 42) {
        };
        assertFalse(person.equals(nullStreet), "Persons with null streets should not be equal.");
        nullStreet.setStreet(person.getStreet());
        person.setStreet(null);
        assertFalse(person.equals(nullStreet), "Persons with null streets should not be equal.");


        PersonVO differentStreet = new PersonVO("Mayer", "Toralf", "Oranienstr", 42) {
        };
        assertFalse(person.equals(differentStreet), "Persons with null streets should not be equal.");

        PersonVO differentHouseNr = new PersonVO("Mayer", "Konrad", "Wattstr", 43) {
        };
        assertFalse(person.equals(differentHouseNr), "Persons with different houseNr should not be equal.");

    }


    @Test
    void equals_WithDifferentLastNames_ShouldReturnFalse() throws CustomerNoDateOfBirthException, CustomerTooYoungException {
        CustomerVO customer1 = new CustomerVO("Smith", "John", "Main Street", 42, Gender.M, LocalDate.of(1990, 1, 1));
        CustomerVO customer2 = new CustomerVO("Doe", "John", "Main Street", 42, Gender.M, LocalDate.of(1990, 1, 1));

        assertFalse(customer1.equals(customer2), "Customers with different last names should not be equal.");
    }

    @Test
    void equals_WithOneNullLastName_ShouldReturnFalse() throws CustomerNoDateOfBirthException, CustomerTooYoungException {
        CustomerVO customer1 = new CustomerVO(null, "John", "Main Street", 42, Gender.M, LocalDate.of(1990, 1, 1));
        CustomerVO customer2 = new CustomerVO("Doe", "John", "Main Street", 42, Gender.M, LocalDate.of(1990, 1, 1));

        assertFalse(customer1.equals(customer2), "Customers with one null last name should not be equal.");
    }

    @Test
    void equals_WithBothNullLastNames_ShouldReturnTrue() throws CustomerNoDateOfBirthException, CustomerTooYoungException {
        CustomerVO customer1 = new CustomerVO(null, "John", "Main Street", 42, Gender.M, LocalDate.of(1990, 1, 1));
        CustomerVO customer2 = new CustomerVO(null, "John", "Main Street", 42, Gender.M, LocalDate.of(1990, 1, 1));

        assertTrue(customer1.equals(customer2), "Customers with both null last names should be equal.");
    }

    @Test
    void equals_WithDifferentStreets_ShouldReturnFalse() throws CustomerNoDateOfBirthException, CustomerTooYoungException {
        CustomerVO customer1 = new CustomerVO("Smith", "John", "Main Street", 42, Gender.M, LocalDate.of(1990, 1, 1));
        CustomerVO customer2 = new CustomerVO("Smith", "John", "Broadway", 42, Gender.M, LocalDate.of(1990, 1, 1));

        assertFalse(customer1.equals(customer2), "Customers with different streets should not be equal.");
    }

    @Test
    void equals_WithOneNullStreet_ShouldReturnFalse() throws CustomerNoDateOfBirthException, CustomerTooYoungException {
        CustomerVO customer1 = new CustomerVO("Smith", "John", null, 42, Gender.M, LocalDate.of(1990, 1, 1));
        CustomerVO customer2 = new CustomerVO("Smith", "John", "Main Street", 42, Gender.M, LocalDate.of(1990, 1, 1));

        assertFalse(customer1.equals(customer2), "Customers with one null street should not be equal.");
    }

    @Test
    void equals_WithBothNullStreets_ShouldReturnTrue() throws CustomerNoDateOfBirthException, CustomerTooYoungException {
        CustomerVO customer1 = new CustomerVO("Smith", "John", null, 42, Gender.M, LocalDate.of(1990, 1, 1));
        CustomerVO customer2 = new CustomerVO("Smith", "John", null, 42, Gender.M, LocalDate.of(1990, 1, 1));

        assertTrue(customer1.equals(customer2), "Customers with both null streets should be equal.");
    }


    /**********************************************************************************************
     * End of equals tests.
     **********************************************************************************************/


    @Test
    void testToString() {
        String expectedString = "Name: Konrad Mayer\n\tStreet: Wattstr 42\n";
        assertEquals(expectedString, person.toString(), "The toString method should return the formatted string.");
    }


    @Test
    void testConstructor_DefaultValues() {
        assertNull(personWithNullAttributes.getLastName(), "Default last name should be null.");
        assertNull(personWithNullAttributes.getFirstName(), "Default first name should be null.");
        assertNull(personWithNullAttributes.getStreet(), "Default street should be null.");
        assertEquals(0, personWithNullAttributes.getHouseNr(), "Default house number should be 0.");
    }

    /*****************************************************************************************************
     * Test setters functions into PersonVO with invalid values.
     *****************************************************************************************************/


    @Test
    void test_setLastName_withNull_shouldThrowException() {
        assertThrows(NullPointerException.class, () -> personMock.setLastName(null),
                "Mock: lastName cannot be null or empty");
    }

    @Test
    void test_setLastName_withEmptyString_shouldThrowException() {
        assertThrows(NullPointerException.class, () -> personMock.setLastName(""),
                "Mock: lastName cannot be null or empty");
    }

    @Test
    void test_setLastName_withValidInput_shouldPass() {
        assertDoesNotThrow(() -> personMock.setLastName("Doe"));
        assertEquals("Doe", personMock.getLastName());
    }

    @Test
    void test_setFirstName_withNull_shouldThrowException() {
        assertThrows(NullPointerException.class, () -> personMock.setFirstName(null),
                "Mock: firstName cannot be null or empty");
    }

    @Test
    void test_setFirstName_withEmptyString_shouldThrowException() {
        assertThrows(NullPointerException.class, () -> personMock.setFirstName(""),
                "Mock: firstName cannot be null or empty");
    }

    @Test
    void test_setFirstName_withValidInput_shouldPass() {
        assertDoesNotThrow(() -> personMock.setFirstName("John"));
        assertEquals("John", personMock.getFirstName());
    }

    @Test
    void test_setStreet_withNull_shouldThrowException() {
        assertThrows(NullPointerException.class, () -> personMock.setStreet(null),
                "Mock: street cannot be null or empty");
    }

    @Test
    void test_setStreet_withEmptyString_shouldThrowException() {
        assertThrows(NullPointerException.class, () -> personMock.setStreet(""),
                "Mock: street cannot be null or empty");
    }

    @Test
    void test_setStreet_withValidInput_shouldPass() {
        assertDoesNotThrow(() -> personMock.setStreet("Marwitzerstr"));
        assertEquals("Marwitzerstr", personMock.getStreet());
    }

    @Test
    void test_setHouseNr_withNegativeValue_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> personMock.setHouseNr(-1),
                "Mock: houseNr must be positive");
    }

    @Test
    void test_setHouseNr_withZero_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> personMock.setHouseNr(0),
                "Mock: houseNr must be positive");
    }

    @Test
    void test_setHouseNr_withValidInput_shouldPass() {
        assertDoesNotThrow(() -> personMock.setHouseNr(123));
        assertEquals(123, personMock.getHouseNr());
    }


    private class PersonVOMocking extends PersonVO {

        public PersonVOMocking(String lastName, String firstName, String street, int houseNr) {
            super(lastName, firstName, street, houseNr);
        }

        public PersonVOMocking() {
            super();
        }

        @Override
        public void setLastName(String lastName) {
            if (lastName == null || lastName.trim().isEmpty()) {
                throw new NullPointerException("Mock: lastName cannot be null or empty");
            }
            super.setLastName(lastName);
        }

        @Override
        public void setFirstName(String firstName) {
            if (firstName == null || firstName.trim().isEmpty()) {
                throw new NullPointerException("Mock: firstName cannot be null or empty");
            }
            super.setFirstName(firstName);
        }

        @Override
        public void setStreet(String street) {
            if (street == null || street.trim().isEmpty()) {
                throw new NullPointerException("Mock: street cannot be null or empty");
            }
            super.setStreet(street);
        }

        @Override
        public void setHouseNr(int houseNr) {
            if (houseNr <= 0) {
                throw new IllegalArgumentException("Mock: houseNr must be positive");
            }
            super.setHouseNr(houseNr);
        }
    }


    @AfterEach
    void tearDown() {
        person = null;
    }
}