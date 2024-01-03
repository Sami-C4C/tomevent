package de.thb.dim.eventTom.valueObjects.ticketSale;

import de.thb.dim.eventTom.valueObjects.customerManagement.CustomerVO;
import de.thb.dim.eventTom.valueObjects.customerManagement.Gender;
import de.thb.dim.eventTom.valueObjects.customerManagement.exceptions.CustomerNoDateOfBirthException;
import de.thb.dim.eventTom.valueObjects.customerManagement.exceptions.CustomerTooYoungException;
import de.thb.dim.eventTom.valueObjects.eventManagement.EventVO;
import de.thb.dim.eventTom.valueObjects.eventManagement.PartyVO;
import de.thb.dim.eventTom.valueObjects.eventManagement.ShowVO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;


/**
 * @author Osama Ahmad , MN:20233244
 */


class SeasonTicketVOTest {

    private EventVO event;
    private SeasonTicketVO seasonTicket;
    private BackstageTicketVO backStageTicket;
    /*    private LocalDate startOfSeason;
        private LocalDate endOfSeason;*/
    Duration runtime;
    LocalDateTime showStartTime, showEndTime;


    private CustomerVO customer3;


    @BeforeEach
    public void setUp() throws CustomerNoDateOfBirthException, CustomerTooYoungException {

/*        startOfSeason = LocalDate.of(2023, 1, 1);
        endOfSeason = LocalDate.of(2023, 12, 31);*/

        String[] showEquipment = {"Lights", "Speaker", "Furniture"};

        customer3 = new CustomerVO("Gieske", "Antonia", "Gertraudenstr", 77, Gender.F, LocalDate.of(1997, 4, 13));

        LocalDateTime showDate = LocalDateTime.of(2024, 5, 1, 13, 00);
        showStartTime = LocalDateTime.of(2024, 3, 13, 18, 00);
        showEndTime = LocalDateTime.of(2024, 3, 18, 23, 00);


        runtime = Duration.ofHours(4);

        event = new ShowVO(1, "Show1", showEquipment, "Berlin", showDate, runtime, 4);


        seasonTicket = new SeasonTicketVO(100, 100.0f, event, showStartTime.toLocalDate(), showEndTime.toLocalDate());
        backStageTicket = new BackstageTicketVO(224, 45.60f, "B23", event, customer3);


    }



    @Test
    void test_equals_sameObjectComparison_shouldReturnTrue() {
        assertTrue(seasonTicket.equals(seasonTicket),"Comparing the same instance should return true.");
    }


    @Test
    void equals_nullComparison_shouldReturnFalse() {
        assertFalse(seasonTicket.equals(null));
    }

    @Test
    void test_equals_differentClassComparison_shouldReturnFalse() {
        Object otherObject = new Object();
        assertFalse(seasonTicket.equals(otherObject) && otherObject.equals(seasonTicket));
        assertFalse(seasonTicket.equals(backStageTicket), "Comparing an object of a different class should return false.");
        assertFalse(otherObject instanceof TicketVO);
    }

