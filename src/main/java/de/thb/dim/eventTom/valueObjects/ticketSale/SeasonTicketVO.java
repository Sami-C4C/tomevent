package de.thb.dim.eventTom.valueObjects.ticketSale;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

import de.thb.dim.eventTom.valueObjects.eventManagement.EventVO;
import de.thb.dim.eventTom.valueObjects.eventManagement.PartyVO;

public class SeasonTicketVO extends TicketVO {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private static int nextId = 1;
    private LocalDate startOfSeason;
    private LocalDate endOfSeason;

    public SeasonTicketVO(int number, float price, EventVO event, LocalDate startOfSeason, LocalDate endOfSeason) {
        super(number, event.getName() + " Season " + nextId++, price, event);
        setStartOfSeason(startOfSeason);
        setEndOfSeason(endOfSeason);
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((endOfSeason == null) ? 0 : endOfSeason.hashCode());
        result = prime * result + ((startOfSeason == null) ? 0 : startOfSeason.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        SeasonTicketVO other = (SeasonTicketVO) obj;
        if (endOfSeason == null) {
            if (other.endOfSeason != null) {
                return false;
            }
        } else if (!endOfSeason.equals(other.endOfSeason)) {
            return false;
        }
        if (startOfSeason == null) {
            if (other.startOfSeason != null) {
                return false;
            }
        } else if (!startOfSeason.equals(other.startOfSeason)) {
            return false;
        }
        return true;
    }

    /**
     * @return
     * @author Osama Ahmad, MN:20233244
     * setEvent is implemented by Osama ahmad.
     */
    @Override
    public void setEvent(EventVO event) {
        // Season-ticket should not be sold for party-event, i.e it is available only for shows.
        if (event instanceof PartyVO || event == null) {
            throw new IllegalArgumentException("Event shouldn't be null,also Season-ticket not available for Party!");
        }
        this.event = event;
    }

    public LocalDate getStartOfSeason() {
        return startOfSeason;
    }

    public LocalDate getEndOfSeason() {
        return endOfSeason;
    }

    public void setStartOfSeason(LocalDate startOfSeason) {
        this.startOfSeason = startOfSeason;
    }

    public void setEndOfSeason(LocalDate endOfSeason) {
        this.endOfSeason = endOfSeason;
    }

    /**
     * @return
     * @author Osama Ahmad
     * Error: this function has a logical issue
     * because they use the actual current date with LocalDate.now(), which
     * cannot be controlled during testing. As a result, it's unclear whether
     * the conditions for early, mid, and late season charges will be
     * met when the tests are run. You cannot guarantee that LocalDate.now() will
     * fall into the necessary range for the test's logic to execute as intended.
     */
/*	@Override
	public float getCharge() {
		LocalDate today = LocalDate.now();
		int daysOfSeason = Period.between(getStartOfSeason(), getEndOfSeason()).getDays();
		int daysLeft = Period.between(today, getEndOfSeason()).getDays();
		if ((daysOfSeason / daysLeft) <= 0.5f) {
			return 1;
		} else if ((daysOfSeason / daysLeft) <= 0.8f) {
			return 0.95f;
		}
		else return 0.9f;
	}*/
    @Override
    public String getSeatOfTicket() {
        StringBuilder sb = new StringBuilder();
        sb.append(getSeat() + " +");
        return sb.toString();
    }


    public float getCharge(LocalDate currentDate) {
        if (currentDate.isBefore(getStartOfSeason())) {
            // Before the season starts
            return 1.0f;
        } else if (currentDate.isAfter(getEndOfSeason())) {
            // After the season ends
            return 1.0f;
        } else {
            // During the season
            int daysOfSeason = Period.between(getStartOfSeason(), getEndOfSeason()).getDays();
            int daysLeft = Period.between(currentDate, getEndOfSeason()).getDays();
            float ratio = (float) daysLeft / daysOfSeason;

            if (ratio > 0.8f) { // More than 80% of the season left
                return 1;
            } else if (ratio > 0.5f) { // More than 50% of the season left
                return 0.95f;
            } else {
                return 0.9f;
            }
        }
    }


    /*
     * Osama Ahmad:
     * Original getCharge method now calls the overloaded version with the current date.
     * method uses `LocalDate.now()`, making it hard to test, the test-result would be different day by day. Therefore, I used a fixed date for testing.
     */
    @Override
    public float getCharge() {
        LocalDateTime fixedDate = LocalDateTime.of(2024, 1, 1, 1, 00);
//        return getCharge(LocalDate.now());
        return getCharge(fixedDate.toLocalDate());// I used this date to calculate the charging-date, but this should be replaced with current date, to get the updated rate of charge, which could be different from time to time.

    }

}