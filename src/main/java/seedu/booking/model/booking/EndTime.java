package seedu.booking.model.booking;

import static java.util.Objects.requireNonNull;
import static seedu.booking.commons.util.AppUtil.checkArgument;

import java.time.LocalDateTime;

/**
 * Represents the end time in the booking system.
 */
public class EndTime {


    public final LocalDateTime value;

    /**
     * Constructs an {@code EndTime}.
     *
     * @param endTime A valid end time.
     */
    public EndTime(LocalDateTime endTime) {
        requireNonNull(endTime);
        value = endTime;
    }


    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EndTime // instanceof handles nulls
                && value.equals(((EndTime) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
