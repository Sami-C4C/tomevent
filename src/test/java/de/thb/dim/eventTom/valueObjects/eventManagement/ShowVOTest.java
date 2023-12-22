package de.thb.dim.eventTom.valueObjects.eventManagement;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Osama Ahmad , MN:20233244
 */
class ShowVOTest {
    @Test
    void testShowVOInitialization() {
        ShowVO show = new ShowVO(3, "Theaterstück", new String[]{"Bühnendekoration", "Soundanlage"}, "Theater", LocalDateTime.now(), Duration.ofHours(2), 2);
        assertEquals(3, show.getId());
        assertEquals("Theaterstück", show.getName());
        assertEquals(Duration.ofHours(2), show.getRuntime());

    }

    @Test
    void testShowVOStandardConstructor() {
        ShowVO show = new ShowVO(3, "show3", null, null, null, null, 1);
        assertEquals(3, show.getId());
        assertNotNull(show.getName());
        assertNull(show.getLocation());
        assertNull(show.getDate());
        assertNull(show.getRuntime());
        // Weitere Überprüfungen für die Attribute von EventVO
    }


    @Test
    void testHashCodeConsistency() {
        ShowVO show = new ShowVO(1, "Show", new String[]{"Equipment"}, "Location", LocalDateTime.now(), Duration.ofHours(2), 1);
        int hashCode1 = show.hashCode();
        int hashCode2 = show.hashCode();

        assertEquals(hashCode1, hashCode2);
    }


    @Test
    void testDifferentObjectsHaveDifferentHashCodes() {
        ShowVO show1 = new ShowVO(1, "Show1", new String[]{"Equipment1"}, "Location1", LocalDateTime.now(), Duration.ofHours(2), 1);
        ShowVO show2 = new ShowVO(2, "Show2", new String[]{"Equipment2"}, "Location2", LocalDateTime.now(), Duration.ofHours(3), 2);

        assertNotEquals(show1.hashCode(), show2.hashCode());
    }


    @Test
    void testEqualsWithItself() {
        ShowVO show = new ShowVO(1, "Show", new String[]{"Equipment"}, "Location", LocalDateTime.now(), Duration.ofHours(2), 1);

        assertEquals(show, show);
    }


    @Test
    void testEqualsWithAnotherObjectWithSameValues() {
        LocalDateTime date = LocalDateTime.now();
        ShowVO show1 = new ShowVO(1, "Show", new String[]{"Equipment"}, "Location", date, Duration.ofHours(2), 1);
        ShowVO show2 = new ShowVO(1, "Show", new String[]{"Equipment"}, "Location", date, Duration.ofHours(2), 1);

        assertEquals(show1, show2);
    }


    @Test
    void testNotEqualsWithDifferentObject() {
        ShowVO show1 = new ShowVO(1, "Show1", new String[]{"Equipment1"}, "Location1", LocalDateTime.now(), Duration.ofHours(2), 1);
        ShowVO show2 = new ShowVO(2, "Show2", new String[]{"Equipment2"}, "Location2", LocalDateTime.now(), Duration.ofHours(3), 2);

        assertNotEquals(show1, show2);
    }


    @Test
    void testNotEqualsWithDifferentClassObject() {
        ShowVO show = new ShowVO(1, "Show", new String[]{"Equipment"}, "Location", LocalDateTime.now(), Duration.ofHours(2), 1);
        String notAShow = "Not a Show";

        assertNotEquals(show, notAShow);
    }

    @Test
    void testSameAttributesProduceSameHashCode() {
        LocalDateTime date = LocalDateTime.now();
        ShowVO show1 = new ShowVO(1, "Show", new String[]{"Equipment"}, "Location", date, Duration.ofHours(2), 1);
        ShowVO show2 = new ShowVO(1, "Show", new String[]{"Equipment"}, "Location", date, Duration.ofHours(2), 1);

        assertEquals(show1.hashCode(), show2.hashCode());
    }

