package wllt.validators;

import wllt.dto.CategoryDTO;
import wllt.entities.Category;
import wllt.exceptions.ValidationException;

/**
 * The class validator for {@link Category} objects.
 *
 */
public class CategoryValidator {
    public static void validate(CategoryDTO categoryDTO) throws ValidationException {
        if (!categoryDTO.getName().matches("[a-zA-Z]{3,20}"))
            throw new ValidationException("CategoryValidation01", "Name can only contain alphabets and be between 3 and 20 characters");
    }

    public static void validatePriority(Integer priority) throws ValidationException {
        if (priority<1 || priority>10)
            throw new ValidationException("CategoryPriorityValidation01", "Category priority must be an integer between 1 and 10");
    }
}
