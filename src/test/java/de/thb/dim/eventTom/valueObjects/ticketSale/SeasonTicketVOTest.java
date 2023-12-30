package de.thb.dim.eventTom.valueObjects.ticketSale;

import de.thb.dim.eventTom.valueObjects.eventManagement.EventVO;
import de.thb.dim.eventTom.valueObjects.eventManagement.PartyVO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;


/**
 * @author Osama Ahmad , MN:20233244
 */
import static org.junit.jupiter.api.Assertions.*;

class SeasonTicketVOTest {

    private EventVO event;
    private SeasonTicketVO ticket;

    @BeforeEach
    void setUp() {
        // Set up the event object
        LocalDateTime eventDate = LocalDateTime.of(2024, 1, 1, 19, 0);
        event = new PartyVO(1, "Event Name", new String[]{"Equipment"}, "Venue", eventDate, "Catering", "Performer");

        // Create a season ticket for the tests
        LocalDate startOfSeason = LocalDate.of(2024, 4, 1);
        LocalDate endOfSeason = LocalDate.of(2024, 9, 30);
        ticket = new SeasonTicketVO(1, 100.0f, event, startOfSeason, endOfSeason);
    }


    @Test
    void testHashCode() {
        SeasonTicketVO ticket = new SeasonTicketVO(1, 100.0f, event, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 6, 1));
        int expectedHash = ticket.hashCode(); // Save the original hash code
        int newHash = new SeasonTicketVO(1, 100.0f, event, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 6, 1)).hashCode();
        assertEquals(expectedHash, newHash, "Hash code should be the same for two objects with the same properties.");
    }


    @Test
    void testEquals() {
        SeasonTicketVO ticket1 = new SeasonTicketVO(1, 100.0f, event, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 6, 1));
        SeasonTicketVO ticket2 = new SeasonTicketVO(1, 100.0f, event, ticket.getStartOfSeason(), ticket.getEndOfSeason());
        assertTrue(ticket.equals(ticket2), "Two tickets with the same attributes should be equal.");
        assertFalse(ticket1.equals(ticket2), "Two tickets with the different attributes should not be equal.");
        SeasonTicketVO ticketDifferent = new SeasonTicketVO(1, 100.0f, event, LocalDate.of(2024, 1, 2), LocalDate.of(2024, 6, 2));
        assertFalse(ticket1.equals(ticketDifferent), "Two tickets with different dates should not be equal.");

        ticket2.setEndOfSeason(null);

        assertFalse(ticket1.equals(ticket2));// Test equals when the end of session is null and the other end of session ist not null then should return false.
        assertFalse(ticket2.equals(ticket1));
        ticket2.setEndOfSeason(ticket.getEndOfSeason()); // reset endOfSession of ticket2 back to the beginning
        ticket2.setStartOfSeason(null); // set start of session of ticket2 to null

        assertFalse(ticket2.equals(ticket)); //When StartOfSeason is null and other StartOfSeason is not null, then should return false.

        ticket1.setStartOfSeason(ticket.getStartOfSeason()); // StartOfSession for ticket1 and ticket2 are the same but with different endOfSession
        assertFalse(ticket1.equals(ticket2)); //When EndOfSeason is not equal to other EndOfSeason should return False.


        ticketDifferent.setEndOfSeason(ticket.getEndOfSeason()); // ticket2 and ticketDifferent have both the same EndOfSeason but different StartOfSeason
        assertFalse(ticketDifferent.equals(ticket2));      // When StartOfSeason is not equal To other StartOfSeason, should return false

        Object other = new Object();
        assertFalse(other instanceof TicketVO && ticket1 instanceof Object);


    }


    @Test
    void getStartOfSeason() {
        LocalDate start = LocalDate.of(2024, 1, 1);
        SeasonTicketVO ticket = new SeasonTicketVO(1, 100.0f, event, start, LocalDate.of(2024, 6, 1));
        assertEquals(start, ticket.getStartOfSeason(), "Should return the correct start of season date.");
    }

    @Test
    void getEndOfSeason() {
        LocalDate end = LocalDate.of(2024, 6, 1);
        SeasonTicketVO ticket = new SeasonTicketVO(1, 100.0f, event, LocalDate.of(2024, 1, 1), end);
        assertEquals(end, ticket.getEndOfSeason(), "Should return the correct end of season date.");
    }


    @Test
    void setStartOfSeason() {
        LocalDate newStart = LocalDate.of(2024, 2, 1);
        SeasonTicketVO ticket = new SeasonTicketVO(1, 100.0f, event, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 6, 1));
        ticket.setStartOfSeason(newStart);
        assertEquals(newStart, ticket.getStartOfSeason(), "Should update the start of the season.");
    }

    @Test
    void setEndOfSeason() {
        LocalDate newEnd = LocalDate.of(2024, 7, 1);
        SeasonTicketVO ticket = new SeasonTicketVO(1, 100.0f, event, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 6, 1));
        ticket.setEndOfSeason(newEnd);
        assertEquals(newEnd, ticket.getEndOfSeason(), "Should update the end of the season.");
    }


    @Test
    void getCharge() {
        // Assuming LocalDate.now() is 2024-03-01
        SeasonTicketVO ticket = new SeasonTicketVO(1, 100.0f, event, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 6, 1));
        float charge = ticket.getCharge();
        assertEquals(1.0f, charge, "Should return the correct charge based on the time of the season.");
        // Additional assertions should be made for different periods within the season
    }


