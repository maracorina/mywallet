package wllt.validators;

import wllt.dto.EmailDTO;
import wllt.exceptions.ValidationException;

public class EmailValidator {
    public static void validate(EmailDTO emailDTO) throws ValidationException {
        if(emailDTO.getTo() == null)
            throw new ValidationException("EmailValidation01", "Receiver must not be null!");
        if(emailDTO.getSubject() == null)
            throw new ValidationException("EmailValidation02", "Subject must not be null!");
        if(emailDTO.getText() == null)
            throw new ValidationException("EmailValidation03", "Message must not be null!");
    }
}
