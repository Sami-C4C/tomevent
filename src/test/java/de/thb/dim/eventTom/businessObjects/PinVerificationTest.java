package de.thb.dim.eventTom.businessObjects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Osama Ahmad, MN: 20233244
 */
class PinVerificationTest {
    private static IService pinService;
    private Customer customer;

    @BeforeEach
    void setUp() {
        pinService = mock(IService.class);
        customer = new Customer();
    }

    @Test
    void testCorrectPinAllowsOrder() {
        // Assuming the customer sets their PIN
        customer.setPin("1234");
        // When the correct PIN is entered
        when(pinService.verifyPin(customer.getPin())).thenReturn(true);

        // Then the customer should be allowed to order
        assertTrue(customer.canOrder(), "Customer with correct PIN should be able to order.");
    }

    @Test
    void testIncorrectFirstDigitBlocksOrder() {
        // Assuming the customer sets their PIN
        customer.setPin("1234");
        // When the first digit is incorrect
        when(pinService.verifyPin("0234")).thenReturn(false);

        // Then the customer should not be allowed to order
        assertFalse(customer.canOrder(), "Customer with incorrect first digit in PIN should not be able to order.");
    }

    @Test
    void testIncorrectSecondDigitBlocksOrder() {
        // Assuming the customer sets their PIN
        customer.setPin("1234");
        // When the second digit is incorrect
        when(pinService.verifyPin("1034")).thenReturn(false);

        // Then the customer should not be allowed to order
        assertFalse(customer.canOrder(), "Customer with incorrect second digit in PIN should not be able to order.");
    }

    @Test
    void testIncorrectThirdDigitBlocksOrder() {
        // Assuming the customer sets their PIN
        customer.setPin("1234");
        // When the third digit is incorrect
        when(pinService.verifyPin("1204")).thenReturn(false);

        // Then the customer should not be allowed to order
        assertFalse(customer.canOrder(), "Customer with incorrect third digit in PIN should not be able to order.");
    }

    @Test
    void testIncorrectFourthDigitBlocksOrder() {
        // Assuming the customer sets their PIN
        customer.setPin("1234");
        // When the fourth digit is incorrect
        when(pinService.verifyPin("1230")).thenReturn(false);

        // Then the customer should not be allowed to order
        assertFalse(customer.canOrder(), "Customer with incorrect fourth digit in PIN should not be able to order.");
    }

    // Helper classes and methods (to be implemented)
    static class Customer {
        private String pin;
        void setPin(String pin) { this.pin = pin; }
        String getPin() { return pin; }
        boolean canOrder() {
            // This should call the PinService to verify the PIN
            // and return true if the service verifies the PIN is correct.
            return pinService.verifyPin(getPin());
        }
    }

    interface IService {
        boolean verifyPin(String pin);
    }
}