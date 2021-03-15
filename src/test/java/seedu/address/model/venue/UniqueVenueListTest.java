package seedu.address.model.venue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_CAPACITY_FIELD;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalVenues.VENUE1;
import static seedu.address.testutil.TypicalVenues.VENUE2;
import static seedu.address.testutil.TypicalVenues.VENUE3;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.venue.exceptions.DuplicateVenueException;
import seedu.address.model.venue.exceptions.VenueNotFoundException;
import seedu.address.testutil.VenueBuilder;

public class UniqueVenueListTest {

    private final UniqueVenueList uniqueVenueList = new UniqueVenueList();

    @Test
    public void contains_nullVenue_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueVenueList.contains(null));
    }

    @Test
    public void contains_venueNotInList_returnsFalse() {
        assertFalse(uniqueVenueList.contains(VENUE1));
    }

    @Test
    public void contains_venueInList_returnsTrue() {
        uniqueVenueList.add(VENUE1);
        assertTrue(uniqueVenueList.contains(VENUE1));
    }

    @Test
    public void contains_venueWithSameIdentityFieldsInList_returnsTrue() {
        uniqueVenueList.add(VENUE1);
        Venue editedVenue1 = new VenueBuilder(VENUE1).withCapacity(VALID_VENUE_CAPACITY_FIELD)
                .build();
        assertTrue(uniqueVenueList.contains(editedVenue1));
    }

    @Test
    public void add_nullVenue_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueVenueList.add(null));
    }

    @Test
    public void add_duplicateVenue_throwsDuplicateVenueException() {
        uniqueVenueList.add(VENUE1);
        assertThrows(DuplicateVenueException.class, () -> uniqueVenueList.add(VENUE1));
    }

    @Test
    public void setVenue_nullTargetVenue_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueVenueList.setVenue(null, VENUE1));
    }

    @Test
    public void setVenue_nullEditedVenue_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueVenueList.setVenue(VENUE1, null));
    }

    @Test
    public void setVenue_targetVenueNotInList_throwsVenueNotFoundException() {
        assertThrows(VenueNotFoundException.class, () -> uniqueVenueList.setVenue(VENUE1, VENUE1));
    }

    @Test
    public void setVenue_editedVenueIsSameVenue_success() {
        uniqueVenueList.add(VENUE1);
        uniqueVenueList.setVenue(VENUE1, VENUE1);
        UniqueVenueList expectedUniqueVenueList = new UniqueVenueList();
        expectedUniqueVenueList.add(VENUE1);
        assertEquals(expectedUniqueVenueList, uniqueVenueList);
    }


    @Test
    public void setVenue_editedVenueHasSameIdentity_success() {
        uniqueVenueList.add(VENUE1);
        Venue editedVenue1 = new VenueBuilder(VENUE1).withCapacity(30).build();
        uniqueVenueList.setVenue(VENUE1, editedVenue1);
        UniqueVenueList expectedUniqueVenueList = new UniqueVenueList();
        expectedUniqueVenueList.add(editedVenue1);
        assertEquals(expectedUniqueVenueList, uniqueVenueList);
    }

    @Test
    public void setVenue_editedVenueHasDifferentIdentity_success() {
        uniqueVenueList.add(VENUE1);
        uniqueVenueList.setVenue(VENUE1, VENUE3);
        UniqueVenueList expectedUniqueVenueList = new UniqueVenueList();
        expectedUniqueVenueList.add(VENUE3);
        assertEquals(expectedUniqueVenueList, uniqueVenueList);
    }


    @Test
    public void setVenue_editedVenueHasNonUniqueIdentity_throwsDuplicateVenueException() {
        uniqueVenueList.add(VENUE1);
        uniqueVenueList.add(VENUE3);
        assertThrows(DuplicateVenueException.class, () -> uniqueVenueList.setVenue(VENUE1, VENUE3));
    }

    @Test
    public void remove_nullVenue_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueVenueList.remove(null));
    }

    @Test
    public void setVenues_nullUniqueVenueList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueVenueList.setVenues((UniqueVenueList) null));
    }

    @Test
    public void setVenues_uniqueVenueList_replacesOwnListWithProvidedUniqueVenueList() {
        uniqueVenueList.add(VENUE1);
        UniqueVenueList expectedUniqueVenueList = new UniqueVenueList();
        expectedUniqueVenueList.add(VENUE2);
        uniqueVenueList.setVenues(expectedUniqueVenueList);
        assertEquals(expectedUniqueVenueList, uniqueVenueList);
    }

    @Test
    public void setVenue_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueVenueList.setVenues((List<Venue>) null));
    }

    @Test
    public void setVenues_list_replacesOwnListWithProvidedList() {
        uniqueVenueList.add(VENUE1);
        List<Venue> venueList = Collections.singletonList(VENUE3);
        uniqueVenueList.setVenues(venueList);
        UniqueVenueList expectedUniqueVenueList = new UniqueVenueList();
        expectedUniqueVenueList.add(VENUE3);
        assertEquals(expectedUniqueVenueList, uniqueVenueList);
    }

    @Test
    public void setVenues_listWithDuplicateVenues_throwsDuplicateVenueException() {
        List<Venue> listWithDuplicateVenues = Arrays.asList(VENUE1, VENUE1);
        assertThrows(DuplicateVenueException.class, () -> uniqueVenueList.setVenues(listWithDuplicateVenues));
    }

    @Test
    public void setVenues_listWithDuplicateVenueNames_throwsDuplicateVenueException() {
        List<Venue> listWithDuplicateVenues = Arrays.asList(VENUE1, VENUE2);
        assertThrows(DuplicateVenueException.class, () -> uniqueVenueList.setVenues(listWithDuplicateVenues));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueVenueList.asUnmodifiableObservableList().remove(0));
    }
}
