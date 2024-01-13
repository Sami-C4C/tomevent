package de.thb.dim.eventTom.valueObjects.eventManagement;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Osama Ahmad , MN:20233244
 */
class ShowVOTest {

    private ShowVOMocking showMock;
    private EventVO show1, showWithNullRuntime;
    private ShowVO show2;
    private LocalDateTime date;
    private Duration runtime;

    @BeforeEach
    void setUp() {
        date = LocalDateTime.of(2023, 10, 5, 20, 0);
        runtime = Duration.ofHours(2);

        show1 = new ShowVO(1, "Show A", new String[]{"Sound", "Lights"}, "Theatre", date, runtime, 100);
        show2 = new ShowVO(1, "Show B", new String[]{"Sound", "Lights"}, "Theatre", date, runtime, 100);
        showMock = new ShowVOMocking(1, "Show", new String[]{"Lights", "Sound"}, "Theatre", LocalDateTime.now(), Duration.ofHours(5), 100);
        showWithNullRuntime = new ShowVO(1, "Show A", new String[]{"Sound", "Lights"}, "Theatre", date, null, 100);
    }


    @Test
    public void testEquals() {
        assertTrue(show1.equals(show1), "A show must equal itself.");
        assertFalse(show1.equals(null), "A show should not equal null.");
        Object otherObject = new Object();
        assertFalse(show1.equals(otherObject), "A show should not equal an object of a different class.");
        ShowVO showDifferentRuntime = new ShowVO(1, "Show A", new String[]{"Sound", "Lights"}, "Theatre", date, Duration.ofHours(3), 100);
        assertFalse(show1.equals(showDifferentRuntime), "Shows with different runtimes should not be equal.");

        ShowVO show1Clone = new ShowVO(1, "Show A", new String[]{"Sound", "Lights"}, "Theatre", date, runtime, 100);

        assertTrue(show1.equals(show1Clone), "Two shows with the same runtime should be equal.");

        ShowVO anotherShowWithNullRuntime = new ShowVO(1, "Show A", new String[]{"Sound", "Lights"}, "Theatre", date, null, 100);
        assertTrue(showWithNullRuntime.equals(anotherShowWithNullRuntime), "Two shows with null runtimes should be equal.");

        assertFalse(show1.equals(showWithNullRuntime) && showDifferentRuntime.equals(show1), "A show with a runtime should not equal a show with a null runtime.");

    }

    //Test for getClass() != obj.getClass()
    @Test
    void testEqualsWithDifferentClasses() {
        // Create a subclass of ShowVO to simulate a different class comparison.
        class SubShowVO extends ShowVO {
            public SubShowVO(int id, String name, String[] equipment, String location, LocalDateTime date, Duration runtime, int anzCategory) {
                super(id, name, equipment, location, date, runtime, anzCategory);
            }
        }

        EventVO event = new EventVO(1, "Concert", new String[]{"Microphone", "Speakers"}, "City Hall", date, 2) {
        }; // Using an anonymous subclass because EventVO is abstract

        // Create an instance of the subclass.
        ShowVO subShowVO = new SubShowVO(1, "Show A", new String[]{"Sound", "Lights"}, "Theatre", date, runtime, 100);

        // Assert that show1 does not equal an object of a different class.
        assertFalse(show1.equals(subShowVO), "ShowVO should not be equal to an object of a subclass.");
        assertFalse(show1.equals(event), "ShowVO should not be equal to an object of a subclass.");

    }

    //Test for if (other.runtime != null)
    @Test
    void testEqualsWithNullRuntimeInOneAndNonNullInOther() {
        // Create a show with null runtime
        ShowVO showWithNullRuntime = new ShowVO(1, "Show A", new String[]{"Sound", "Lights"}, "Theatre", date, null, 100);

        // Assert that a show with a runtime does not equal a show with a null runtime.
        assertFalse(show1.equals(showWithNullRuntime), "A show with a runtime should not equal a show with a null runtime.");
        assertFalse(showWithNullRuntime.equals(show1), "A show with a null runtime should not equal a show with a runtime.");
    }


