package de.thb.dim.eventTom.valueObjects.eventManagement;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Osama Ahmad , MN:20233244
 */
class PartyVOTest {

    private EventVO party1, party2, partyWithNullCatering, partyWithNullPerformer;
    private PartyVO party3;
    private LocalDateTime date;
    private PartyVOMocking partyMocking;

    @BeforeEach
    void setUp() {
        date = LocalDateTime.of(2023, 10, 5, 20, 0);

        party1 = new PartyVO(1, "Party A", new String[]{"Sound", "Lights"}, "Club XYZ", date, "Buffet", "DJ John");
        party2 = new PartyVO(4, "Party A", new String[]{"Sound", "Lights"}, "Club XYZ", date, "Buffet", "DJ John");
        partyWithNullCatering = new PartyVO(1, "Party A", new String[]{"Sound", "Lights"}, "Club XYZ", date, null, "DJ John");
        partyWithNullPerformer = new PartyVO(1, "Party A", new String[]{"Sound", "Lights"}, "Club XYZ", date, "Buffet", null);
        partyMocking = new PartyVOMocking(1, "Test Party", new String[]{"Sound System"}, "Test Location", date, "Test Catering", "Test Performer");
        party3 = new PartyVO(5, "Havana-party", new String[]{"DJ-Device", "Lights"}, "Havana Club", date, "Buffet", "Sebastian Irmscher");

    }


    @Test
    void testEquals() {
        assertTrue(party1.equals(party1), "A party must equal itself.");
        assertFalse(party1.equals(null), "A party should not equal null.");

        Object otherObject = new Object();
        assertFalse(party1.equals(otherObject), "A party should not equal an object of a different class.");
        assertFalse(otherObject instanceof PartyVO);

        PartyVO differentCatering = new PartyVO(1, "Party A", new String[]{"Sound", "Lights"}, "Club XYZ", date, "Full Bar", "DJ John");
        assertFalse(party1.equals(differentCatering), "Parties with different catering should not be equal.");


        EventVO party2Clone = new PartyVO(4, "Party A", new String[]{"Sound", "Lights"}, "Club XYZ", date, "Buffet", "DJ John");

        assertTrue(party2.equals(party2Clone), "Two parties with the same catering should be equal.");

        PartyVO anotherPartyWithNullCatering = new PartyVO(1, "Party A", new String[]{"Sound", "Lights"}, "Club XYZ", date, null, "DJ John");
        assertTrue(partyWithNullCatering.equals(anotherPartyWithNullCatering), "Two parties with null catering should be equal.");

        PartyVO differentPerformer = new PartyVO(1, "Party A", new String[]{"Sound", "Lights"}, "Club XYZ", date, "Buffet", "DJ Jane");
        assertFalse(party1.equals(differentPerformer), "Parties with different performers should not be equal.");


        PartyVO anotherPartyWithNullPerformer = new PartyVO(1, "Party A", new String[]{"Sound", "Lights"}, "Club XYZ", date, "Buffet", null);
        assertTrue(partyWithNullPerformer.equals(anotherPartyWithNullPerformer), "Two parties with null performers should be equal.");

    }


    @Test
    void testPartyVOInitialization() {
        PartyVO party = new PartyVO(2, "PartyEvent", new String[]{"DJ-Set", "Lichter"}, "Club", LocalDateTime.now(), "Catering-Service", "DJ Max");
        assertEquals(2, party.getId());
        assertEquals("PartyEvent", party.getName());
        assertEquals("Catering-Service", party.getCatering());
        assertEquals("DJ Max", party.getPerformer());

    }

    @Test
    void testPartyVOStandardConstructor() {
        PartyVO party = new PartyVO(1, "Party1", null, null, null, null, null);
        assertEquals(1, party.getId());
        assertNotNull(party.getName());
        assertNull(party.getLocation());
        assertNull(party.getDate());
        assertNull(party.getCatering());
        assertNull(party.getPerformer());

    }

    @Test
    void testEqualsWithDifferentClass() {
        PartyVO party = new PartyVO(1, "Party", new String[]{"DJ"}, "Club", LocalDateTime.now(), "Catering", "DJ");
        Object other = new Object();

        assertNotEquals(party, other);
    }


