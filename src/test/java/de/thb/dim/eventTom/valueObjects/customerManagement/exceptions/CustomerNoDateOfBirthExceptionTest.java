package de.thb.dim.eventTom.valueObjects.customerManagement.exceptions;

import de.thb.dim.eventTom.valueObjects.customerManagement.CustomerVO;
import de.thb.dim.eventTom.valueObjects.customerManagement.Gender;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Tobi Emma Nankpan, MN:20216374
 */
class CustomerNoDateOfBirthExceptionTest {



    @Test
    void testDefaultConstructor() {
        Exception exception = assertThrows(CustomerNoDateOfBirthException.class, () -> {
            throw new CustomerNoDateOfBirthException();
        });
        assertEquals("There is no date of birth available.", exception.getMessage(), "Default message should match.");
    }

    @Test
    void testConstructorWithCustomMessage() {
        String customMessage = "Custom Date of Birth error";
        Exception exception = assertThrows(CustomerNoDateOfBirthException.class, () -> {
            throw new CustomerNoDateOfBirthException(customMessage);
        });
        assertEquals(customMessage, exception.getMessage(), "Custom message should match.");
    }







    @Test
    void shouldThrowExceptionWhenDateOfBirthIsNull() {
        assertThrows(CustomerNoDateOfBirthException.class, () -> {
            // Assuming that CustomerVO throws CustomerNoDateOfBirthException when dob is null
            new CustomerVO("Doe", "John", null, 0, Gender.M, null);
        }, "Should throw CustomerNoDateOfBirthException when date of birth is null");
    }

    @Test
    void shouldThrowExceptionWhenCalculatingAgeWithoutDob() throws CustomerNoDateOfBirthException, CustomerTooYoungException, NoSuchFieldException, IllegalAccessException {
        // Create a new customer with a valid date of birth
        CustomerVO customer = new CustomerVO("Doe", "John", "Main Street", 123, Gender.M, LocalDate.of(1990, 1, 1));

        // Use reflection to access the private dateOfBirth field and set it to null
        Field dobField = CustomerVO.class.getDeclaredField("dateOfBirth");
        dobField.setAccessible(true);
        dobField.set(customer, null);

        // Now the dateOfBirth is null, so calculating the age should throw an exception
        assertThrows(CustomerNoDateOfBirthException.class, customer::calculateAge,
                "Should throw CustomerNoDateOfBirthException when calculating age without date of birth");
    }

}