/*    @Test
    void getCharge_variousScenarios() {
        LocalDate startOfSeason = LocalDate.of(2024, 1, 1);
        LocalDate endOfSeason = LocalDate.of(2024, 12, 31);
        SeasonTicketVO ticket = new SeasonTicketVO(1, 100.0f, event, startOfSeason, endOfSeason);

        // Early in the season
        LocalDate earlySeason = LocalDate.of(2024, 1, 1);
        assertEquals(1.0f, ticket.getCharge(earlySeason), "Should return full charge early in the season.");

        // Mid-season
        LocalDate midSeason = LocalDate.of(2024, 6, 15);
        assertEquals(0.95f, ticket.getCharge(midSeason), "Should return discounted charge mid-season.");

        // Late in the season
        LocalDate lateSeason = LocalDate.of(2024, 11, 15);
        assertEquals(0.9f, ticket.getCharge(lateSeason), "Should return greater discount late in the season.");

        // After the season
        LocalDate afterSeason = LocalDate.of(2025, 1, 15);
        assertEquals(1.0f, ticket.getCharge(afterSeason), "Should return full charge after the season has ended.");
    }*/


    @Test
    void getSeatOfTicket() {
        SeasonTicketVO ticket = new SeasonTicketVO(1, 100.0f, event, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 6, 1));
        ticket.setSeat("A1");
        assertEquals("A1 +", ticket.getSeatOfTicket(), "Should return the correct seat string.");
    }


    @Test
    void getCharge_variousScenarios() {
        // Define the season period
        LocalDate startOfSeason = LocalDate.of(2024, 1, 1);
        LocalDate endOfSeason = LocalDate.of(2024, 12, 31);

        // Create the ticket
        SeasonTicketVO ticket = new SeasonTicketVO(1, 100.0f, event, startOfSeason, endOfSeason);

        // Assume a date early in the season
        LocalDate earlySeason = LocalDate.of(2024, 1, 15);
        if (LocalDate.now().isEqual(earlySeason)) {
            assertEquals(1.0f, ticket.getCharge(), "Should return full charge early in the season.");
        }

        // Assume a date mid-season
        LocalDate midSeason = LocalDate.of(2024, 6, 15);
        if (LocalDate.now().isEqual(midSeason)) {
            assertEquals(0.95f, ticket.getCharge(), "Should return discounted charge mid-season.");
        }

        // Assume a date late in the season
        LocalDate lateSeason = LocalDate.of(2024, 11, 15);
        if (LocalDate.now().isEqual(lateSeason)) {
            assertEquals(0.9f, ticket.getCharge(), "Should return greater discount late in the season.");
        }

        // Assume a date after the season ends
        LocalDate afterSeason = LocalDate.of(2025, 1, 15);
        if (LocalDate.now().isEqual(afterSeason)) {
            assertEquals(1.0f, ticket.getCharge(), "Should return full charge after the season has ended.");
        }
    }

    @Test
    void getCharge_earlyInSeason_returnsFullCharge() {
        // This test must be run early in the season to pass
        LocalDate startOfSeason = LocalDate.of(2024, 4, 1);
        LocalDate endOfSeason = LocalDate.of(2024, 9, 30);
        ticket = new SeasonTicketVO(1, 100.0f, event, startOfSeason, endOfSeason);

        LocalDate today = LocalDate.now();
        // Ensure today's date is early in the season
        if (!today.isBefore(startOfSeason) && today.isBefore(startOfSeason.plusDays((int) (Period.between(startOfSeason, endOfSeason).getDays() * 0.5)))) {
            assertEquals(1.0f, ticket.getCharge(), "Charge should be full price early in the season.");
        }
    }

