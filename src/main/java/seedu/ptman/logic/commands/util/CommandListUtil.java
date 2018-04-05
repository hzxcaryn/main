package seedu.ptman.logic.commands.util;

import java.util.ArrayList;

import seedu.ptman.logic.commands.AddCommand;
import seedu.ptman.logic.commands.AddShiftCommand;
import seedu.ptman.logic.commands.AnnouncementCommand;
import seedu.ptman.logic.commands.ApplyCommand;
import seedu.ptman.logic.commands.ChangeAdminPasswordCommand;
import seedu.ptman.logic.commands.ChangePasswordCommand;
import seedu.ptman.logic.commands.ClearCommand;
import seedu.ptman.logic.commands.DeleteCommand;
import seedu.ptman.logic.commands.DeleteShiftCommand;
import seedu.ptman.logic.commands.EditCommand;
import seedu.ptman.logic.commands.EditOutletCommand;
import seedu.ptman.logic.commands.ExitCommand;
import seedu.ptman.logic.commands.ExportCommand;
import seedu.ptman.logic.commands.FindCommand;
import seedu.ptman.logic.commands.HelpCommand;
import seedu.ptman.logic.commands.HistoryCommand;
import seedu.ptman.logic.commands.ListCommand;
import seedu.ptman.logic.commands.LogInAdminCommand;
import seedu.ptman.logic.commands.LogOutAdminCommand;
import seedu.ptman.logic.commands.MainCommand;
import seedu.ptman.logic.commands.RedoCommand;
import seedu.ptman.logic.commands.ResetAdminPasswordCommand;
import seedu.ptman.logic.commands.ResetPasswordCommand;
import seedu.ptman.logic.commands.SelectCommand;
import seedu.ptman.logic.commands.UnapplyCommand;
import seedu.ptman.logic.commands.UndoCommand;

//@@author hzxcaryn
/**
 * Contains utility methods for getting a list of the commands.
 */
public class CommandListUtil {

    /**
     * @return list of all commands in their respective suggested formats
     */
    public static ArrayList<String> getAllCommands() {
        ArrayList<String> commandList = new ArrayList<>();

        commandList.add(AddCommand.COMMAND_WORD + " " + AddCommand.COMMAND_FORMAT);
        commandList.add(AddShiftCommand.COMMAND_WORD + " " + AddShiftCommand.COMMAND_FORMAT);
        commandList.add(AnnouncementCommand.COMMAND_WORD + " " + AnnouncementCommand.COMMAND_FORMAT);
        commandList.add(ApplyCommand.COMMAND_WORD + " " + ApplyCommand.COMMAND_FORMAT);
        commandList.add(ChangeAdminPasswordCommand.COMMAND_WORD + " " + ChangeAdminPasswordCommand.COMMAND_FORMAT);
        commandList.add(ChangePasswordCommand.COMMAND_WORD + " " + ChangePasswordCommand.COMMAND_FORMAT);
        commandList.add(ClearCommand.COMMAND_WORD);
        commandList.add(DeleteCommand.COMMAND_WORD + " " + DeleteCommand.COMMAND_FORMAT);
        commandList.add(DeleteShiftCommand.COMMAND_WORD + " " + DeleteShiftCommand.COMMAND_FORMAT);
        commandList.add(EditCommand.COMMAND_WORD + " " + EditCommand.COMMAND_FORMAT);
        commandList.add(EditOutletCommand.COMMAND_WORD + " " + EditOutletCommand.COMMAND_FORMAT);
        commandList.add(ExitCommand.COMMAND_WORD);
        commandList.add(ExportCommand.COMMAND_WORD + " " + ExportCommand.COMMAND_FORMAT);
        commandList.add(FindCommand.COMMAND_WORD + " " + FindCommand.COMMAND_FORMAT);
        commandList.add(HelpCommand.COMMAND_WORD);
        commandList.add(HistoryCommand.COMMAND_WORD);
        commandList.add(ListCommand.COMMAND_WORD);
        commandList.add(LogInAdminCommand.COMMAND_WORD + " " + LogInAdminCommand.COMMAND_FORMAT);
        commandList.add(LogOutAdminCommand.COMMAND_WORD);
        commandList.add(MainCommand.COMMAND_WORD);
        commandList.add(RedoCommand.COMMAND_WORD);
        commandList.add(ResetAdminPasswordCommand.COMMAND_WORD);
        commandList.add(ResetPasswordCommand.COMMAND_WORD + " " + ResetPasswordCommand.COMMAND_FORMAT);
        commandList.add(SelectCommand.COMMAND_WORD + " " + SelectCommand.COMMAND_FORMAT);
        commandList.add(UnapplyCommand.COMMAND_WORD + " " + UnapplyCommand.COMMAND_FORMAT);
        commandList.add(UndoCommand.COMMAND_WORD);

        return commandList;
    }
}