    @Test
    void testEqualsDifferentClasses() {
        class SubShowVO extends ShowVO {
            SubShowVO(int id, String name, String[] equipment, String location, LocalDateTime date, Duration runtime, int anzCategory) {
                super(id, name, equipment, location, date, runtime, anzCategory);
            }
        }

        ShowVO subShow = new SubShowVO(1, "Show A", new String[]{"Sound", "Lights"}, "Theatre", date, runtime, 100);
        assertFalse(show1.equals(subShow), "ShowVO should not be equal to a subclass instance.");
    }


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



    @Test
    void testEqualsWithDifferentClass() {
        // Create a subclass of ShowVO
        class ShowVOSubclass extends ShowVO {
            public ShowVOSubclass(int id, String name, String[] equipment, String location, LocalDateTime date, Duration runtime, int anzCategory) {
                super(id, name, equipment, location, date, runtime, anzCategory);
            }
        }

        // Create an instance of the subclass
        ShowVO subShow = new ShowVOSubclass(1, "Show A", new String[]{"Sound", "Lights"}, "Theatre", date, runtime, 100);

        // Assert that show does not equal an object of its subclass
        assertFalse(subShow.equals(show2), "ShowVO instance should not be equal to an instance of its subclass.");
    }

    @Test
    void testEqualsWithDifferentNonSubclass() {
        // Create an instance of a different class
        Object otherObject = new Object();

        // Assert that show does not equal an object of a different class
        assertFalse(show2.equals(otherObject), "ShowVO instance should not be equal to an instance of a different class.");
    }

    /*******************************************************************************
     * Tests for setRuntime
     *******************************************************************************/
    // After Fixing the implementation od setRuntime, invalid values are no more accepted.
    @Test
    void testSetRuntime() {
        // Positive test: setting a valid runtime
        Duration newRuntime = Duration.ofHours(3);
        show2.setRuntime(newRuntime);
        assertEquals(newRuntime, show2.getRuntime(), "Runtime should be updated to 3 hours.");

        // Negative test: setting a negative runtime
        Duration negativeRuntime = Duration.ofHours(-3);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> show2.setRuntime(negativeRuntime));
        assertEquals("Runtime must not be negative", exception.getMessage(), "Negative runtime should throw IllegalArgumentException.");

        // Null test: setting runtime to null
        show2.setRuntime(null);
        assertNull(show2.getRuntime(), "Setting runtime to null should be allowed.");

        // Boundary tests
        // Test boundary: zero duration
        show2.setRuntime(Duration.ZERO);
        assertEquals(Duration.ZERO, show2.getRuntime(), "Setting runtime to zero should be allowed.");

        // Test boundary: smallest positive duration
        show2.setRuntime(Duration.ofNanos(1));
        assertEquals(Duration.ofNanos(1), show2.getRuntime(), "Setting runtime to the smallest positive duration should be allowed.");
    }


    /*****************************************************************************************
     * Tests for Setters into ShowVO with invalid values.
     *****************************************************************************************/
    @Test
    void setRuntime_NullValue_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> showMock.setRuntime(null), "Setting null runtime should throw IllegalArgumentException.");
    }

    @Test
    void setRuntime_NegativeValue_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> showMock.setRuntime(Duration.ofHours(-1)), "Setting negative runtime should throw IllegalArgumentException.");
    }

    @Test
    void setNrAvailableTickets_NegativeValue_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> showMock.setNrAvailableTickets(-1), "Setting negative number of available tickets should throw IllegalArgumentException.");
    }


    private class ShowVOMocking extends ShowVO {

        public ShowVOMocking(int id, String name, String[] equipment, String location, LocalDateTime date, Duration runtime, int anzCategory) {
            super(id, name, equipment, location, date, runtime, anzCategory);
        }

        @Override
        public void setRuntime(Duration runtime) {
            if (runtime == null || runtime.isNegative()) {
                throw new IllegalArgumentException("Runtime must be positive and non-null.");
            }
            super.setRuntime(runtime);
        }

        @Override
        public void setNrAvailableTickets(int nrAvailableTickets) {
            if (nrAvailableTickets < 0) {
                throw new IllegalArgumentException("Number of available tickets must be non-negative.");
            }
            super.setNrAvailableTickets(nrAvailableTickets);
        }
    }



    @AfterEach
    public void teardown() {
        // Reset all the objects to null to ensure no state is carried over between tests
        show1 = null;
        show2 = null;

    }

}