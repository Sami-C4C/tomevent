package de.thb.dim.eventTom.valueObjects.customerManagement;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Tobi Emma Nankpan
 */
class GenderTest {



    @Test
    void toNumber() {
        // Testing the toNumber method for each gender type
        assertEquals(1, Gender.M.toNumber(), "The number for male (M) should be 1");
        assertEquals(2, Gender.F.toNumber(), "The number for female (F) should be 2");
        assertEquals(3, Gender.D.toNumber(), "The number for diverse (D) should be 3");
        assertEquals(4, Gender.U.toNumber(), "The number for unknown (U) should be 4");
    }

    @Test
    void testToString() {
        // Testing the toString method for each gender type
        assertEquals("male", Gender.M.toString(), "The string for male (M) should be 'male'");
        assertEquals("female", Gender.F.toString(), "The string for female (F) should be 'female'");
        assertEquals("diverse", Gender.D.toString(), "The string for diverse (D) should be 'diverse'");
        assertEquals("unknown", Gender.U.toString(), "The string for unknown (U) should be 'unknown'");
    }

    @Test
    void values() {
        // Testing the values method which should return all constants in an array
        Gender[] genders = Gender.values();
        assertArrayEquals(new Gender[]{Gender.M, Gender.F, Gender.D, Gender.U}, genders, "Should return an array of all gender constants.");
    }

    @Test
    void valueOf() {
        // Testing the valueOf method with valid input
        assertEquals(Gender.M, Gender.valueOf("M"), "valueOf for 'M' should return Gender.M");
        assertEquals(Gender.F, Gender.valueOf("F"), "valueOf for 'F' should return Gender.F");
        assertEquals(Gender.D, Gender.valueOf("D"), "valueOf for 'D' should return Gender.D");
        assertEquals(Gender.U, Gender.valueOf("U"), "valueOf for 'U' should return Gender.U");

        // Testing the valueOf method with invalid input
        Exception exception = assertThrows(IllegalArgumentException.class, () -> Gender.valueOf("Invalid"), "Invalid gender type should throw IllegalArgumentException");
        assertTrue(exception.getMessage().contains("No enum constant"), "The exception message should indicate the invalid constant");
    }
}