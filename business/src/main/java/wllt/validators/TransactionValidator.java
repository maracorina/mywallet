package wllt.validators;

import wllt.dto.TransactionDTO;
import wllt.entities.Transaction;
import wllt.exceptions.ValidationException;

/**
 * The class validator for {@link Transaction} objects.
 *
 */
public class TransactionValidator {
    public static void validate(TransactionDTO transactionDTO) throws ValidationException {
        if (transactionDTO.getSum() < 0)
            throw new ValidationException("TransactionValidation01", "Sum must be positive");
    }
}
