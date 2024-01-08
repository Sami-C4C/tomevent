package de.thb.dim.eventTom.valueObjects.customerManagement;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonVOTest {
    private PersonVO person;

    private PersonVO personWithNullAttributes;

    @BeforeEach
    void setUp() {
        // Setup a person object for each test
        person = new PersonVO("Mayer", "Konrad", "Wattstr", 42) {
        };
        personWithNullAttributes = new PersonVO() {
        };
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

/*    @Test
    void setLastName() {
        person.setLastName("Smith");
        assertEquals("Smith", person.getLastName(), "The last name should be updated to Smith.");
        assertThrows(NullPointerException.class, () -> person.setLastName(null), "Setting last name to null should throw NullPointerException.");
    }*/

    @Test
    void setFirstName() {
        person.setFirstName("Jane");
        assertEquals("Jane", person.getFirstName(), "The first name should be updated to Jane.");
    }

    @Test
    void setStreet() {
        person.setStreet("Broadway");
        assertEquals("Broadway", person.getStreet(), "The street should be updated to Broadway.");
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

        PersonVO differentFirstName = new PersonVO("Mayer", "Toralf", "Wattstr", 42) {};
        assertFalse(person.equals(differentFirstName), "Persons with different first names should not be equal.");


        PersonVO otherWithNullFirstName = new PersonVO("Mayer", "null", "Wattstr", 42) {};
        assertFalse(person.equals(otherWithNullFirstName), "Persons with null first names should not be equal.");

        PersonVO differentLastName = new PersonVO("Schneider", "Konrad", "Wattstr", 42) {};
        assertFalse(person.equals(differentLastName), "Persons with different last names should not be equal.");

        PersonVO otherWithNullValues = new PersonVO(null, null, null, 0) {};
        assertTrue(personWithNullAttributes.equals(otherWithNullValues), "Persons with null last names should be equal.");

        PersonVO otherWithNullLastName = new PersonVO(null, "John", "Main Street", 42) {};
        assertFalse(personWithNullAttributes.equals(otherWithNullLastName), "Persons with null last names should be equal.");

        otherWithNullLastName.setLastName(person.getLastName());
        person.setLastName(null);
        assertFalse(person.equals(otherWithNullLastName), "Persons with null last names should be equal.");


        PersonVO nullStreet = new PersonVO("Mayer", "Toralf", null, 42) {};
        assertFalse(person.equals(nullStreet), "Persons with null streets should not be equal.");
        nullStreet.setStreet(person.getStreet());
        person.setStreet(null);
        assertFalse(person.equals(nullStreet), "Persons with null streets should not be equal.");


        PersonVO differentStreet = new PersonVO("Mayer", "Toralf", "Oranienstr", 42) {};
        assertFalse(person.equals(differentStreet), "Persons with null streets should not be equal.");

        PersonVO differentHouseNr = new PersonVO("Mayer", "Konrad", "Wattstr", 43) {};
        assertFalse(person.equals(differentHouseNr), "Persons with different houseNr should not be equal.");

    }



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


    @AfterEach
    void tearDown() {
        person = null;
    }
}