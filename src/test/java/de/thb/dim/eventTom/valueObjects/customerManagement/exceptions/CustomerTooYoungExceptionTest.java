package de.thb.dim.eventTom.valueObjects.customerManagement.exceptions;

import de.thb.dim.eventTom.valueObjects.customerManagement.CustomerVO;
import de.thb.dim.eventTom.valueObjects.customerManagement.Gender;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Tobi Emma Nankpan, MN: 20216374
 */
class CustomerTooYoungExceptionTest {
    @Test
    void shouldThrowExceptionForUnderageCustomer() {
        LocalDate tooYoungDob = LocalDate.now().minusYears(16); // A 16-year-old customer

        assertThrows(CustomerTooYoungException.class, () -> {
            new CustomerVO("Young", "Kid", "Youth Street", 42, Gender.D, tooYoungDob);
        }, "Should throw CustomerTooYoungException for underage customer");
    }

    @Test
    void shouldNotThrowExceptionForOfAgeCustomer() {
        LocalDate ofAgeDob = LocalDate.now().minusYears(20); // A 20-year-old customer

        assertDoesNotThrow(() -> {
            new CustomerVO("Adult", "Person", "Mature Street", 100, Gender.U, ofAgeDob);
        }, "Should not throw CustomerTooYoungException for of-age customer");
    }
}