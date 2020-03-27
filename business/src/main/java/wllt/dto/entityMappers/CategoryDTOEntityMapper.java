package wllt.dto.entityMappers;

import wllt.dto.CategoryDTO;
import wllt.dto.NotificationDTO;
import wllt.entities.Category;
import wllt.entities.Notification;

import java.util.HashSet;
import java.util.Set;

/**
 * Entity Mapper class for {@link Notification} & {@link NotificationDTO} objects.
 * The class maps an object that has been stated above, to its counterpart.
 *
 */
public class CategoryDTOEntityMapper {

    private CategoryDTOEntityMapper(){

    }

    public static Category getCategoryFromDTO(CategoryDTO categoryDTO){

        Category category = new Category();

        if (categoryDTO != null) {
            category.setID(categoryDTO.getID());
            category.setType(categoryDTO.getType());
            category.setName(categoryDTO.getName());
        }

        return category;
    }

    public static CategoryDTO getDTOFromCategory(Category category){

        CategoryDTO categoryDTO = new CategoryDTO();

        if (category != null) {
            categoryDTO.setID(category.getID());
            categoryDTO.setType(category.getType());
            categoryDTO.setName(category.getName());
        }

        return categoryDTO;

    }

    public static Set<CategoryDTO> getCategoryDTOListFromCategoryList(Set<Category> categorys) {
        Set<CategoryDTO> categoryDTOS = new HashSet<>();

        for (Category category : categorys) {
            categoryDTOS.add(getDTOFromCategory(category));
        }

        return categoryDTOS;
    }

    public static Set<Category> getCategoryListFromCategoryDTOList(Set<CategoryDTO> categoryDTOS) {
        Set<Category> categorys = new HashSet<>();

        for (CategoryDTO categoryDTO : categoryDTOS) {
            categorys.add(getCategoryFromDTO(categoryDTO));
        }

        return categorys;
    }
}
