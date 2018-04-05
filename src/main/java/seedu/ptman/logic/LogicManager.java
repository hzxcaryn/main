package seedu.ptman.logic;

import static seedu.ptman.logic.parser.CliSyntax.PREFIX_PASSWORD;

import java.util.ArrayList;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.ptman.commons.core.ComponentManager;
import seedu.ptman.commons.core.LogsCenter;
import seedu.ptman.logic.commands.Command;
import seedu.ptman.logic.commands.CommandResult;
import seedu.ptman.logic.commands.exceptions.CommandException;
import seedu.ptman.logic.commands.util.CommandListUtil;
import seedu.ptman.logic.parser.PartTimeManagerParser;
import seedu.ptman.logic.parser.exceptions.ParseException;
import seedu.ptman.model.Model;
import seedu.ptman.model.employee.Employee;
import seedu.ptman.model.outlet.OutletInformation;
import seedu.ptman.model.shift.Shift;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final CommandHistory history;
    private final PartTimeManagerParser partTimeManagerParser;
    private final UndoRedoStack undoRedoStack;

    public LogicManager(Model model) {
        this.model = model;
        history = new CommandHistory();
        partTimeManagerParser = new PartTimeManagerParser();
        undoRedoStack = new UndoRedoStack();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        try {
            Command command = partTimeManagerParser.parseCommand(commandText);
            command.setData(model, history, undoRedoStack);
            CommandResult result = command.execute();
            undoRedoStack.push(command);
            return result;
        } finally {
            history.add(processInput(commandText));
        }
    }

    //@@author koo1993
    /**
     * Obscure sensitive information like password by replacing it with "a space"
     * @param input
     * @return the processed input
     */
    private String processInput(String input) {
        StringBuilder newString = new StringBuilder(input);
        int indexOfPrefix = newString.indexOf(PREFIX_PASSWORD.getPrefix());
        int indexOfSpace = newString.indexOf(" ", indexOfPrefix);
        while (indexOfPrefix >= 0) {
            if (indexOfSpace == -1) {
                indexOfSpace = newString.length();
            }
            newString.replace(indexOfPrefix + 3 , indexOfSpace, " ");

            indexOfPrefix = newString.indexOf(PREFIX_PASSWORD.getPrefix(), indexOfPrefix + 3);
            indexOfSpace = newString.indexOf(" ", indexOfPrefix);

        }
        return newString.toString();
    }

    //@@author hzxcaryn
    @Override
    public ObservableList<String> getCommandList() {
        ArrayList<String> commandList = CommandListUtil.getAllCommands();
        ObservableList<String> observableCommandList = FXCollections.observableList(commandList);
        return FXCollections.unmodifiableObservableList(observableCommandList);
    }

    //@@author
    @Override
    public ObservableList<Employee> getFilteredEmployeeList() {
        return model.getFilteredEmployeeList();
    }

    @Override
    public ObservableList<Shift> getFilteredShiftList() {
        return model.getFilteredShiftList();
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }

    @Override
    public OutletInformation getOutletInformation() {
        return model.getOutletInformation();
    }

    @Override
    public boolean isAdminMode() {
        return model.isAdminMode();
    }
}
