package de.thb.dim.eventTom.valueObjects.eventManagement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Written by Osama Ahmad , MN: 20233244
 */
class TestEventManagement {
    private PartyVO party;
    private ShowVO show;

    @BeforeEach
    public void setup() {
        // Erstellen realer Objekte
        String[] equipment = {"Sound System", "Lights"};
        LocalDateTime partyDate = LocalDateTime.now().plusDays(7);
        LocalDateTime showDate = LocalDateTime.now().plusDays(14);
        Duration runtime = Duration.ofHours(2);

        party = new PartyVO(1, "Party 1", equipment, "Club XYZ", partyDate, "Buffet", "DJ John");
        show = new ShowVO(2, "Show 1", equipment, "Theater ABC", showDate, runtime, 1);
    }

    @Test
    public void testPartyVOAttributes() {
        // Test PartyVO attributes
        assertEquals(1, party.getId());
        assertEquals("Party 1", party.getName());
        assertEquals("Sound System, Lights", party.equipmentToString());
        assertEquals("Club XYZ", party.getLocation());
    }

    @Test
    public void testShowVOAttributes() {
        // Test ShowVO attributes
        assertEquals(2, show.getId());
        assertEquals("Show 1", show.getName());
        assertEquals("Sound System, Lights", show.equipmentToString());
        assertEquals("Theater ABC", show.getLocation());
    }


    @Test
    public void partyWithValidAttributes() {
        assertNotNull(party);
        assertEquals("Party 1", party.getName());
    }


    @Test
    public void partyWithoutId() {
        assertThrows(IllegalArgumentException.class, () -> {
            new PartyVO(0, "Party 1", new String[]{"Sound System", "Lights"}, "Club XYZ", LocalDateTime.now(), "Buffet", "DJ John");
        });
    }

    @Test
    public void partyWithNegativId() {
        assertThrows(IllegalArgumentException.class, () -> {
            new PartyVO(-2, "Party 1", new String[]{"Sound System", "Lights"}, "Club XYZ", LocalDateTime.now(), "Buffet", "DJ John");
        });
    }


    @Test
    public void partyWithNullName() {
        assertThrows(NullPointerException.class, () -> {
            new PartyVO(1, null, new String[]{"Sound System", "Lights"}, "Club XYZ", LocalDateTime.now(), "Buffet", "DJ John");
        });
    }

    @Test
    public void partyWithEmptyEquipment() {
        PartyVO emptyEquipmentParty = new PartyVO(2, "Party 2", new String[]{}, "Club ABC", LocalDateTime.now(), "Snacks", "DJ Jane");
        assertEquals("", emptyEquipmentParty.equipmentToString());
    }

    @Test
    public void showWithValidAttributes() {
        assertNotNull(show);
        assertEquals("Show 1", show.getName());
    }

    @Test
    public void showWithNegativeRuntime() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ShowVO(3, "Show 2", new String[]{"Projector"}, "Theater XYZ", LocalDateTime.now(), Duration.ofHours(-2), 1);
        });
    }

    @Test
    public void showOnBoundaryDate() {
        LocalDateTime boundaryDate = LocalDateTime.MAX;
        ShowVO boundaryShow = new ShowVO(4, "Boundary Show", new String[]{"Stage"}, "Boundary Theater", boundaryDate, Duration.ofHours(1), 1);
        assertEquals(boundaryDate, boundaryShow.getDate());
    }


    @Test
    public void partyWithEmptyName() {
        PartyVO emptyNameParty = new PartyVO(1, "", new String[]{"Sound System"}, "Club XYZ", LocalDateTime.now(), "Buffet", "DJ John");
        assertEquals("", emptyNameParty.getName());
    }


    @Test
    public void partyWithFutureDate() {
        LocalDateTime futureDate = LocalDateTime.now().plusYears(1);
        PartyVO futureParty = new PartyVO(1, "Future Party", new String[]{"Sound System"}, "Club XYZ", futureDate, "Buffet", "DJ John");
        assertEquals(futureDate, futureParty.getDate());
    }

    @Test
    public void partyWithPastDate() {
        LocalDateTime pastDate = LocalDateTime.now().minusYears(1);
        PartyVO pastParty = new PartyVO(1, "Past Party", new String[]{"Sound System"}, "Club XYZ", pastDate, "Buffet", "DJ John");
        assertEquals(pastDate, pastParty.getDate());
    }


    @Test
    public void showWithNoEquipment() {
        ShowVO noEquipmentShow = new ShowVO(2, "Show No Equipment", new String[]{}, "Theater ABC", LocalDateTime.now(), Duration.ofHours(2), 1);
        assertEquals("", noEquipmentShow.equipmentToString());
    }


    @Test
    public void showWithMaxRuntime() {
        Duration maxRuntime = Duration.ofHours(24); // Angenommen, dies ist die maximale Dauer
        ShowVO maxRuntimeShow = new ShowVO(3, "Max Runtime Show", new String[]{"Projector"}, "Theater XYZ", LocalDateTime.now(), maxRuntime, 1);
        assertEquals(maxRuntime, maxRuntimeShow.getRuntime());
    }

    @Test
    public void setNrAvailableTicketsWithValidValue() {
        ShowVO show = new ShowVO(1, "Test Show", new String[]{"Sound System"}, "Test Location", LocalDateTime.now(), Duration.ofHours(2), 1);
        show.setNrAvailableTickets(100);
        assertEquals(100, show.getNrAvailableTickets());
    }


    @Test
    public void setNrAvailableTicketsToZero() {
        ShowVO show = new ShowVO(1, "Test Show", new String[]{"Sound System"}, "Test Location", LocalDateTime.now(), Duration.ofHours(2), 1);
        show.setNrAvailableTickets(0);
        assertEquals(0, show.getNrAvailableTickets());
    }

    @Test
    public void setNrAvailableTicketsToNegative() {
        ShowVO show = new ShowVO(1, "Test Show", new String[]{"Sound System"}, "Test Location", LocalDateTime.now(), Duration.ofHours(2), 1);
        show.setNrAvailableTickets(-1);
        assertEquals(-1, show.getNrAvailableTickets()); // Annahme: Negativer Wert ist zugelassen
    }



/*    @Test
    public void showWithInvalidSeat() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ShowVO(4, "Invalid Seat Show", new String[]{"Stage"}, "Boundary Theater", LocalDateTime.now(), Duration.ofHours(1), -1);
        });
    }*/


}
