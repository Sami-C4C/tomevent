package de.thb.dim.eventTom.valueObjects.eventManagement;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import de.thb.dim.eventTom.valueObjects.ticketSale.TicketVO;

public abstract class EventVO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected int id;
	protected int nrAvailableTickets;
	protected String name;
	protected String[] equipment;
	protected String location;
	protected LocalDateTime date;
	
	protected float seatTicketPrice;
	protected float seasonTicketPrice;
	protected float backstageTicketPrice;
	
	protected ArrayList<TicketVO> ticketCategory;
	
	public EventVO(int id, String name, String[] equipment, String location, LocalDateTime date, int anzCategory) {
		setId(id);
		setName(name);
		setEquipment(equipment);
		setLocation(location);
		setDate(date);
		ticketCategory = new ArrayList<TicketVO>();
	}
	
	public EventVO() {
		this(0, null, null, null, null, 1);
	}
	
	@Override
	public Object clone() {
		EventVO event = null;
		try {
			event = (EventVO) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError();
		}
		return event;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer(); 
		sb.append(getId() + " - " );
		sb.append(getName() + ": ");
		sb.append(getDate() + " at ");
		sb.append(getLocation() + "\t");
		sb.append(equipmentToString());
		return sb.toString();
	}
	
	public String equipmentToString() {
		StringBuffer sb = new StringBuffer();

		if (getEquipment() != null) { 
			for (String currentEquipment : getEquipment()) {
				sb.append(currentEquipment + ", ");
			}
			// remove comma at the end
			sb = new StringBuffer(sb.substring(0, sb.length() - 2)); 
		}
		return sb.toString();
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + Arrays.hashCode(equipment);
		result = prime * result + id;
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) 
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass()) 
			return false;
		EventVO other = (EventVO) obj;
		if (date == null) {
			if (other.date != null) {
				return false;
			}
		} else if (!date.equals(other.date)) {
			return false;
		}
		if (id != other.id) {
			return false;
		}
		if (location == null) {
			if (other.location != null) {
				return false;
			}
		} else if (!location.equals(other.location)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}
	
	public void calculateNrAvailableTickets() {
		for(TicketVO t : ticketCategory)
			nrAvailableTickets += t.getNumber();
	}
	
	public void addTicketCategory(TicketVO t) {
		ticketCategory.add(t);
	}
	
	public void deleteTicketCategory(TicketVO t) {
		ticketCategory.remove(t);
	}
	
	public TicketVO getTicketCategory(int index) {
		return ticketCategory.get(index);
	}

	public int getId() {
		return id;
	}
	
	public int getNrAvailableTickets() {
		return nrAvailableTickets;
	}


	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	
	
	public String[] getEquipment() {
		return equipment;
	}
	
	public String getLocation() {
		return location;
	}
	
	public LocalDateTime getDate() {
		return date;
	}
	
	public float getSeatTicketPrice() {
		return seatTicketPrice;
	}
	
	public float getSeasonTicketPrice() {
		return seasonTicketPrice;
	}
	
	public float getBackstageTicketPrice() {
		return backstageTicketPrice;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setEquipment(String[] equipment) {
		this.equipment = equipment;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	
	public void setSeatTicketPrice(float seatTicketPrice) {
		this.seatTicketPrice = seatTicketPrice;
	}
	
	public void setSeasonTicketPrice(float seasonTicketPrice) {
		this.seasonTicketPrice = seasonTicketPrice;
	}
	
	public void setBackstageTicketPrice(float backstageTicketPrice) {
		this.backstageTicketPrice = backstageTicketPrice;
	}
	
	
	
}
