package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;

import java.util.Arrays;
import java.util.stream.Stream;

import seedu.address.logic.commands.FindVenueCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.booking.VenueNameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindVenueCommand object.
 */
public class FindVenueCommandParser implements Parser<FindVenueCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindVenueCommand
     * and returns a FindVenueCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public FindVenueCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(trimmedArgs, PREFIX_VENUE);
        if (!arePrefixesPresent(argMultimap, PREFIX_VENUE)
                || !argMultimap.getValue(PREFIX_VENUE).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindVenueCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindVenueCommand(new VenueNameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