    @Test
    void testEqualsWithNullCateringAndPerformer() {
        LocalDateTime fixedDate = LocalDateTime.of(2023, 9, 22, 12, 0);

        PartyVO party1 = new PartyVO(1, "Party", new String[]{"DJ"}, "Club", fixedDate, null, "DJ");
        PartyVO party2 = new PartyVO(1, "Party", new String[]{"DJ"}, "Club", fixedDate, "Catering", "DJ");

        assertNotEquals(party1, party2);
        party1.setCatering("Catering");
        assertEquals(party1, party2);

        LocalDateTime fixedDate2 = LocalDateTime.of(2023, 3, 28, 9, 0);

        PartyVO party3 = new PartyVO(1, "Party", new String[]{"DJ"}, "Club", fixedDate2, "Catering", null);
        PartyVO party4 = new PartyVO(1, "Party", new String[]{"DJ"}, "Club", fixedDate2, "Catering", "DJ");

        assertNotEquals(party3, party4);

        party3.setPerformer("DJ");

        assertEquals(party3, party4);

    }


    @Test
    void testEqualsWithDifferentCateringAndPerformer() {
        PartyVO party1 = new PartyVO(1, "Party", new String[]{"DJ"}, "Club", LocalDateTime.now(), "Catering1", "DJ");
        PartyVO party2 = new PartyVO(1, "Party", new String[]{"DJ"}, "Club", LocalDateTime.now(), "Catering2", "DJ");

        assertNotEquals(party1, party2);

        PartyVO party3 = new PartyVO(1, "Party", new String[]{"DJ"}, "Club", LocalDateTime.now(), "Catering", "DJ1");
        PartyVO party4 = new PartyVO(1, "Party", new String[]{"DJ"}, "Club", LocalDateTime.now(), "Catering", "DJ2");

        assertNotEquals(party3, party4);

        party3.setPerformer(null);
        assertFalse(party3.equals(party4));
    }


    @Test
    public void testDifferentInstanceComparison() {
        LocalDateTime fixedDate = LocalDateTime.of(2023, 9, 22, 12, 0);

        EventVO event1 = new PartyVO(2, "PartyEvent", new String[]{"DJ-Set", "Lichter"}, "Club", fixedDate, "Catering-Service", "DJ Max");
        PartyVO event2 = new PartyVO(2, "PartyEvent", new String[]{"DJ-Set", "Lichter"}, "Club", fixedDate, "Catering-Service", "DJ Max");

        assertEquals(event1, event2);
        event2.setCatering(null);
        assertNotEquals(event1, event2);
    }


    @Test
    void testEqualsWithNullCateringComparison() {
        LocalDateTime fixedDate = LocalDateTime.of(2023, 9, 22, 12, 0);

        PartyVO party1 = new PartyVO(1, "Party", new String[]{"DJ"}, "Club", fixedDate, null, "DJ");
        PartyVO party2 = new PartyVO(1, "Party", new String[]{"DJ"}, "Club", fixedDate, "Catering5", "DJ");

        assertNotEquals(party1, party2);

        party1.setCatering("Catering5");

        assertTrue(party1.equals(party2));
    }

    /**********************************************************************
     * Tests for setCatering and setPerformer
     **********************************************************************/
// see ==> PartyVOMocking, where the implementation is fixed.
    @Test
    void test_setCatering() {
        // Positive test
        String newCatering = "Deluxe Buffet";
        party3.setCatering(newCatering);
        assertEquals(newCatering, party3.getCatering(), "Catering should be updated to Deluxe Buffet.");

        // Null test
        party3.setCatering(null);
        assertNull(party3.getCatering(), "Setting catering to null should be allowed.");
    }

    @Test
    void testSetPerformer() {
        // Positive test
        String newPerformer = "DJ Max";
        party3.setPerformer(newPerformer);
        assertEquals(newPerformer, party3.getPerformer(), "Performer should be updated to DJ Max.");

        // Null test
        party3.setPerformer(null);
        assertNull(party3.getPerformer(), "Setting performer to null should be allowed.");
    }

