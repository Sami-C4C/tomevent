package de.thb.dim.eventTom.valueObjects.eventManagement;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import de.thb.dim.eventTom.valueObjects.ticketSale.TicketVO;


/**
 * Osama Ahmad:
 * Cloneable is implemented.
 * The error java.lang.InternalError occurs because clone
 * throws a CloneNotSupportedException. This typically happens
 * when the class does not implement the Cloneable interface.
 */
public abstract class EventVO implements Serializable, Cloneable {

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


    /**
     * Osama Ahmad, MN:20233244.
     * NullPointerException, IllegalArgumentException are inserted,
     * for testing also invalid values
     * @param id
     * @param name
     * @param equipment
     * @param location
     * @param date
     * @param anzCategory
     * @throws NullPointerException
     * @throws IllegalArgumentException
     */
    public EventVO(int id, String name, String[] equipment, String location, LocalDateTime date, int anzCategory) throws NullPointerException, IllegalArgumentException {
        setId(id);
        setName(name);
        setEquipment(equipment);
        setLocation(location);
        setDate(date);
        this.ticketCategory = new ArrayList<>(anzCategory);
    }

    /**
     * Osama Ahmad, MN:20233244.
     * EventVO is abstract and could not be instantiated.
     * every created event should have an id with concreted name.
     *  with some tests like testPartyWithNullName, NullPointerException to be thrown
     *  Also with testPartyWithoutId, IllegalArgumentException to be thrown.
     *
     */
    public EventVO() {
        this(0, null, null, null, null, 1);
    }


    /**
     * Fixed by Osama Ahmad, MN:20233244
     * @return
     * @throws CloneNotSupportedException
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        EventVO clonedEvent = (EventVO) super.clone();

        // Deep clone the ticketCategory ArrayList if it's not null
        if (this.ticketCategory != null) {
            clonedEvent.ticketCategory = new ArrayList<>(this.ticketCategory.size());
            for (TicketVO ticket : this.ticketCategory) {
                // Assuming TicketVO also implements Cloneable and has a correctly overridden clone method
                clonedEvent.ticketCategory.add((TicketVO) ticket.clone());
            }
        }
        return clonedEvent;
    }










    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(getId() + " - ");
        sb.append(getName() + ": ");
        sb.append(getDate() + " at ");
        sb.append(getLocation() + "\t");
        sb.append(equipmentToString());
        return sb.toString();
    }

    /**
     * Osama Ahmad:
     *  * Converts the equipment array of this event into a single, comma-separated string.
     *  * This method handles various scenarios including null elements within the equipment array
     *  * and entirely null or empty arrays. It ensures a clean and readable string output for
     *  * the equipment list. If the equipment array is null or empty, or if all elements are null,
     *  * an empty string is returned. Otherwise, a formatted string with all non-null elements
     *  * joined by commas is provided.
     *
     * @return
     */
    public String equipmentToString() {
        if (equipment == null || equipment.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (String currentEquipment : equipment) {
            if (currentEquipment != null) { // Check for null items
                sb.append(currentEquipment).append(", ");
            }
        }
        // If the StringBuilder ends up empty (all items were null), return an empty string
        if (sb.length() == 0) {
            return "";
        }
        // Otherwise, remove the last comma and space
        return sb.substring(0, sb.length() - 2);
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
        }if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }



    /**
     * Fixed by Osama Ahmad,MN:20233244
     */
    public void calculateNrAvailableTickets() {
        // Reset the number of available tickets before calculation
        nrAvailableTickets = 0;

        // Sum the number of tickets from each category
        for(TicketVO t : ticketCategory) {

            nrAvailableTickets += t.getNumber();
        }
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

    // Osama Ahmad: Mocking-tests are written to check invalid values for Setters

    /**
     * Osama Ahmad:
     * To test also the invalid IDs values, but I got error because of if (name == null) into equals function
     * therefore I wrote mocking-test into EventVO to check also the invalid values for alle setters
     * because I could during testing assign invalid values for price, id, name ...etc.
     * See → testEventVO_SetterWithInvalidValue() into EventVOTest.
     * @param id
     */
    public void setId(int id) {
        this.id = id;
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

    /**
     * Osama Ahmad:
     * It makes possible to set a season ticket price for party events (PartyVO instances). This behavior
     * is contrary to business logic, as season tickets for party events should not be available.
     * I wrote the fixed implementation in PartyVOMocking of PartyVOTest.
     * @param seasonTicketPrice
     */
    public void setSeasonTicketPrice(float seasonTicketPrice) {
        this.seasonTicketPrice = seasonTicketPrice;
    }

    /**
     * Osama Ahmad:
     * It makes possible to set a backstage ticket price for party events (PartyVO instances). This behavior
     * is contrary to business logic, as backstage tickets for party events should not be available.
     * I wrote the fixed implementation into PartyVOMocking of PartyVOTest.
     * @param backstageTicketPrice
     */
    public void setBackstageTicketPrice(float backstageTicketPrice) {
        this.backstageTicketPrice = backstageTicketPrice;
    }


}