package de.thb.dim.eventTom.businessObjects.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Osama ahmad, MN:20233244
 */
class NoOrderExceptionTest {

    @Test
    void defaultConstructor_MessageIsCorrect() {
        NoOrderException exception = new NoOrderException();
        assertEquals("Order could not be found.", exception.getMessage());
    }

    @Test
    void messageConstructor_MessageIsCorrect() {
        String customMessage = "Custom message for NoOrderException";
        NoOrderException exception = new NoOrderException(customMessage);
        assertEquals(customMessage, exception.getMessage());
    }

}