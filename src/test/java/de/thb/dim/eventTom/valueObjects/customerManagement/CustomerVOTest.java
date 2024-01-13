package de.thb.dim.eventTom.valueObjects.customerManagement;

import de.thb.dim.eventTom.valueObjects.customerManagement.exceptions.CustomerNoDateOfBirthException;
import de.thb.dim.eventTom.valueObjects.customerManagement.exceptions.CustomerTooYoungException;
import de.thb.dim.eventTom.valueObjects.eventManagement.EventVO;
import de.thb.dim.eventTom.valueObjects.eventManagement.PartyVO;
import de.thb.dim.eventTom.valueObjects.ticketSale.OrderVO;
import de.thb.dim.eventTom.valueObjects.ticketSale.SeatTicketVO;
import de.thb.dim.eventTom.valueObjects.ticketSale.StateOfOrderVO;
import de.thb.dim.eventTom.valueObjects.ticketSale.TicketVO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;


/**
 * @author Tobi Emma Nankpan,MN:20216374
 */
class CustomerVOTest {

    private CustomerVO customer;
    private EventVO event;
    private final LocalDateTime testStartTime = LocalDateTime.of(2023, 12, 18, 12, 0);
    private LocalDate dob, invalidDob;
    private Gender gender;
    private String lastName;
    private String firstName;
    private String street;
    private int houseNr;
    private TicketVO ticket;

    @BeforeEach
    void setUp() throws CustomerNoDateOfBirthException, CustomerTooYoungException {
        // default customer for each test
        dob = LocalDate.of(1990, 1, 1);
        invalidDob = LocalDate.now().plusDays(1); // Future date, should cause exception
        gender = Gender.M;
        lastName = "Weiss";
        firstName = "Melanie";
        street = "Potsdamerstr";
        houseNr = 123;

        event = new PartyVO(1, "Party Event", new String[]{"Speaker", "Microphone"}, "Event Hall", testStartTime, "Catering", "DJ");
        ticket = new SeatTicketVO(10, 99.99f, "A1", event);

        try {
            customer = new CustomerVO(lastName, firstName, street, houseNr, gender, dob);
        } catch (Exception e) {
            fail("Setup should not fail");
        }


        // Use spying to monitor the real object while being able to stub methods
        CustomerVO spiedCustomer = spy(customer);

        // Force the calculateAge() method to throw a CustomerNoDateOfBirthException
        doThrow(new CustomerNoDateOfBirthException("Internal error: No date of birth.")).when(spiedCustomer).calculateAge();

        // Replace the real customer object with the spied one
        customer = spiedCustomer;

    }

    @Test
    void testDobWhenNull() {
        assertThrows(CustomerNoDateOfBirthException.class, () -> new CustomerVO(lastName, firstName, street, houseNr, gender, null), "Constructor should throw an exception for null date of birth.");
    }


    @Test
    void testToStringHandlesNoDateOfBirthException() {
        // Call the toString method on the spiedCustomer and capture the result
        String result = customer.toString();  // Using 'customer', which is the spied version

        // Assert that the result contains the specific error message from the CustomerNoDateOfBirthException
        assertTrue(result.contains("Internal error: No date of birth."), "The toString method should handle CustomerNoDateOfBirthException.");
    }

    @Test
    void test_setDateOfBirth_NullValue_ShouldThrowException() {
        try {
            customer = new CustomerVO(lastName, firstName, street, houseNr, gender, dob);
            assertThrows(CustomerNoDateOfBirthException.class, () -> customer.setDateOfBirth(null), "Setting null date of birth should throw CustomerNoDateOfBirthException.");
        } catch (Exception e) {
            fail("Setting up customer with valid data should not throw an exception.");
        }
    }

    @Test
    void setDateOfBirth_FutureDate_ShouldThrowException() {
        assertThrows(CustomerNoDateOfBirthException.class, () -> new CustomerVO(lastName, firstName, street, houseNr, gender, invalidDob), "Constructor should throw CustomerTooYoungException for future date of birth.");
    }


