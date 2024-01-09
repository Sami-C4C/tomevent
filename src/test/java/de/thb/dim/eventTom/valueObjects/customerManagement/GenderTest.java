package de.thb.dim.eventTom.valueObjects.customerManagement;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Tobi Emma Nankpan, MN: 20216374
 */
class GenderTest {

    @ParameterizedTest
    @EnumSource(value = Gender.class)
    void testGender(Gender gender){
        assertNotNull(gender);
    }

    @Test
    void toNumber() {
        // Testing the toNumber method for each gender type
        assertEquals(1, Gender.M.toNumber(), "The number for male (M) should be 1");
        assertEquals(2, Gender.F.toNumber(), "The number for female (F) should be 2");
        assertEquals(3, Gender.D.toNumber(), "The number for diverse (D) should be 3");
        assertEquals(4, Gender.U.toNumber(), "The number for unknown (U) should be 4");
    }


}