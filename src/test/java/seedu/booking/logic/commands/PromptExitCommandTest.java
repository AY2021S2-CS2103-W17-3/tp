package seedu.booking.logic.commands;

import static seedu.booking.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.booking.logic.commands.states.AddBookingCommandState.STATE_START;
import static seedu.booking.testutil.TypicalPersons.getTypicalBookingSystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.booking.logic.commands.states.AddBookingCommandState;
import seedu.booking.logic.commands.states.CommandState;
import seedu.booking.model.ModelManager;
import seedu.booking.model.UserPrefs;

public class PromptExitCommandTest {
    private ModelManager model;
    private ModelManager expectedModel;

    @BeforeEach
    void setup() {
        model = new ModelManager(getTypicalBookingSystem(), new UserPrefs());
        expectedModel = new ModelManager(model.getBookingSystem(), new UserPrefs());

        CommandState commandState = new AddBookingCommandState();
        ModelManager.setCommandState(commandState);
        ModelManager.setStateActive();
        ModelManager.setState(STATE_START);
    }

    @Test
    void execute() {
        PromptExitCommand command = new PromptExitCommand();
        assertCommandSuccess(command, model, "Prompting exited.", expectedModel);
    }
}