    @Test
    void constructorWithValidData_ShouldCreateCustomer() {
        LocalDate dob = LocalDate.of(1990, 1, 1);
        try {
            CustomerVO customer = new CustomerVO("Wieber", "Tom", dob);
            assertNotNull(customer, "Customer should be created with valid data.");
            assertEquals("Wieber", customer.getLastName(), "Last name should be set correctly.");
            assertEquals("Tom", customer.getFirstName(), "First name should be set correctly.");
            assertEquals(Gender.U, customer.getGender(), "Gender should be set to unknown (U).");
            assertEquals(dob, customer.getDateOfBirth(), "Date of birth should be set correctly.");
        } catch (CustomerNoDateOfBirthException | CustomerTooYoungException e) {
            fail("No exception should be thrown with valid data.");
        }
    }

    @Test
    void constructorWithNullDob_ShouldThrowException() {
        assertThrows(CustomerNoDateOfBirthException.class, () -> new CustomerVO("Wieber", "Tom", null), "Constructor should throw CustomerNoDateOfBirthException when date of birth is null.");
    }


    @Test
    void toString_WhenCalled_ShouldIncludeImportantDetails() {
        try {
            customer = new CustomerVO(lastName, firstName, street, houseNr, gender, dob);
            String result = customer.toString();
            assertTrue(result.contains(lastName) && result.contains(firstName) && result.contains(street), "toString should include last name, first name, and street.");
        } catch (Exception e) {
            fail("Setting up customer with valid data should not throw an exception.");
        }
    }


  /*  @Test
    void calculateAge() {
        short expectedAge = (short) (LocalDate.now().getYear() - dob.getYear());
        try {
            assertEquals(expectedAge, customer.calculateAge(), "Age calculation should match expected value.");
        } catch (Exception e) {
            fail("Age calculation should not throw an exception.");
        }
    }*/

    @Test
    void getNextid() {
        // Assumes that the ID increments for each new customer
        int nextIdBefore = CustomerVO.getNextid();
        try {
            new CustomerVO("New", "Customer", "Dummy street", 456, Gender.F, LocalDate.of(2000, 1, 1));
        } catch (Exception e) {
            fail("Creating a new customer should not fail.");
        }
        assertEquals(nextIdBefore + 1, CustomerVO.getNextid(), "Next ID should increment after creating a new customer.");
    }


    @Test
    void getId() {
        assertEquals(customer.getId(), customer.getId(), "ID should be consistent between calls.");
    }

    @Test
    void getGender() {
        assertEquals(gender, customer.getGender(), "Gender should match the value set in setUp.");
    }


    @Test
    void getDateOfBirth() {
        assertEquals(dob, customer.getDateOfBirth(), "Date of birth should match the value set in setUp.");
    }


    @Test
    void getOrder() {
        assertNull(customer.getOrder(), "Initially, customer should not have an order.");
    }

    @Test
    void setGender() {
        Gender newGender = Gender.F;
        customer.setGender(newGender);
        assertEquals(newGender, customer.getGender(), "Gender should be updated to the new value.");
    }

    @Test
    void setDateOfBirth() {
        LocalDate newDob = LocalDate.of(2000, 1, 1);
        try {
            customer.setDateOfBirth(newDob);
            assertEquals(newDob, customer.getDateOfBirth(), "Date of birth should be updated to the new value.");
        } catch (Exception e) {
            fail("Setting date of birth should not throw an exception.");
        }
    }

    @Test
    void setDateOfBirthWithException() {
        LocalDate invalidDob = LocalDate.now().plusDays(1); // future date, should cause exception
        assertThrows(CustomerNoDateOfBirthException.class, () -> customer.setDateOfBirth(invalidDob), "Setting an invalid date of birth should throw an exception.");
    }

