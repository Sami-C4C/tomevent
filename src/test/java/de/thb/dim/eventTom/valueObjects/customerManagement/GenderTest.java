package de.thb.dim.eventTom.valueObjects.customerManagement;

import de.thb.dim.eventTom.valueObjects.ticketSale.StateOfOrderVO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

class GenderTest {

    @ParameterizedTest
    @EnumSource(value = Gender.class)
    void testGender(Gender gender){
        assertNotNull(gender);
    }


}