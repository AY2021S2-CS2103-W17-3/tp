package seedu.booking.model.booking;

import static seedu.booking.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

import seedu.booking.model.Tag;
import seedu.booking.model.person.Email;
import seedu.booking.model.venue.VenueName;

/**
 * Represents a booking in the booking list.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Booking {
    // Data fields
    private Email bookerEmail;
    private VenueName venueName;
    private final Description description;
    private final StartTime bookingStart;
    private final EndTime bookingEnd;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Booking(Email bookerEmail, VenueName venueName, Description description,
                   StartTime bookingStart, EndTime bookingEnd, Set<Tag> tags) {
        requireAllNonNull(bookerEmail, venueName, description, bookingStart, bookingEnd, tags);
        this.bookerEmail = bookerEmail;
        this.venueName = venueName;
        this.description = description;
        this.bookingStart = bookingStart;
        this.bookingEnd = bookingEnd;
        this.tags.addAll(tags);
    }



    public Email getBookerEmail() {
        return bookerEmail;
    }

    public VenueName getVenueName() {
        return venueName;
    }

    public Description getDescription() {
        return description;
    }

    public StartTime getBookingStart() {
        return bookingStart;
    }

    public EndTime getBookingEnd() {
        return bookingEnd;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }



    /**
     * Returns true if both bookings overlap.
     * This can be used to test for booking conflicts.
     */
    public boolean isOverlapping(Booking otherBooking) {
        if (otherBooking == this) {
            return true;
        }
        if (otherBooking == null) {
            return false;
        }
        if (otherBooking.getVenueName().equals(this.venueName)) {
            return this.bookingStart.value.isBefore(otherBooking.bookingEnd.value)
                    && otherBooking.bookingStart.value.isBefore(this.bookingEnd.value);
        } else {
            return false;
        }

    }


    /**
     * Returns true if both bookings have the same data fields.
     * This notion of equality between two bookings.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Booking)) {
            return false;
        }

        Booking otherBooking = (Booking) other;
        return otherBooking.getBookerEmail().equals(getBookerEmail())
                && otherBooking.getVenueName().equals(getVenueName())
                && otherBooking.getDescription().equals(getDescription())
                && otherBooking.getBookingStart().equals(getBookingStart())
                && otherBooking.getBookingEnd().equals(getBookingEnd())
                && otherBooking.getTags().equals(getTags());
    }

    /**
     * Returns true if the start time is earlier than the end time.
     */
    public boolean isValidTime() {
        return this.bookingStart.value.compareTo(this.bookingEnd.value) < 0;
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(bookerEmail, venueName, description, bookingStart, bookingEnd);
    }


    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Booker: ")
                .append(getBookerEmail())
                .append("; Venue: ")
                .append(getVenueName())
                .append("; Description: ")
                .append(getDescription())
                .append("; Start of booking: ")
                .append(getBookingStart())
                .append("; End of booking: ")
                .append(getBookingEnd());

        Set<Tag> tags = getTags();
        if (!tags.isEmpty()) {
            builder.append("; Tags: ");
            tags.forEach(builder::append);
        }
        return builder.toString();
    }

    /**
     * Returns true if both bookings have the same id.
     * This defines a weaker notion of equality between two bookings.
     */
    public boolean isSameBooking(Booking otherBooking) {
        if (otherBooking == this) {
            return true;
        }

        return otherBooking != null
                && otherBooking.equals(this);
    }

    /**
     * Returns true if both bookings have same fields.
     * This defines a weaker notion of equality between two bookings.
     */
    public boolean isExactlySameBooking(Booking otherBooking) {
        return otherBooking.getBookerEmail().equals(getBookerEmail())
                && otherBooking.getVenueName().equals(getVenueName())
                && otherBooking.getBookingStart().equals(getBookingStart())
                && otherBooking.getBookingEnd().equals(getBookingEnd())
                && otherBooking.getDescription().equals(getDescription())
                && otherBooking.getTags().equals(getTags());
    }

    public void setVenueName(VenueName venueName) {
        this.venueName = venueName;
    }

    public void setEmail(Email newEmail) {
        this.bookerEmail = newEmail;
    }
}