    @Test
    void test_toString_WithValidDateOfBirth_ShouldIncludeDobAndAge() throws CustomerNoDateOfBirthException, CustomerTooYoungException {
        LocalDate dob = LocalDate.of(1990, 1, 1);
        int expectedAge = Period.between(dob, LocalDate.now()).getYears();

        CustomerVO customer = new CustomerVO("Smith", "John", "Main Street", 100, Gender.M, dob);
        String customerString = customer.toString();

        assertTrue(customerString.contains("Date of Birth: " + dob.format(DateTimeFormatter.ofPattern("dd MM yyyy"))), "The toString method should include the date of birth");
        assertTrue(customerString.contains("Age: " + expectedAge), "The toString method should include the correct age");
    }


    @Test
    void setOrder() {
        OrderVO newOrder = new OrderVO(2024000013, StateOfOrderVO.CONFIRMED, testStartTime, customer); // Assuming a default constructor or appropriate parameters
        customer.setOrder(newOrder);
        assertEquals(newOrder, customer.getOrder(), "Order should be updated to the new value.");
    }

    @Test
    void hasOrder() {
        assertFalse(customer.hasOrder(), "Initially, customer should not have an order.");
        customer.setOrder(new OrderVO(202400007, StateOfOrderVO.STARTED, testStartTime, customer)); // Assuming a default constructor or appropriate parameters
        assertTrue(customer.hasOrder(), "After setting an order, hasOrder should return true.");
    }


    @Test
    void testHashCode() throws CustomerNoDateOfBirthException, CustomerTooYoungException {
        // Create first customer
        CustomerVO customer = new CustomerVO("Smith", "John", "Main Street", 123, Gender.M, LocalDate.of(1990, 1, 1));

        // Create second customer with same details
        CustomerVO sameCustomer = new CustomerVO("Smith", "John", "Main Street", 123, Gender.M, LocalDate.of(1990, 1, 1));        /*

         * Two CustomerVO objects comparing aren't being recognized as equal, likely due to the
         *  way their hashCode and equals methods are implemented.
         * The hashCode method includes the id field in its calculation. If nextId is being
         * incremented each time a CustomerVO is created, then each CustomerVO will have
         *  a different id, and thus a different hash code. This would cause the assertEquals
         *  assertion in your test to fail, even if all other fields are the same.
         */
        assertEquals(customer.hashCode(), sameCustomer.hashCode(), "Hashcode should be consistent for objects with equal properties excluding id.");
    }


    @Test
    void testEquals_DifferentClass_ShouldReturnFalse1() {
        Object notACustomer = new Object();
        assertFalse(customer.equals(notACustomer), "Comparing CustomerVO with an object of a different class should return false.");
        assertFalse(notACustomer instanceof CustomerVO);
    }

    @Test
    void testEquals_DifferentClass_ShouldReturnFalse2() throws CustomerNoDateOfBirthException, CustomerTooYoungException {
        CustomerVO customer = new CustomerVO("Doe", "John", "Main Street", 123, Gender.M, LocalDate.of(1990, 1, 1));
        Object otherObject = new Object();
        assertFalse(customer.equals(otherObject), "Comparing CustomerVO object with object of a different class should return false.");
    }


    @Test
    void customerUnderEighteen_ShouldThrowCustomerTooYoungException() {
        // Set up a date of birth for a 12-year-old customer
        LocalDate twelveYearsAgo = LocalDate.now().minusYears(12);

        // Attempt to create a customer with this date of birth
        assertThrows(CustomerTooYoungException.class, () -> {
            new CustomerVO("Last", "First", "Street", 123, Gender.U, twelveYearsAgo);
        }, "Should throw CustomerTooYoungException for customers under the age of 18");
    }

