package seedu.booking.model;

import java.util.Locale;

import static java.util.Objects.requireNonNull;
import static seedu.booking.commons.util.AppUtil.checkArgument;

/**
 * Represents a venueTag in the booking system.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class Tag {

    public static final String MESSAGE_CONSTRAINTS =
            "Tag names should be alphanumeric without spaces.\n"
            + "Consecutive commas are not allowed without tags in between.\n";
    public static final String TAG_NON_EMPTY = "Tag field should not be empty.\n"
            + "To search for multiple tags, separate them with commas.\n";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";

    public final String tagName;
    public final String tagNameLowerCase;

    /**
     * Constructs a {@code Tag}.
     *
     * @param tagName A valid tag name.
     */
    public Tag(String tagName) {
        requireNonNull(tagName);
        checkArgument(isValidTagName(tagName), MESSAGE_CONSTRAINTS);
        this.tagName = tagName;
        this.tagNameLowerCase = tagName.toLowerCase();
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTagName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Tag // instanceof handles nulls
                && tagNameLowerCase.equals(((Tag) other).tagNameLowerCase)); // state check
    }

    @Override
    public int hashCode() {
        return tagNameLowerCase.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + tagName + ']';
    }

}
