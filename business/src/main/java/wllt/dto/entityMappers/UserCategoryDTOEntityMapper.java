package wllt.dto.entityMappers;

import wllt.dto.UserCategoryDTO;
import wllt.entities.UserCategory;

import java.util.HashSet;
import java.util.Set;

/**
 * Entity Mapper class for {@link UserCategory} & {@link UserCategoryDTO} objects.
 * The class maps an object that has been stated above, to its counterpart.
 *
 */
public class UserCategoryDTOEntityMapper {

    private UserCategoryDTOEntityMapper(){

    }

    public static UserCategory getUserCategoryFromDTO(UserCategoryDTO userCategoryDTO){

        UserCategory userCategory = new UserCategory();

        if (userCategoryDTO != null) {
            userCategory.setUser(UserDTOEntityMapper.getUserFromUserDTO(userCategoryDTO.getUser()));
            userCategory.setCategory(CategoryDTOEntityMapper.getCategoryFromDTO(userCategoryDTO.getCategory()));
            userCategory.setPriority(userCategory.getPriority());
        }

        return userCategory;
    }

    public static UserCategoryDTO getDTOFromUserCategory(UserCategory userCategory){

        UserCategoryDTO userCategoryDTO = new UserCategoryDTO();

        if (userCategory != null) {
            userCategoryDTO.setUser(UserDTOEntityMapper.getDTOFromUser(userCategory.getUser()));
            userCategoryDTO.setCategory(CategoryDTOEntityMapper.getDTOFromCategory(userCategory.getCategory()));
            userCategoryDTO.setPriority(userCategory.getPriority());
        }

        return userCategoryDTO;

    }

    public static Set<UserCategoryDTO> getUserCategoryDTOListFromUserCategoryList(Set<UserCategory> userCategorys) {
        Set<UserCategoryDTO> userCategoryDTOS = new HashSet<>();

        for (UserCategory userCategory : userCategorys) {
            userCategoryDTOS.add(getDTOFromUserCategory(userCategory));
        }

        return userCategoryDTOS;
    }

    public static Set<UserCategory> getUserCategoryListFromUserCategoryDTOList(Set<UserCategoryDTO> userCategoryDTOS) {
        Set<UserCategory> userCategorys = new HashSet<>();

        for (UserCategoryDTO userCategoryDTO : userCategoryDTOS) {
            userCategorys.add(getUserCategoryFromDTO(userCategoryDTO));
        }

        return userCategorys;
    }
}