    @Test
    void testEquals_NullDateOfBirthInOne_ShouldReturnFalse1() {
        try {
            // Create a valid customer
            CustomerVO customerWithDob = new CustomerVO("Doe", "John", "Main Street", 123, Gender.M, LocalDate.of(1990, 1, 1));

            // Create another customer with the same details
            CustomerVO customerWithNullDob = new CustomerVO("Doe", "John", "Main Street", 123, Gender.M, LocalDate.of(1990, 1, 1));

            // Use reflection to set the dateOfBirth of the second customer to null
            Field dateOfBirthField = CustomerVO.class.getDeclaredField("dateOfBirth");
            dateOfBirthField.setAccessible(true);
            dateOfBirthField.set(customerWithNullDob, null);

            // Now the first customer has a dob and the second customer has null dob
            assertFalse(customerWithDob.equals(customerWithNullDob), "Comparing with a customer with null date of birth should return false.");

        } catch (NoSuchFieldException | IllegalAccessException | CustomerNoDateOfBirthException |
                 CustomerTooYoungException e) {
            e.printStackTrace();
            fail("Unexpected exception during test: " + e.getMessage());
        }
    }

    @Test
    void testEquals_NullDateOfBirthInOne_ShouldReturnFalse2() throws Exception {
        // Set up two customers with the same details
        CustomerVO customerWithNullDob = new CustomerVO("Martin", "John", "Main Street", 123, Gender.M, LocalDate.of(1990, 1, 1));
        CustomerVO customerWithNonNullDob = new CustomerVO("Martin", "John", "Main Street", 123, Gender.M, LocalDate.of(1990, 1, 1));

        // Use reflection to set dateOfBirth of the first customer to null
        Field dateOfBirthField = CustomerVO.class.getDeclaredField("dateOfBirth");
        dateOfBirthField.setAccessible(true);
        dateOfBirthField.set(customerWithNullDob, null);

        // Test the specific condition in equals method
        assertFalse(customerWithNullDob.equals(customerWithNonNullDob), "Customer with null dateOfBirth should not be equal to customer with non-null dateOfBirth.");
    }

    @Test
    void test_dobToString_WithNullDateOfBirth_ShouldThrowException() throws Exception {
        // Set up a customer
        CustomerVO customer = new CustomerVO("Smith", "John", "Main Street", 123, Gender.M, LocalDate.of(1990, 1, 1));

        // Use reflection to set dateOfBirth to null
        Field dateOfBirthField = CustomerVO.class.getDeclaredField("dateOfBirth");
        dateOfBirthField.setAccessible(true);
        dateOfBirthField.set(customer, null);

        // Attempt to call dobToString and expect an exception
        assertThrows(CustomerNoDateOfBirthException.class, customer::dobToString,
                "Calling dobToString with null dateOfBirth should throw CustomerNoDateOfBirthException.");
    }

    @Test
    void testEquals_DifferentDateOfBirth_ShouldReturnFalse() throws CustomerNoDateOfBirthException, CustomerTooYoungException {
        LocalDate differentDob = LocalDate.of(1991, 1, 1);
        CustomerVO customerWithDifferentDob = new CustomerVO("Doe", "John", "Main Street", 123, Gender.M, differentDob);
        assertFalse(customer.equals(customerWithDifferentDob), "Comparing with a customer with a different date of birth should return false.");
    }


    @Test
    void testToString() {
        String customerString = customer.toString();
        assertNotNull(customerString, "toString should not return null.");
        assertTrue(customerString.contains(firstName) && customerString.contains(lastName), "toString should contain the customer's name.");
    }








/*    @Test
    void toString_WithNullDateOfBirth_ShouldNotIncludeDobAndAge() {
        try {
            CustomerVO customer = new CustomerVO("Doe", "John", null);
        } catch (CustomerNoDateOfBirthException | CustomerTooYoungException e) {
            String errorMessage = e.getMessage();
            assertTrue(errorMessage.contains("No date of birth"), "Error message should mention missing date of birth.");
        }
    }*/


