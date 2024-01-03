package de.thb.dim.eventTom.businessObjects.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Osama Ahmad, MN: 20233244
 */
class NoCustomerExceptionTest {

    @Test
    void defaultConstructor_MessageIsCorrect() {
        NoCustomerException exception = new NoCustomerException();
        assertEquals("No customer was indicated.", exception.getMessage());
    }

    @Test
    void messageConstructor_MessageIsCorrect() {
        String customMessage = "No Customer found";
        NoCustomerException exception = new NoCustomerException(customMessage);
        assertEquals(customMessage, exception.getMessage());
    }

}