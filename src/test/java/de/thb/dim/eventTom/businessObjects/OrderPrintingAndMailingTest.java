package de.thb.dim.eventTom.businessObjects;

import de.thb.dim.eventTom.businessObjects.exceptions.NoCustomerException;
import de.thb.dim.eventTom.valueObjects.customerManagement.CustomerVO;
import de.thb.dim.eventTom.valueObjects.customerManagement.Gender;
import de.thb.dim.eventTom.valueObjects.customerManagement.exceptions.CustomerNoDateOfBirthException;
import de.thb.dim.eventTom.valueObjects.customerManagement.exceptions.CustomerTooYoungException;
import de.thb.dim.eventTom.valueObjects.ticketSale.OrderVO;
import de.thb.dim.eventTom.valueObjects.ticketSale.StateOfOrderVO;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertNotNull;

/**
 * @author Osama Ahmad, MN:20233244.
 */
class OrderPrintingAndMailingTest {



    private class MailingService implements IService {

        @Override
        public String startService(OrderVO order) throws NoCustomerException, IllegalStateException, NullPointerException {

            String emailMessage = "Order successfully sent to the email of customer: Osama.Ahmad@mail.com";
            sendEmailToCustomer(order, emailMessage);
            return emailMessage;
        }

        private void sendEmailToCustomer(OrderVO order, String emailMessage) {
            // Simulating email sending process
            System.out.println("Sending email...");
            // In a real application, you would have code here to actually send an email.
        }
    }





    private class PrintingService implements IService {

        @Override
        public String startService(OrderVO order) throws NoCustomerException, IllegalStateException, NullPointerException {

            return formatCustomerInformation(order);
        }

        private String formatCustomerInformation(OrderVO order) {
            CustomerVO customer = order.getCustomer();

            StringBuilder sb = new StringBuilder();
            sb.append("Printing Order Information:\n");
            sb.append("Firstname: ").append(customer.getFirstName()).append(".\n");
            sb.append("Lastname: ").append(customer.getLastName()).append(".\n");
            sb.append("Gender: ").append(customer.getGender()).append(".\n");

            // Formatting the order date
            LocalDateTime orderDate = order.getTimestampStartedOrder();
            sb.append("Bought on: ").append(orderDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))).append(".\n");

            return sb.toString();
        }
    }


    @Nested
    public class PrintingServiceTest {

        @Test
        void testPrintOrderInformation() throws NoCustomerException, CustomerNoDateOfBirthException, CustomerTooYoungException {
            CustomerVO customer = new CustomerVO("Ahmad", "Osama", "Brunnenstr", 67, Gender.M, LocalDate.of(1990, 1, 1));
            OrderVO order = new OrderVO(202400001, StateOfOrderVO.CONFIRMED, LocalDateTime.now(), customer);

            PrintingService printingService = new PrintingService();
            String customerInfo = printingService.startService(order);
            assertNotNull(customerInfo, "Customer information should not be null");
            System.out.println(customerInfo);
        }
    }



    @Nested
    class MailingServiceTest {

        @Test
        void testMailingService() throws NoCustomerException, CustomerNoDateOfBirthException, CustomerTooYoungException {
            CustomerVO customer = new CustomerVO("Ahmad", "Osama", "Brunnenstr", 67, Gender.M, LocalDate.of(1990, 1, 1));
            OrderVO order = new OrderVO(202400001, StateOfOrderVO.CONFIRMED, LocalDateTime.now(), customer);

            MailingService mailingService = new MailingService();
            String result = mailingService.startService(order);
            assertNotNull(result, "Result should not be null");
            System.out.println(result);
        }
    }

}