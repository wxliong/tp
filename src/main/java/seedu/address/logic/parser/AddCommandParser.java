package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;


import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.classgroup.ClassGroup;
import seedu.address.model.classgroup.ClassGroupId;
import seedu.address.model.classgroup.ClassGroupType;
import seedu.address.model.student.*;
import seedu.address.model.tamodule.AcademicYear;
import seedu.address.model.tamodule.ModuleCode;
import seedu.address.model.tamodule.ModuleName;
import seedu.address.model.tamodule.TaModule;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    private static final Pattern ADD_COMMAND_FORMAT = Pattern.compile("(?<entityType>\\S+)(?<arguments>.*)");

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public AddCommand parse(String args, Model model) throws ParseException {
        final Matcher matcher = ADD_COMMAND_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        final String entityType = matcher.group("entityType");
        final String arguments = matcher.group("arguments");
        ArgumentMultimap argMultimap;

        switch(entityType) {

        case TYPE_STUDENT:
            argMultimap = ArgumentTokenizer.tokenize(arguments, PREFIX_ID, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL);

            if (!arePrefixesPresent(argMultimap, PREFIX_ID, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL)
                    || !argMultimap.getPreamble().isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
            }

            StudentId id = ParserUtil.parseStudentId(argMultimap.getValue(PREFIX_ID).get());
            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
            Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
            Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());

            Student student = new Student(id, name, phone, email);

            return new AddCommand(student);

        case TYPE_MODULE:
            argMultimap = ArgumentTokenizer.tokenize(arguments, PREFIX_NAME, PREFIX_MODULE_CODE, PREFIX_ACADEMIC_YEAR);

            if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_MODULE_CODE, PREFIX_ACADEMIC_YEAR)
                    || !argMultimap.getPreamble().isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
            }

            ModuleName moduleName = ParserUtil.parseModuleName(argMultimap.getValue(PREFIX_NAME).get());
            ModuleCode moduleCode = ParserUtil.parseModuleCode(argMultimap.getValue(PREFIX_MODULE_CODE).get());
            AcademicYear academicYear = ParserUtil.parseAcademicYear(argMultimap.getValue(PREFIX_ACADEMIC_YEAR).get());

            TaModule module = new TaModule(moduleName, moduleCode, academicYear);

            return new AddCommand(module);

        case TYPE_CLASS:
            argMultimap = ArgumentTokenizer.tokenize(arguments, PREFIX_ID, PREFIX_TYPE, PREFIX_MODULE_INDEX);

            if (!arePrefixesPresent(argMultimap, PREFIX_ID, PREFIX_TYPE, PREFIX_MODULE_INDEX)
                    || !argMultimap.getPreamble().isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
            }

            ClassGroupId classGroupId = ParserUtil.parseClassGroupId(argMultimap.getValue(PREFIX_NAME).get());
            ClassGroupType classGroupType = ParserUtil.parseClassGroupType(argMultimap.getValue(PREFIX_MODULE_CODE).get());
            TaModule taModule = ParserUtil.parseTaModule(argMultimap.getValue(PREFIX_MODULE_INDEX).get(), model);

            ClassGroup classGroup = new ClassGroup(classGroupId, classGroupType, taModule);

            return new AddCommand(classGroup);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