    @Test
    void testDifferentRuntimeProducesDifferentHashCode() {
        ShowVO show1 = new ShowVO(1, "Show", new String[]{"Equipment"}, "Location", LocalDateTime.now(), Duration.ofHours(2), 1);
        ShowVO show2 = new ShowVO(1, "Show", new String[]{"Equipment"}, "Location", LocalDateTime.now(), Duration.ofHours(3), 1);

        assertNotEquals(show1.hashCode(), show2.hashCode());
    }

    @Test
    void testNotEqualsWithNull() {
        ShowVO show = new ShowVO(1, "Show", new String[]{"Equipment"}, "Location", LocalDateTime.now(), Duration.ofHours(2), 1);

        assertNotEquals(show, null);
    }

    @Test
    void testEqualsDespiteDifferentCreationTimes() {

        LocalDateTime fixedDate = LocalDateTime.of(2023, 10, 4, 12, 0); // Beispiel-Datum

        ShowVO show1 = new ShowVO(1, "Show", new String[]{"Equipment"}, "Location", fixedDate, Duration.ofHours(2), 1);
        ShowVO show2 = new ShowVO(1, "Show", new String[]{"Equipment"}, "Location", fixedDate, Duration.ofHours(2), 1);

        assertEquals(show1, show2);
    }


    @Test
    void testNotEqualsWithDifferentLocations() {
        ShowVO show1 = new ShowVO(1, "Show", new String[]{"Equipment"}, "Location1", LocalDateTime.now(), Duration.ofHours(2), 1);
        ShowVO show2 = new ShowVO(1, "Show", new String[]{"Equipment"}, "Location2", LocalDateTime.now(), Duration.ofHours(2), 1);

        assertNotEquals(show1, show2);
    }

    @Test
    void testEqualsWithDifferentClass() {
        ShowVO show = new ShowVO(1, "Show", new String[]{"Equipment"}, "Location", LocalDateTime.now(), Duration.ofHours(2), 1);
        Object other = new Object();

        assertNotEquals(show, other);
    }


    @Test
    void testEqualsWithNullRuntimeInOneObject() {
        ShowVO show1 = new ShowVO(1, "Show", new String[]{"Equipment"}, "Location", LocalDateTime.now(), null, 1);
        ShowVO show2 = new ShowVO(1, "Show", new String[]{"Equipment"}, "Location", LocalDateTime.now(), Duration.ofHours(2), 1);

        assertNotEquals(show1, show2);
    }

    @Test
    void testEqualsWithDifferentRuntimeValues() {
        ShowVO show1 = new ShowVO(1, "Show", new String[]{"Equipment"}, "Location", LocalDateTime.now(), Duration.ofHours(2), 1);
        ShowVO show2 = new ShowVO(1, "Show", new String[]{"Equipment"}, "Location", LocalDateTime.now(), Duration.ofHours(3), 1);

        assertNotEquals(show1, show2);
    }


    @Test
    void testEqualsWithOneNullRuntimeValues() {
        ShowVO show1 = new ShowVO(1, "Show", new String[]{"Equipment"}, "Location", null, Duration.ofHours(2), 1);
        ShowVO show2 = new ShowVO(1, "Show", new String[]{"Equipment"}, "Location", LocalDateTime.now(), Duration.ofHours(3), 1);

        assertFalse(show1.equals(show2));
    }

    @Test
    public void testDifferentClassComparison() {
        LocalDateTime fixedDate1 = LocalDateTime.of(2023, 9, 22, 12, 0);

        EventVO event1 = new PartyVO(1, "Event1", new String[]{"DJ-Set", "Lichter"}, "Club", fixedDate1, "Catering-Service", "DJ Max");
        EventVO show2 = new ShowVO(1, "Event1", new String[]{"Equipment"}, "Location", fixedDate1, Duration.ofHours(3), 1);

        assertFalse(event1.equals(show2));
    }


}