    @Test
    void toString_WithOrder_ShouldIncludeOrderDetails() {
        try {
            CustomerVO customer = new CustomerVO("Wieber", "Tom", LocalDate.of(1990, 1, 1));
            OrderVO order = new OrderVO(202400001, StateOfOrderVO.STARTED, testStartTime, customer);
            customer.setOrder(order);
            String customerString = customer.toString();
            assertTrue(customerString.contains("Order available"), "toString should indicate that an order is available.");
            assertTrue(customerString.contains(order.toString()), "toString should include the order details.");
        } catch (CustomerNoDateOfBirthException | CustomerTooYoungException e) {
            fail("No exception should be thrown with valid data.");
        }
    }

    @Test
    void toString_WithoutOrder_ShouldNotIncludeOrderDetails() {
        try {
            CustomerVO customer = new CustomerVO("Hamperl", "John", LocalDate.of(1990, 1, 1));
            String customerString = customer.toString();
            assertFalse(customerString.contains("Order available"), "toString should not indicate that an order is available if there is none.");
        } catch (CustomerNoDateOfBirthException | CustomerTooYoungException e) {
            fail("No exception should be thrown with valid data.");
        }
    }


    @Test
    void calculateAge_ShouldReturnCorrectAge() throws CustomerNoDateOfBirthException, CustomerTooYoungException {
        LocalDate dob = LocalDate.of(1990, 1, 1); // Adjust the year as needed
        CustomerVO customer = new CustomerVO("Doe", "John", "Main Street", 123, Gender.M, dob);
        short expectedAge = (short) Period.between(dob, LocalDate.now()).getYears();
        try {
            assertEquals(expectedAge, customer.calculateAge(), "The calculated age should match the expected age.");
        } catch (CustomerNoDateOfBirthException e) {
            fail("No exception should be thrown for a valid date of birth.");
        }
    }


    /**
     * checks that the toString method includes the formatted date of birth and the calculated
     * age when a valid date of birth is provided.
     */
    @Test
    void toString_WithValidDateOfBirth_ShouldIncludeDobAndAge() throws CustomerNoDateOfBirthException, CustomerTooYoungException {
        LocalDate dob = LocalDate.of(1990, 1, 1); // Adjust the year as needed
        CustomerVO customer = new CustomerVO("Wieber", "John", "Main Street", 123, Gender.M, dob);
        String customerString = customer.toString();

        assertTrue(customerString.contains(dob.format(DateTimeFormatter.ofPattern("dd MM yyyy"))), "toString should contain the formatted date of birth.");

        String agePart = customerString.split("Age: ")[1].split("\n")[0].trim();
        if (!agePart.matches("\\d+")) {
            fail("toString does not contain a numerical age. Extracted Age Part: " + agePart);
        }
    }

    @Test
    void testAgeRegex() {
        String sampleOutput = "ID: 37\tName: John Wieber\n\tStreet: Main Street 123\n\tmale\tDate of Birth: 01 01 1990\tAge: 34\nNo order available";
        Pattern pattern = Pattern.compile("Age: (\\d+)");
        Matcher matcher = pattern.matcher(sampleOutput);

        assertTrue(matcher.find(), "The regex should find the age in the sample output.");
        String age = matcher.group(1);
        assertNotNull(age, "Extracted age should not be null");
    }


    @Test
    void toString_WithNullDateOfBirth_ShouldNotIncludeDobAndAge() {
        try {
            // Ensure the constructor signature matches and pass a null dob.
            CustomerVO customer = new CustomerVO("Doe", "John", null, 0, Gender.U, null);
            fail("A CustomerNoDateOfBirthException should have been thrown");
        } catch (CustomerNoDateOfBirthException e) {
            String errorMessage = e.getMessage();
            assertTrue(errorMessage.contains("Date of Birth cannot be null"), "Error message should mention missing date of birth.");
        } catch (CustomerTooYoungException e) {
            fail("CustomerTooYoungException should not be thrown here.");
        }
    }


    @AfterEach
    void tearDown() {
        customer = null;
        event = null;
    }
}