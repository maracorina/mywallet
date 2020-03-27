package wllt.manager.remote;

import wllt.dto.CategoryDTO;
import wllt.dto.UserCategoryDTO;
import wllt.exceptions.BusinessException;
import wllt.exceptions.ValidationException;

import java.util.List;

/**
 * Interface for Remote usage
 *
 */
public interface CategoryManager {

    CategoryDTO insertCategory(CategoryDTO categoryDTO) throws ValidationException;

    UserCategoryDTO insertUserCategoryRelation(UserCategoryDTO userCategoryDTO) throws ValidationException, BusinessException;

    UserCategoryDTO updateUserCategoryPriority(UserCategoryDTO userCategoryDTO) throws ValidationException, BusinessException;

    CategoryDTO getCategory(Integer id) throws BusinessException;
}