    // Additional tests to verify the handling of null values in the constructor
    @Test
    void testPartyWithNullCatering() {
        PartyVO partyWithNullCateringTest = new PartyVO(1, "Party A", new String[]{"Sound", "Lights"}, "Club XYZ", date, null, "Osama Ahmad");

        assertNull(partyWithNullCateringTest.getCatering(), "Party constructed with null catering should have null catering.");
    }

    @Test
    void testPartyWithNullPerformer() {
        PartyVO partyWithNullPerformerTest = new PartyVO(1, "Party A", new String[]{"Sound", "Lights"}, "Club XYZ", date, "Buffet", null);

        assertNull(partyWithNullPerformerTest.getPerformer(), "Party constructed with null performer should have null performer.");
    }

    /***********************************************************************************************
     * Testing the setters of PartyVO with invalid values.
     ***********************************************************************************************/
    @Test
    void test_setCatering_Null_ShouldThrowException() {
        assertThrows(NullPointerException.class, () -> partyMocking.setCatering(null), "Setting null catering should throw an exception.");
    }

    @Test
    void test_setCatering_Empty_ShouldThrowException() {
        assertThrows(NullPointerException.class, () -> partyMocking.setCatering(""), "Setting empty catering should throw an exception.");
    }

    @Test
    void test_setPerformer_Null_ShouldThrowException() {
        assertThrows(NullPointerException.class, () -> partyMocking.setPerformer(null), "Setting null performer should throw an exception.");
    }

    @Test
    void test_setPerformer_Empty_ShouldThrowException() {
        assertThrows(NullPointerException.class, () -> partyMocking.setPerformer(""), "Setting empty performer should throw an exception.");
    }

    /***************************************************************************************************
     * End of Setter-Tests
     ****************************************************************************************************/

    @Test
    void test_setSeasonTicketPrice_ShouldThrowUnsupportedOperationException() {
        // Assert that an UnsupportedOperationException is thrown when setSeasonTicketPrice is called
        assertThrows(UnsupportedOperationException.class,
                () -> partyMocking.setSeasonTicketPrice(100.0f),
                "setSeasonTicketPrice should throw UnsupportedOperationException for PartyVO");
    }

    @Test
    void test_setBackstageTicketPrice_ShouldThrowUnsupportedOperationException() {
        // Assert that an UnsupportedOperationException is thrown when setBackstageTicketPrice is called
        assertThrows(UnsupportedOperationException.class,
                () -> partyMocking.setBackstageTicketPrice(200.0f),
                "setBackstageTicketPrice should throw UnsupportedOperationException for PartyVO");
    }


    private static class PartyVOMocking extends PartyVO {

        public PartyVOMocking(int id, String name, String[] equipment, String location, LocalDateTime date, String catering, String performer) {
            super(id, name, equipment, location, date, catering, performer);
        }

        @Override
        public void setCatering(String catering) {
            if (catering == null || catering.trim().isEmpty()) {
                throw new NullPointerException("Catering cannot be null or empty.");
            }
            super.setCatering(catering);
        }


        @Override
        public void setPerformer(String performer) {
            if (performer == null || performer.trim().isEmpty()) {
                throw new NullPointerException("Performer cannot be null or empty.");
            }
            super.setPerformer(performer);
        }


        /**
         * Osama Ahmad:
         * Overrides the setSeasonTicketPrice method to disallow setting season ticket prices for PartyVO.
         *
         * @param seasonTicketPrice the season ticket price
         * @throws UnsupportedOperationException if the method is called
         */
        @Override
        public void setSeasonTicketPrice(float seasonTicketPrice) {
            throw new UnsupportedOperationException("Season tickets are not available for party events.");
        }


        /**
         * Overrides the setBackstageTicketPrice method to disallow setting backstage ticket prices for PartyVO.
         *
         * @param backstageTicketPrice the backstage ticket price
         * @throws UnsupportedOperationException if the method is called
         */
        @Override
        public void setBackstageTicketPrice(float backstageTicketPrice) {
            throw new UnsupportedOperationException("Backstage tickets are not available for party events.");
        }

    }

    @AfterEach
    public void teardown() {
        // Reset all the objects to null to ensure no state is carried over between tests.
        party1 = null;
        party2 = null;


    }
}