package de.thb.dim.eventTom.valueObjects.customerManagement;

import de.thb.dim.eventTom.valueObjects.customerManagement.exceptions.CustomerNoDateOfBirthException;
import de.thb.dim.eventTom.valueObjects.customerManagement.exceptions.CustomerTooYoungException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Period;

import static org.junit.jupiter.api.Assertions.*;

class TestCustomerManagement {

    private CustomerVO customer;

    @BeforeEach
    void setup() throws Exception {
        customer = new CustomerVO("Ahmad", "Osama", "Brunnenstr", 55, Gender.M, LocalDate.of(1990, 1, 2));
    }

    @Test
    void createValidCustomer() {
        assertEquals("Osama", customer.getFirstName());
        assertEquals("Ahmad", customer.getLastName());
        assertEquals(Gender.M, customer.getGender());
        assertEquals(55, customer.getHouseNr());
    }

    @Test
    void createCustomerWithNullName() {
        assertThrows(NullPointerException.class, () -> {
            new CustomerVO(null, "Osama", "Brunnenstr", 55, Gender.M, LocalDate.of(1990, 1, 2));
        });
    }

    @Test
    void createTooYoungCustomer() {
        assertThrows(CustomerTooYoungException.class, () -> {
            new CustomerVO("Ahmad", "Osama", "Brunnenstr", 55, Gender.M, LocalDate.now());
        });
    }



    @Test
    void calculateAgeCorrectly() throws CustomerNoDateOfBirthException, CustomerTooYoungException {
        LocalDate dob = LocalDate.of(1990, 1, 2);
        customer.setDateOfBirth(dob);
        assertEquals(33, customer.calculateAge()); // Alter zum Testzeitpunkt
    }

    @Test
    void setInvalidDateOfBirth() {
        assertThrows(CustomerTooYoungException.class, () -> {
            customer.setDateOfBirth(LocalDate.now().minusYears(10));
        });
    }


    @Test
    void createCustomerWhoIsTooYoung() {
        LocalDate tooYoungDOB = LocalDate.now().minusYears(16);
        assertThrows(CustomerTooYoungException.class, () -> {
            new CustomerVO("Doe", "Jane", "Main Street", 123, Gender.F, tooYoungDOB);
        });
    }

    @Test
    void testCreateCustomerWithoutDateOfBirth() {
        assertThrows(CustomerNoDateOfBirthException.class, () -> {
            new CustomerVO("Doe", "Jane", "Main Street", 123, Gender.F,null);
        });
    }

    @Test
    void createCustomerWithFutureDateOfBirth() {
        LocalDate futureDOB = LocalDate.now().plusYears(10);
        assertThrows(CustomerTooYoungException.class, () -> {
            new CustomerVO("Oldenberg", "Jahn", "Main Street", 57, Gender.M, futureDOB);
        });
    }

    @Test
    void ageShouldBeCorrectlyCalculated() throws CustomerNoDateOfBirthException, CustomerTooYoungException {
        LocalDate dob = LocalDate.of(2000, 1, 1);
        customer.setDateOfBirth(dob);
        int expectedAge = Period.between(dob, LocalDate.now()).getYears();
        assertEquals(expectedAge, customer.calculateAge());
    }




    // Weitere Tests für andere Methoden und Randfälle
}