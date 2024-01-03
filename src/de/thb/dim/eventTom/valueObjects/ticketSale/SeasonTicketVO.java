package de.thb.dim.eventTom.valueObjects.ticketSale;

import java.time.LocalDate;
import java.time.Period;

import de.thb.dim.eventTom.valueObjects.eventManagement.EventVO;

public class SeasonTicketVO extends TicketVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int nextId = 1;
	private LocalDate startOfSeason;
	private LocalDate endOfSeason;
	
	public SeasonTicketVO(int number, float price, EventVO event, LocalDate startOfSeason, LocalDate endOfSeason) {
		super(number, event.getName() + " Season " + nextId++, price,  event);
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

	@Override
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
	}

	@Override
	public String getSeatOfTicket() {
		StringBuilder sb = new StringBuilder();
		sb.append(getSeat() + " +");
		return sb.toString();
	}

}
