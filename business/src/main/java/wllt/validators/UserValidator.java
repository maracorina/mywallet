package wllt.validators;

import wllt.dto.UserDTO;
import wllt.entities.User;
import wllt.exceptions.ValidationException;

/**
 * The class validator for {@link User} objects.
 *
 * @author Mara Corina
 */
public class UserValidator {
    public static void validate(UserDTO userDTO) throws ValidationException {
        if(!userDTO.getFirstName().matches("[a-zA-Z]{5,20}"))
            throw new ValidationException("UserValidation01", "Firstname can only contain alphabets and be between 5 and 20 characters");
        if(!userDTO.getLastName().matches("[a-zA-Z]{5,20}"))
            throw new ValidationException("UserValidation02", "Lastname can only contain alphabets and be between 5 and 20 characters");
        if(!userDTO.getMobileNumber()
                .matches("^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\.0-9]*$"))
            throw new ValidationException("UserValidation03", "Not a valid romanian phone number");
        if(!userDTO.getEmail().matches("[-a-zA-Z_]{1,50}@[a-zA-Z]{1,50}.[a-zA-Z]{1,5}"))
            throw new ValidationException("UserValidation04", "Not a valid email");
        if(!userDTO.getPassword().matches("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#_!$%]).{6,20})"))
            throw new ValidationException("UserValidation06", "Password must contain at least one digit, one upper case letter, one lower case letter and one special symbol and be between 6 and 20 characters");

    }
}
