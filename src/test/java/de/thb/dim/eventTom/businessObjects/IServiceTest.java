package de.thb.dim.eventTom.businessObjects;

import de.thb.dim.eventTom.businessObjects.exceptions.NoCustomerException;
import de.thb.dim.eventTom.valueObjects.customerManagement.CustomerVO;
import de.thb.dim.eventTom.valueObjects.customerManagement.Gender;
import de.thb.dim.eventTom.valueObjects.customerManagement.exceptions.CustomerNoDateOfBirthException;
import de.thb.dim.eventTom.valueObjects.customerManagement.exceptions.CustomerTooYoungException;
import de.thb.dim.eventTom.valueObjects.ticketSale.OrderVO;
import de.thb.dim.eventTom.valueObjects.ticketSale.StateOfOrderVO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


/**
 * @author Osama Ahmad, MN: 20233244
 */
@ExtendWith(MockitoExtension.class)
public class IServiceTest {


    public class PrintingService implements IService {
        @Override
        public String startService(OrderVO order) throws NoCustomerException {
            // Logic for printing service
            return "Printing Service Started";
        }
    }

    public class MailingService implements IService {
        @Override
        public String startService(OrderVO order) throws NoCustomerException {
            // Logic for mailing service
            return "Mailing Service Started";
        }
    }


    private IService service;
    private OrderVO order;
    private CustomerVO customer;
    private final LocalDateTime testStartTime = LocalDateTime.of(2023, 12, 18, 12, 0);
    private IService printingService;
    private IService mailingService;


    @BeforeEach
    public void setUp() throws CustomerNoDateOfBirthException, CustomerTooYoungException {
        service = new IService_Implementation();
        customer = new CustomerVO("Ahmad", "Osama", "Berlinerstr", 23, Gender.M, LocalDate.of(1990, 1, 2));
        order = new OrderVO(1, StateOfOrderVO.STARTED, testStartTime, customer);


        // Create mocks for both services
        printingService = mock(PrintingService.class);
        mailingService = mock(MailingService.class);
    }

    @Test
    public void test_whenStartServiceCalled_withValidOrder_shouldReturnConfirmation() throws NoCustomerException {
        String result = service.startService(order);
        assertEquals("Service Started", result);
    }

    @Test
    public void test_whenStartServiceCalled_withFinishedOrder_shouldThrowIllegalStateException() {
        order.setState(StateOfOrderVO.FINISHED); // Set the state to FINISHED
        assertThrows(IllegalStateException.class, () -> service.startService(order));
    }

    @Test
    public void test_whenStartServiceCalled_withNullCustomer_shouldThrowNoCustomerException() {
        order.setCustomer(null);
        assertThrows(NoCustomerException.class, () -> service.startService(order));
    }


    @Test
    public void test_whenStartServiceCalled_withNullOrder_shouldThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> service.startService(null));
    }


    @Test
    public void testPrintingService() throws Exception {
        // Define mock behavior for printing service
        when(printingService.startService(Mockito.any())).thenReturn("Printing Service Mocked");

        // Call the method and assert the result
        String result = printingService.startService(null);
        assertEquals("Printing Service Mocked", result);
    }

    @Test
    public void testMailingService() throws Exception {
        // Define mock behavior for mailing service
        when(mailingService.startService(Mockito.any())).thenReturn("Mailing Service Mocked");

        // Call the method and assert the result
        String result = mailingService.startService(null);
        assertEquals("Mailing Service Mocked", result);
    }


    private class IService_Implementation implements IService {

        @Override
        public String startService(OrderVO order) throws NoCustomerException, IllegalStateException, NullPointerException {
            if (order == null) {
                throw new NullPointerException("Order cannot be null.");
            }

            if (order.getCustomer() == null) {
                throw new NoCustomerException("Order must have a customer.");
            }
            if (order.getState() == StateOfOrderVO.FINISHED) {
                throw new IllegalStateException("Cannot start service on a finished order.");
            }


            return "Service Started";
        }
    }


    @AfterEach
    public void tearDown() {
        // Clean up after tests
        service = null;
        order = null;
        customer = null;
    }
}