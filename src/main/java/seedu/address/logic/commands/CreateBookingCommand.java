package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BOOKER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BOOKINGEND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BOOKINGSTART;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.booking.Booking;
import seedu.address.model.person.Person;
import seedu.address.model.venue.Venue;

/**
 * Adds a person to the address book.
 */
public class CreateBookingCommand extends Command {

    public static final String COMMAND_WORD = "create_booking";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a booking to the address book. "
            + "Parameters: "
            + PREFIX_BOOKER + "BOOKER "
            + PREFIX_VENUE + "VENUE "
            + PREFIX_DESCRIPTION + "DATETIME "
            + PREFIX_BOOKINGSTART + "DATETIME "
            + PREFIX_BOOKINGEND + "DATETIME\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_BOOKER + "John Doe "
            + PREFIX_VENUE + "UTOWN Hall 2 "
            + PREFIX_DESCRIPTION + "NA "
            + PREFIX_BOOKINGSTART + "2012-01-31 22:59:59 "
            + PREFIX_BOOKINGEND + "2012-01-31 23:59:59";

    public static final String MESSAGE_SUCCESS = "New booking added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This booking already exists in the address book.";
    public static final String MESSAGE_INVALID_TIME =
            "This booking's starting time is not earlier than the ending time.";
    public static final String MESSAGE_INVALID_VENUE = "This venue does not exist in the system.";
    public static final String MESSAGE_INVALID_PERSON = "This booker does not exist in the system.";
    private final Booking toAdd;
    private final Venue venueInBooking;
    private final Person personInBooking;

    /**
     * Creates an CreateBookingCommand to add the specified {@code Booking}
     */
    public CreateBookingCommand(Booking booking) {
        requireNonNull(booking);
        toAdd = booking;
        venueInBooking = booking.getVenue();
        personInBooking = booking.getBooker();
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasBooking(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        if (!toAdd.isValidTime()) {
            throw new CommandException(MESSAGE_INVALID_TIME);
        }

        if (!model.hasVenue(venueInBooking)) {
            throw new CommandException(MESSAGE_INVALID_VENUE);
        }

        if (!model.hasPerson(personInBooking)) {
            throw new CommandException(MESSAGE_INVALID_PERSON);
        }

        model.addBooking(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CreateBookingCommand // instanceof handles nulls
                && toAdd.equals(((CreateBookingCommand) other).toAdd));
    }
}
