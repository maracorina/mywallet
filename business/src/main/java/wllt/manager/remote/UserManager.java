package wllt.manager.remote;

import wllt.dto.UserCategoryDTO;
import wllt.dto.UserDTO;
import wllt.exceptions.BusinessException;
import wllt.exceptions.ValidationException;

import java.util.List;

/**
 * Interface for Remote usage
 *
 * @author Mara Corina
 */
public interface UserManager {

    UserDTO findUser(Integer id) throws BusinessException;

    List<UserDTO> findAllUsers();

    UserDTO findUserByUsernameAndPassword(String username, String password) throws BusinessException;

    UserDTO findUserByUsername(String username) throws BusinessException;

    UserDTO insertUser(UserDTO userDTO) throws ValidationException, BusinessException;

    UserCategoryDTO insertUserCategory(UserCategoryDTO userCategoryDTO) throws ValidationException, BusinessException;

    UserCategoryDTO updateCategoryPriority(UserCategoryDTO userCategoryDTO) throws BusinessException, ValidationException;

    UserDTO updateUser(UserDTO userDTO) throws BusinessException, ValidationException;

    UserDTO deactivateUser(String username) throws BusinessException;

    UserDTO activateUser(String username) throws BusinessException, ValidationException;
}
