package seedu.booking.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.booking.commons.core.Messages;
import seedu.booking.logic.commands.exceptions.CommandException;
import seedu.booking.model.Model;
import seedu.booking.model.booking.Booking;

/**
 * Displays all existing bookings to the terminal
 */
public class ListBookingCommand extends Command {

    public static final String COMMAND_WORD = "list_booking";

    public static final String MESSAGE_BOOKING_LISTED_SUCCESS = "Here are all venues currently in the system:\n";


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Booking> lastShownList = model.getFilteredBookingList();
        final StringBuilder outputString = new StringBuilder();

        if (lastShownList.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_BOOKING_LISTED_EMPTY);
        }

        outputString.append(MESSAGE_BOOKING_LISTED_SUCCESS);

        return new CommandResult(outputString.toString());
    }
}
