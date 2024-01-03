package de.thb.dim.eventTom.valueObjects.customerManagement;

import de.thb.dim.eventTom.valueObjects.customerManagement.exceptions.CustomerNoDateOfBirthException;
import de.thb.dim.eventTom.valueObjects.customerManagement.exceptions.CustomerTooYoungException;
import de.thb.dim.eventTom.valueObjects.ticketSale.OrderVO;
import de.thb.dim.eventTom.valueObjects.ticketSale.StateOfOrderVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.*;
/**
 * The methods of the class CustomerVO are tested.
 *
 * Special assert statements are used for testing <br>
 *
 * @author Tobi Emma Nankpan
 */

class CustomerVOTest {


    private CustomerVO  customer1, customer2,customerX, customerY,customerZ;

    @BeforeEach
    void setup() throws Exception {
        customer1 = new CustomerVO("Schneider", "Tom", "Brunnenstr", 55, Gender.M, LocalDate.of(1990, 1, 2));
        customer2 = new CustomerVO("Hayap","Corinne", LocalDate.of(1998,10,30));
        customerX = new CustomerVO("Nachname", "Vorname", "Straße", 123, Gender.M, LocalDate.of(1990, 1, 1));
        customerY = new CustomerVO("Nachname", "Vorname", "Straße", 123, Gender.M, LocalDate.of(1990, 1, 1));
        customerZ = new CustomerVO("Nachname", "Vorname", "Straße", 124, Gender.F, LocalDate.of(1995, 6, 15));

    }

    @Test
    void calculateAge() throws CustomerTooYoungException, CustomerNoDateOfBirthException {

        LocalDate dob = LocalDate.of(1990, 1, 2);
        customer1.setDateOfBirth(dob);
        assertEquals(34, customer1.calculateAge());
        assertThrows(CustomerNoDateOfBirthException.class, () -> {
            CustomerVO customerS = new CustomerVO("Doe", "Jane", "Main Street", 123, Gender.F, null);
            assertEquals(-1, customerS.calculateAge(), "Internal error: No date of birth.");
        });

    }



    @Test
    void testCalculateAgeWithNullDateOfBirth() {



    }










    @Test
    void testCreateCustomerWhoIsTooYoung() {
        LocalDate tooYoungDOB = LocalDate.now().minusYears(16);
        assertThrows(CustomerTooYoungException.class, () -> {
            new CustomerVO("Doe", "Jane", "Main Street", 123, Gender.F, tooYoungDOB);
        });
    }

    @Test
    void testCreateCustomerWithoutDateOfBirth() {
        try {
            assertThrows(CustomerNoDateOfBirthException.class, () -> {
                new CustomerVO("Doe", "Jane", "Main Street", 123, Gender.F,null);
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testSetInvalidDateOfBirth() {
        assertThrows(CustomerTooYoungException.class, () -> {
            customer1.setDateOfBirth(LocalDate.now().minusYears(10));
        });
    }

    @Test
    void getNextid() {

    }

    @Test
    void getOrder() throws CustomerNoDateOfBirthException, CustomerTooYoungException {
        CustomerVO customer = new CustomerVO("Nachname", "Vorname", "Straße", 123, Gender.M, LocalDate.of(1990, 1, 1));
        // Initially, the customer should not have an order
        assertNull(customer.getOrder());

        // Create an OrderVO and set it for the customer
        OrderVO order = new OrderVO(1, StateOfOrderVO.CONFIRMED, LocalDateTime.of(2023,05,14,10,30), customer);
        customer.setOrder(order);

        // Now, the customer should have an order
        assertNotNull(customer.getOrder());
        assertEquals(order, customer.getOrder());
    }


    @Test
    void testHashCode() {

    }

    @Test
    void testEquals(){
        try {
            assertThrows(CustomerNoDateOfBirthException.class, () -> {
                CustomerVO customer = new CustomerVO("Nachname", "Vorname", "Straße", 123, Gender.M, LocalDate.of(1990, 1, 1));
                //Identical customer objects should be identical
                assertTrue(customer.equals(customerX));

                // Different customer objects should be unequal
                assertFalse(customerX.equals(customerZ));

                CustomerVO customerA = new CustomerVO(null, "Vorname", "Straße", 123, Gender.M, LocalDate.of(1990, 1, 1));
                assertNotNull(customerX.getLastName());
                assertFalse(customerA.equals(customerX));

                CustomerVO customerB = new CustomerVO("Nachname", "Vorname", null, 123, Gender.M, LocalDate.of(1990, 1, 1));
                assertNull(customerB.getStreet());
                assertNotNull(customer1.getStreet());
                assertEquals(false,customerB.equals(customer1));

                CustomerVO customerC = new CustomerVO("Nachname", null, "Straße", 123, Gender.M, LocalDate.of(1990, 1, 1));
                assertNotNull(customer1.getFirstName());
                assertEquals(false,customer.equals(customer1));

                customer.setDateOfBirth(null);
                assertNull(customer.getDateOfBirth());
                assertNotEquals(customer, customerZ);

            });

        } catch (Exception e) {

            throw new RuntimeException(e);
        }



    }



    @Test
    void testToString() {
        try {
            assertThrows(CustomerNoDateOfBirthException.class, () -> {
                //Create a CustomerVO object
                CustomerVO customer = new CustomerVO("Doe", "John", "123 Main St", 1, Gender.M, LocalDate.of(1990, 1, 1));

                // Creates an OrderVO object linked to the CustomerVO
                OrderVO order = new OrderVO(1, StateOfOrderVO.CONFIRMED, LocalDateTime.of(2023,05,14,10,30), customer);
                customer.setOrder(order);

                // Define expectations for the expected results chain
                String expectedString = "ID: " + customer.getId() + "\tName: "+ customer.getFirstName()+ " "+ customer.getLastName()+ "\n\tStreet: " + customer.getStreet() + " "+ customer.getHouseNr() + "\n\t"+ customer.getGender().toString() + "\tDate of Birth: " +
                        customer.getDateOfBirth().format(DateTimeFormatter.ofPattern("dd MM yyyy")) + "\tAge: " + customer.calculateAge() +
                        "\nOrder available: \n" + order.toString();

                // Calling the toString() method
                String result = customer.toString();

                // Checking using assertEquals
                assertEquals(expectedString, result);
                customer.setDateOfBirth(null);

            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
}