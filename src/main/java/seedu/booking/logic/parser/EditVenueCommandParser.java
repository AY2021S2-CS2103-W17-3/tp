package seedu.booking.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.booking.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.booking.logic.parser.CliSyntax.PREFIX_CAPACITY;
import static seedu.booking.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.booking.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.booking.logic.parser.CliSyntax.PREFIX_VENUE;
import static seedu.booking.logic.parser.CliSyntax.PREFIX_VENUE_ORIGINAL;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import seedu.booking.logic.commands.EditVenueCommand;
import seedu.booking.logic.commands.EditVenueCommand.EditVenueDescriptor;
import seedu.booking.logic.parser.exceptions.ParseException;
import seedu.booking.model.Tag;
import seedu.booking.model.venue.VenueName;

/**
 * Parses input arguments and creates a new EditVenueCommand object.
 */
public class EditVenueCommandParser implements Parser<EditVenueCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditVenueCommand
     * and returns an EditVenueCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public EditVenueCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_VENUE_ORIGINAL, PREFIX_VENUE,
                PREFIX_CAPACITY, PREFIX_DESCRIPTION, PREFIX_TAG);

        VenueName venueName;

        if (!arePrefixesPresent(argMultimap, PREFIX_VENUE_ORIGINAL)
                || argMultimap.getValue(PREFIX_VENUE_ORIGINAL).isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditVenueCommand.MESSAGE_USAGE));
        }

        try {
            venueName = ParserUtil.parseVenueName(argMultimap.getValue(PREFIX_VENUE_ORIGINAL).get());
        } catch (ParseException pe) {
            throw new ParseException(VenueName.MESSAGE_CONSTRAINTS);
        }

        EditVenueDescriptor editVenueDescriptor = new EditVenueDescriptor();
        if (argMultimap.getValue(PREFIX_VENUE).isPresent()) {
            editVenueDescriptor.setVenueName(ParserUtil
                    .parseVenueName(argMultimap.getValue(PREFIX_VENUE).get()));
        }

        if (argMultimap.getValue(PREFIX_CAPACITY).isPresent()) {
            editVenueDescriptor.setCapacity(ParserUtil
                    .parseCapacity(argMultimap.getValue(PREFIX_CAPACITY).get()));
        }

        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            editVenueDescriptor.setDescription(ParserUtil
                    .parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get()));
        }

        if (argMultimap.getValue(PREFIX_TAG).isPresent()) {
            editVenueDescriptor.setTags(getTagSet(argMultimap.getValue(PREFIX_TAG).get()));
        }

        if (!editVenueDescriptor.isAnyFieldEdited()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditVenueCommand.MESSAGE_USAGE));
        }

        return new EditVenueCommand(venueName, editVenueDescriptor);
    }

    private Set<Tag> getTagSet(String input) throws ParseException {
        final Set<Tag> tagSet = new HashSet<>();
        if (input.trim().equals("")) {
            return tagSet;
        }
        String[] tags = input.split(",");
        for (String tag : tags) {
            if (!Tag.isValidTagName(tag.trim())) {
                throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
            }
            tagSet.add(new Tag(tag.trim()));
        }
        return tagSet;
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