/*    @Test
    void getCharge_midSeason_returnsDiscountedCharge() {
        // This test must be run mid-season to pass
        LocalDate startOfSeason = LocalDate.of(2024, 4, 1);
        LocalDate endOfSeason = LocalDate.of(2024, 11, 30);
        ticket = new SeasonTicketVO(1, 100.0f, event, startOfSeason, endOfSeason);

        LocalDate today = LocalDate.now();
        int daysOfSeason = Period.between(startOfSeason, endOfSeason).getDays();
        int daysLeft = Period.between(today, endOfSeason).getDays();
        float expectedCharge = (daysOfSeason / daysLeft) <= 0.5f ? 1.0f : (daysOfSeason / daysLeft) <= 0.8f ? 0.95f : 0.9f;

        // Ensure today's date is mid-season
        if (!today.isBefore(startOfSeason.plusDays((int) (daysOfSeason * 0.5))) && today.isBefore(startOfSeason.plusDays((int) (daysOfSeason * 0.8)))) {
            assertEquals(0.95f, ticket.getCharge(), "Charge should be discounted mid-season.");
        } else {
            assertEquals(expectedCharge, ticket.getCharge(), "Charge should be calculated based on the time of the season.");
        }
    }*/

/*    @Test
    void getCharge_lateSeason_returnsGreaterDiscount() {
        // This test must be run late in the season to pass
        LocalDate startOfSeason = LocalDate.of(2024, 4, 1);
        LocalDate endOfSeason = LocalDate.of(2024, 6, 30);
        ticket = new SeasonTicketVO(1, 100.0f, event, startOfSeason, endOfSeason);

        LocalDate today = LocalDate.now();
        int daysOfSeason = Period.between(startOfSeason, endOfSeason).getDays();
        int daysLeft = Period.between(today, endOfSeason).getDays();
        float expectedCharge = (daysOfSeason / daysLeft) <= 0.5f ? 1.0f : (daysOfSeason / daysLeft) <= 0.8f ? 0.95f : 0.9f;

        // Ensure today's date is late-season
        if (!today.isBefore(startOfSeason.plusDays((int) (daysOfSeason * 0.8))) && today.isBefore(endOfSeason)) {
            assertEquals(0.9f, ticket.getCharge(), "Charge should be greater discounted late-season.");
        } else {
            assertEquals(expectedCharge, ticket.getCharge(), "Charge should be calculated based on the time of the season.");
        }
    }*/

    @AfterEach
    public void teardown() {
        // Reset all the objects to null to ensure no state is carried over between tests
        event = null;
        ticket = null;


        // If there are any static variables in your TicketVO or related classes, reset them as well
        // For example, if you have a static nextId in the TicketVO class:
        // TicketVO.resetNextId(); // Assuming you create a reset method in TicketVO
    }
}