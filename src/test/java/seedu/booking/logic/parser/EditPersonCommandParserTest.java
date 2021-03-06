package seedu.booking.logic.parser;

import static seedu.booking.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.booking.commons.core.Messages.MESSAGE_INVALID_EMAIL_FORMAT;
import static seedu.booking.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.booking.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.booking.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.booking.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.booking.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.booking.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.booking.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.booking.logic.commands.CommandTestUtil.ORIGINAL_EMAIL_DESC_AMY;
import static seedu.booking.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.booking.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.booking.logic.commands.CommandTestUtil.VALID_EMAIL_AMY_GMAIL;
import static seedu.booking.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.booking.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.booking.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.booking.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.booking.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.booking.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.booking.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.booking.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.booking.logic.commands.EditPersonCommand;
import seedu.booking.logic.commands.EditPersonCommand.EditPersonDescriptor;
import seedu.booking.model.person.Email;
import seedu.booking.model.person.Name;
import seedu.booking.model.person.Phone;
import seedu.booking.testutil.EditPersonCommandDescriptorBuilder;

public class EditPersonCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditPersonCommand.MESSAGE_USAGE);

    private EditPersonCommandParser parser = new EditPersonCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no email specified
        assertParseFailure(parser, " " + PREFIX_NAME + VALID_NAME_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditPersonCommand.MESSAGE_USAGE));

        // no field specified
        assertParseFailure(parser, ORIGINAL_EMAIL_DESC_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditPersonCommand.MESSAGE_USAGE));

        // no email and no field specified
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditPersonCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, VALID_NAME_AMY + ORIGINAL_EMAIL_DESC_AMY + INVALID_NAME_DESC,
                Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, VALID_NAME_AMY + ORIGINAL_EMAIL_DESC_AMY + INVALID_PHONE_DESC,
                Phone.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, VALID_NAME_AMY + ORIGINAL_EMAIL_DESC_AMY + INVALID_EMAIL_DESC,
                MESSAGE_INVALID_EMAIL_FORMAT + Email.MESSAGE_CONSTRAINTS); // invalid email

        // invalid phone followed by valid email
        assertParseFailure(parser, VALID_NAME_AMY + ORIGINAL_EMAIL_DESC_AMY + INVALID_PHONE_DESC + EMAIL_DESC_AMY,
                Phone.MESSAGE_CONSTRAINTS);

        // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, VALID_NAME_AMY + ORIGINAL_EMAIL_DESC_AMY + PHONE_DESC_BOB + INVALID_PHONE_DESC,
                Phone.MESSAGE_CONSTRAINTS);


        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, VALID_NAME_AMY + ORIGINAL_EMAIL_DESC_AMY + INVALID_NAME_DESC + INVALID_EMAIL_DESC
                        + VALID_PHONE_AMY, Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        String targetEmail = ORIGINAL_EMAIL_DESC_AMY;
        String userInput = VALID_NAME_AMY + targetEmail + PHONE_DESC_BOB + NAME_DESC_BOB + EMAIL_DESC_BOB;
        EditPersonDescriptor descriptor = new EditPersonCommandDescriptorBuilder()
                 .withPhone(VALID_PHONE_BOB).withName(VALID_NAME_BOB).withEmail(VALID_EMAIL_BOB).build();
        EditPersonCommand expectedCommand = new EditPersonCommand(new Email(VALID_EMAIL_AMY_GMAIL), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        String targetEmail = ORIGINAL_EMAIL_DESC_AMY;
        String userInput = VALID_NAME_AMY + targetEmail + PHONE_DESC_BOB;
        EditPersonDescriptor descriptor = new EditPersonCommandDescriptorBuilder()
                .withPhone(VALID_PHONE_BOB).build();
        EditPersonCommand expectedCommand = new EditPersonCommand(new Email(VALID_EMAIL_AMY_GMAIL), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        String targetEmail = ORIGINAL_EMAIL_DESC_AMY;
        String userInput = VALID_NAME_AMY + targetEmail + NAME_DESC_BOB;
        EditPersonDescriptor descriptor = new EditPersonCommandDescriptorBuilder()
                .withName(VALID_NAME_BOB).build();
        EditPersonCommand expectedCommand = new EditPersonCommand(new Email(VALID_EMAIL_AMY_GMAIL), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = VALID_NAME_AMY + targetEmail + PHONE_DESC_BOB;
        descriptor = new EditPersonCommandDescriptorBuilder()
                .withPhone(VALID_PHONE_BOB).build();
        expectedCommand = new EditPersonCommand(new Email(VALID_EMAIL_AMY_GMAIL), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = VALID_NAME_AMY + targetEmail + EMAIL_DESC_BOB;
        descriptor = new EditPersonCommandDescriptorBuilder()
                .withEmail(VALID_EMAIL_BOB).build();
        expectedCommand = new EditPersonCommand(new Email(VALID_EMAIL_AMY_GMAIL), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);

    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        String targetEmail = ORIGINAL_EMAIL_DESC_AMY;
        String userInput = VALID_NAME_AMY + targetEmail + PHONE_DESC_AMY + EMAIL_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_BOB;
        EditPersonDescriptor descriptor = new EditPersonCommandDescriptorBuilder()
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).build();
        EditPersonCommand expectedCommand = new EditPersonCommand(new Email(VALID_EMAIL_AMY_GMAIL), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        String targetEmail = ORIGINAL_EMAIL_DESC_AMY;
        String userInput = VALID_NAME_AMY + targetEmail + INVALID_PHONE_DESC + PHONE_DESC_BOB;
        EditPersonDescriptor descriptor = new EditPersonCommandDescriptorBuilder()
                .withPhone(VALID_PHONE_BOB).build();
        EditPersonCommand expectedCommand = new EditPersonCommand(new Email(VALID_EMAIL_AMY_GMAIL), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = VALID_NAME_AMY + targetEmail + EMAIL_DESC_BOB + INVALID_PHONE_DESC + PHONE_DESC_BOB;
        descriptor = new EditPersonCommandDescriptorBuilder().withEmail(VALID_EMAIL_BOB)
                .withPhone(VALID_PHONE_BOB).build();
        expectedCommand = new EditPersonCommand(new Email(VALID_EMAIL_AMY_GMAIL), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

}