    @Test
    void equals_endOfSeasonNullInCurrentObjectComparison_shouldReturnFalse() {
        SeasonTicketVO ticketWithNullEnd = new SeasonTicketVO(1, 100.0f, event, LocalDate.of(2024, 1, 1), null);
        SeasonTicketVO ticketWithNonNullEnd = new SeasonTicketVO(1, 100.0f, event, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 6, 1));
        assertFalse(ticketWithNullEnd.equals(ticketWithNonNullEnd), "Comparing when endOfSeason is null in the current object but not in the compared object should return false.");
    }

    @Test
    void equals_startOfSeasonNullInCurrentObjectComparison_shouldReturnFalse() {
        SeasonTicketVO ticketWithNullStart = new SeasonTicketVO(1, 100.0f, event, null, LocalDate.of(2024, 6, 1));
        SeasonTicketVO ticketWithNonNullStart = new SeasonTicketVO(1, 100.0f, event, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 6, 1));
        assertFalse(ticketWithNullStart.equals(ticketWithNonNullStart), "Comparing when startOfSeason is null in the current object but not in the compared object should return false.");
    }



    @Test
    void testEquals() {
        SeasonTicketVO ticket1 = new SeasonTicketVO(1, 100.0f, event, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 6, 1));
        SeasonTicketVO ticket2 = new SeasonTicketVO(100, 100.0f, event, seasonTicket.getStartOfSeason(), seasonTicket.getEndOfSeason());
        assertTrue(seasonTicket.equals(ticket2), "Two tickets with the same attributes should be equal.");
        assertFalse(ticket1.equals(ticket2), "Two tickets with the different attributes should not be equal.");
        SeasonTicketVO ticketDifferent = new SeasonTicketVO(1, 100.0f, event, LocalDate.of(2024, 1, 2), LocalDate.of(2024, 6, 2));
        assertFalse(ticket1.equals(ticketDifferent), "Two tickets with different dates should not be equal.");

        seasonTicket.setEndOfSeason(null);

        assertFalse(ticket1.equals(seasonTicket));// Test equals when the end of session is null and the other end of session ist not null then should return false.
        assertFalse(seasonTicket.equals(ticket1));
        seasonTicket.setEndOfSeason(ticket2.getEndOfSeason()); // reset endOfSession of seasonTicket back to the beginning
        ticket2.setStartOfSeason(null); // set start-session of ticket2 to null

        assertFalse(ticket2.equals(seasonTicket)); //When StartOfSeason is null and other StartOfSeason is not null, then should return false.

        ticket1.setStartOfSeason(seasonTicket.getStartOfSeason()); // StartOfSession for ticket1 and ticket2 are the same but with different endOfSession
        assertFalse(ticket1.equals(ticket2)); //When EndOfSeason is not equal to other EndOfSeason should return False.


        ticketDifferent.setEndOfSeason(seasonTicket.getEndOfSeason()); // ticket2 and ticketDifferent have both the same EndOfSeason but different StartOfSeason
        assertFalse(ticketDifferent.equals(ticket2));      // When StartOfSeason is not equal To other StartOfSeason, should return false

        Object other = new Object();
        assertFalse(other instanceof TicketVO && ticket1 instanceof Object);

        Object seasonTicketClone = seasonTicket.clone();
        assertEquals(seasonTicket, seasonTicketClone);

    }


    @Test
    void testEquals_differentObjectSameValuesComparison_shouldReturnTrue() {
        SeasonTicketVO anotherTicketWithSameValues = new SeasonTicketVO(
                seasonTicket.getNumber(),
                seasonTicket.getBasePrice(),
                seasonTicket.getEvent(),
                seasonTicket.getStartOfSeason(),
                seasonTicket.getEndOfSeason()
        );
        assertTrue(seasonTicket.equals(anotherTicketWithSameValues));
    }

    @Test
    void testEquals_differentObjectDifferentStartOfSeasonComparison_shouldReturnFalse() {
        SeasonTicketVO anotherTicketWithDifferentStartOfSeason = new SeasonTicketVO(
                seasonTicket.getNumber(),
                seasonTicket.getBasePrice(),
                seasonTicket.getEvent(),
                seasonTicket.getStartOfSeason().plusDays(1),
                seasonTicket.getEndOfSeason()
        );
        assertFalse(seasonTicket.equals(anotherTicketWithDifferentStartOfSeason));
    }

    @Test
    void testEquals_differentObjectDifferentEndOfSeasonComparison_shouldReturnFalse() {
        SeasonTicketVO anotherTicketWithDifferentEndOfSeason = new SeasonTicketVO(
                seasonTicket.getNumber(),
                seasonTicket.getBasePrice(),
                seasonTicket.getEvent(),
                seasonTicket.getStartOfSeason(),
                seasonTicket.getEndOfSeason().plusDays(1)
        );
        assertFalse(seasonTicket.equals(anotherTicketWithDifferentEndOfSeason));
    }


    @Test
    void equals_withSameFields_shouldReturnTrue() {
        EventVO sameEvent = (EventVO) event.clone();
        SeasonTicketVO anotherTicket = new SeasonTicketVO(100, 100.0f, sameEvent, showStartTime.toLocalDate(), showEndTime.toLocalDate());
        assertTrue(seasonTicket.equals(anotherTicket));
    }


    @Test
    void testEquals_withDifferentFields_shouldReturnFalse() {
        String[] diffShowEquipment = {"Lights", "Speaker", "Furniture", "Stage"};
        LocalDateTime showDate = LocalDateTime.of(2024, 3, 13, 18, 00);

        EventVO differentEvent = new ShowVO(45, "Show 2", diffShowEquipment, "Theater XYZ", showDate, runtime, 3);
        LocalDate differentStart = LocalDate.of(2023, 2, 1);
        LocalDate differentEnd = LocalDate.of(2023, 11, 30);
        SeasonTicketVO differentTicket = new SeasonTicketVO(2, 150.0f, differentEvent, differentStart, differentEnd);
        assertFalse(seasonTicket.equals(differentTicket));
    }


    @Test
    void testHashCode() {
        SeasonTicketVO ticket = new SeasonTicketVO(1, 100.0f, event, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 10));
        int expectedHash = ticket.hashCode(); // Save the original hash code
        int newHash = new SeasonTicketVO(1, 100.0f, event, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 10)).hashCode();
        assertEquals(expectedHash, newHash, "Hash code should be the same for two objects with the same properties.");
    }


    @Test
    void equals_withNull_shouldReturnFalse() {
        assertFalse(seasonTicket.equals(null));
    }

    @Test
    void equals_withDifferentClass_shouldReturnFalse() {
        Object notATicket = new Object();
        assertFalse(seasonTicket.equals(notATicket));
    }

    @Test
    void getEvent_shouldReturnSetEvent() {
        assertSame(event, seasonTicket.getEvent());
    }


    @Test
    void setEvent_withPartyEvent_shouldThrowIllegalArgumentException() {
        LocalDateTime partyDate = LocalDateTime.of(2023, 12, 31, 22, 00);

        EventVO partyEvent = new PartyVO(1, "Party 1", new String[]{"Sound System", "Lights"}, "Club XYZ", partyDate, "Buffet", "DJ John");
        assertThrows(IllegalArgumentException.class, () -> seasonTicket.setEvent(partyEvent));
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
        SeasonTicketVO ticket = new SeasonTicketVO(1, 100.0f, event, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 10));
        float charge = ticket.getCharge();
        assertEquals(1.0f, charge, "Should return the correct charge based on the time of the season.");
    }


    @Test
    void getSeatOfTicket() {
        SeasonTicketVO ticket = new SeasonTicketVO(1, 100.0f, event, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 6, 1));
        ticket.setSeat("A1");
        assertEquals("A1 +", ticket.getSeatOfTicket(), "Should return the correct seat string.");
    }


  /*  @Test
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
    }*/


    @Test
    void getCharge_variousScenarios() {
        // Define the season period
        LocalDate startOfSeason = LocalDate.of(2024, 1, 1);
        LocalDate endOfSeason = LocalDate.of(2024, 12, 31);

        // Create the ticket
        SeasonTicketVO ticket = new SeasonTicketVO(1, 100.0f, event, startOfSeason, endOfSeason);

        // Assume a date early in the season
        LocalDate earlySeason = LocalDate.of(2024, 1, 2);
        assertEquals(1.0f, ticket.getCharge(earlySeason), "Should return full charge early in the season.");

        // Assume a date mid-season
        LocalDate midSeason = LocalDate.of(2024, 6, 15);
        assertEquals(0.95f, ticket.getCharge(midSeason), "Should return discounted charge mid-season.");

        // Assume a date late in the season
        LocalDate lateSeason = LocalDate.of(2024, 11, 18);
        assertEquals(0.9f, ticket.getCharge(lateSeason), "Should return greater discount late in the season.");

        // Assume a date after the season ends
        LocalDate afterSeason = LocalDate.of(2025, 1, 15);
        assertEquals(1.0f, ticket.getCharge(afterSeason), "Should return full charge after the season has ended.");
    }


    @Test
    void getCharge_earlyInSeason_returnsFullCharge() {
        // This test must be run early in the season to pass
        LocalDate startOfSeason = LocalDate.of(2024, 4, 1);
        LocalDate endOfSeason = LocalDate.of(2024, 9, 30);
        seasonTicket = new SeasonTicketVO(1, 100.0f, event, startOfSeason, endOfSeason);

        LocalDate today = LocalDate.now();
        // Ensure today's date is early in the season
        if (!today.isBefore(startOfSeason) && today.isBefore(startOfSeason.plusDays((int) (Period.between(startOfSeason, endOfSeason).getDays() * 0.5)))) {
            assertEquals(1.0f, seasonTicket.getCharge(), "Charge should be full price early in the season.");
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
        seasonTicket = null;

    }
}