package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLASS_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEEK;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MarkCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.student.Student;

//@@author jxt00
/**
 * Parses input arguments and creates a new MarkCommand object
 */
public class MarkCommandParser implements Parser<MarkCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MarkCommand
     * and returns a MarkCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    @Override
    public MarkCommand parse(String args, Model model) throws ParseException {

        ArgumentMultimap argMultimap = seedu.address.logic.parser.ArgumentTokenizer.tokenize(args,
                PREFIX_CLASS_INDEX, PREFIX_WEEK, PREFIX_STUDENT);

        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_CLASS_INDEX, PREFIX_WEEK, PREFIX_STUDENT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MarkCommand.MESSAGE_USAGE));
        }

        try {
            Index classGroupIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_CLASS_INDEX).get());
            Index weekIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_WEEK).get());
            ObservableList<Student> students = ParserUtil.parseStudents(
                    argMultimap.getValue(PREFIX_STUDENT).get(), model);
            return new MarkCommand(classGroupIndex, weekIndex, students);
        } catch (ParseException pe) {
            throw new ParseException(String.format("%s\n%s", pe.getMessage(), MarkCommand.MESSAGE_USAGE), pe);
        }
    }
}
