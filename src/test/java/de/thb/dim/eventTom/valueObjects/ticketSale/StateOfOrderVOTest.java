package de.thb.dim.eventTom.valueObjects.ticketSale;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StateOfOrderVOTest {

    @Test
    void values_containsAllEnumValues() {
        StateOfOrderVO[] states = StateOfOrderVO.values();
        assertTrue(states.length == 5, "There should be five states defined.");
        assertArrayEquals(new StateOfOrderVO[]{StateOfOrderVO.STARTED, StateOfOrderVO.CONFIRMED, StateOfOrderVO.PRINTED, StateOfOrderVO.MAILED, StateOfOrderVO.FINISHED}, states, "The array should contain all the enum values in the order they are declared.");
    }

    @Test
    void valueOf_returnsCorrectEnumValue() {
        assertEquals(StateOfOrderVO.STARTED, StateOfOrderVO.valueOf("STARTED"), "valueOf should return the correct enum value for 'STARTED'.");
        assertEquals(StateOfOrderVO.CONFIRMED, StateOfOrderVO.valueOf("CONFIRMED"), "valueOf should return the correct enum value for 'CONFIRMED'.");
        assertEquals(StateOfOrderVO.PRINTED, StateOfOrderVO.valueOf("PRINTED"), "valueOf should return the correct enum value for 'PRINTED'.");
        assertEquals(StateOfOrderVO.MAILED, StateOfOrderVO.valueOf("MAILED"), "valueOf should return the correct enum value for 'MAILED'.");
        assertEquals(StateOfOrderVO.FINISHED, StateOfOrderVO.valueOf("FINISHED"), "valueOf should return the correct enum value for 'FINISHED'.");
    }

    @Test
    void valueOf_withInvalidName_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> StateOfOrderVO.valueOf("INVALID"), "valueOf should throw an exception for an invalid name.");
    }
}