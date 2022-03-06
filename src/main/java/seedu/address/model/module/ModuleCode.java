package seedu.address.model.module;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Module's module code in the TAssist.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class ModuleCode {

    public static final String MESSAGE_CONSTRAINTS =
            "Module code should start with 2 to 4 characters, followed by , 4 digits, ending with optional letters "
                    + "and it should not be blank";

    /*
     * The first character of the module code must begin with a letter.
     */
    public static final String VALIDATION_REGEX = "^[\\p{Upper}]{2,4}[\\p{Digit}]{4}\\p{Upper}*$";

    public final String moduleCode;

    /**
     * Constructs a {@code Name}.
     *
     * @param moduleCode A valid module code.
     */
    public ModuleCode(String moduleCode) {
        requireNonNull(moduleCode);
        checkArgument(isValidName(moduleCode.toUpperCase()), MESSAGE_CONSTRAINTS);
        this.moduleCode = moduleCode.toUpperCase();
    }

    /**
     * Returns true if a given string is a valid module code.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return moduleCode;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ModuleCode // instanceof handles nulls
                && moduleCode.equals(((ModuleCode) other).moduleCode)); // state check
    }

    @Override
    public int hashCode() {
        return moduleCode.hashCode();
    }

}