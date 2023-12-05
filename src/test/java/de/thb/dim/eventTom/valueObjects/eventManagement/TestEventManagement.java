package de.thb.dim.eventTom.valueObjects.eventManagement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


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
    public void testPartyWithValidAttributes() {
        assertNotNull(party);
        assertEquals("Party 1", party.getName());
    }


    @Test
    public void testPartyWithoutId() {
        assertThrows(IllegalArgumentException.class, () -> {
            new PartyVO(0, "Party 1", new String[]{"Sound System", "Lights"}, "Club XYZ", LocalDateTime.now(), "Buffet", "DJ John");
        });
    }

    @Test
    public void testPartyWithNegativId() {
        assertThrows(IllegalArgumentException.class, () -> {
            new PartyVO(-2, "Party 1", new String[]{"Sound System", "Lights"}, "Club XYZ", LocalDateTime.now(), "Buffet", "DJ John");
        });
    }


    @Test
    public void testPartyWithNullName() {
        assertThrows(NullPointerException.class, () -> {
            new PartyVO(1, null, new String[]{"Sound System", "Lights"}, "Club XYZ", LocalDateTime.now(), "Buffet", "DJ John");
        });
    }

    @Test
    public void testPartyWithEmptyEquipment() {
        PartyVO emptyEquipmentParty = new PartyVO(2, "Party 2", new String[]{}, "Club ABC", LocalDateTime.now(), "Snacks", "DJ Jane");
        assertEquals("", emptyEquipmentParty.equipmentToString());
    }

    @Test
    public void testShowWithValidAttributes() {
        assertNotNull(show);
        assertEquals("Show 1", show.getName());
    }

    @Test
    public void testShowWithNegativeRuntime() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ShowVO(3, "Show 2", new String[]{"Projector"}, "Theater XYZ", LocalDateTime.now(), Duration.ofHours(-2), 1);
        });
    }

    @Test
    public void testShowOnBoundaryDate() {
        LocalDateTime boundaryDate = LocalDateTime.MAX;
        ShowVO boundaryShow = new ShowVO(4, "Boundary Show", new String[]{"Stage"}, "Boundary Theater", boundaryDate, Duration.ofHours(1), 1);
        assertEquals(boundaryDate, boundaryShow.getDate());
    }


    @Test
    public void testPartyWithEmptyName() {
        PartyVO emptyNameParty = new PartyVO(1, "", new String[]{"Sound System"}, "Club XYZ", LocalDateTime.now(), "Buffet", "DJ John");
        assertEquals("", emptyNameParty.getName());
    }


    @Test
    public void testPartyWithFutureDate() {
        LocalDateTime futureDate = LocalDateTime.now().plusYears(1);
        PartyVO futureParty = new PartyVO(1, "Future Party", new String[]{"Sound System"}, "Club XYZ", futureDate, "Buffet", "DJ John");
        assertEquals(futureDate, futureParty.getDate());
    }

    @Test
    public void testPartyWithPastDate() {
        LocalDateTime pastDate = LocalDateTime.now().minusYears(1);
        PartyVO pastParty = new PartyVO(1, "Past Party", new String[]{"Sound System"}, "Club XYZ", pastDate, "Buffet", "DJ John");
        assertEquals(pastDate, pastParty.getDate());
    }


    @Test
    public void testShowWithNoEquipment() {
        ShowVO noEquipmentShow = new ShowVO(2, "Show No Equipment", new String[]{}, "Theater ABC", LocalDateTime.now(), Duration.ofHours(2), 1);
        assertEquals("", noEquipmentShow.equipmentToString());
    }


    @Test
    public void testShowWithMaxRuntime() {
        Duration maxRuntime = Duration.ofHours(24); // Angenommen, dies ist die maximale Dauer
        ShowVO maxRuntimeShow = new ShowVO(3, "Max Runtime Show", new String[]{"Projector"}, "Theater XYZ", LocalDateTime.now(), maxRuntime, 1);
        assertEquals(maxRuntime, maxRuntimeShow.getRuntime());
    }

    @Test
    public void testSetNrAvailableTicketsWithValidValue() {
        ShowVO show = new ShowVO(1, "Test Show", new String[]{"Sound System"}, "Test Location", LocalDateTime.now(), Duration.ofHours(2), 1);
        show.setNrAvailableTickets(100);
        assertEquals(100, show.getNrAvailableTickets());
    }


    @Test
    public void testSetNrAvailableTicketsToZero() {
        ShowVO show = new ShowVO(1, "Test Show", new String[]{"Sound System"}, "Test Location", LocalDateTime.now(), Duration.ofHours(2), 1);
        show.setNrAvailableTickets(0);
        assertEquals(0, show.getNrAvailableTickets());
    }

    @Test
    public void testSetNrAvailableTicketsToNegative() {
        ShowVO show = new ShowVO(1, "Test Show", new String[]{"Sound System"}, "Test Location", LocalDateTime.now(), Duration.ofHours(2), 1);
        show.setNrAvailableTickets(-1);
        assertEquals(-1, show.getNrAvailableTickets()); // Annahme: Negativer Wert ist zugelassen
    }

}
