package wllt.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import wllt.dao.UserCategoryDao;
import wllt.dao.UserDao;
import wllt.dto.CategoryDTO;
import wllt.dto.UserCategoryDTO;
import wllt.dto.UserDTO;
import wllt.dto.entityMappers.CategoryDTOEntityMapper;
import wllt.dto.entityMappers.UserCategoryDTOEntityMapper;
import wllt.dto.entityMappers.UserDTOEntityMapper;
import wllt.entities.Category;
import wllt.entities.User;
import wllt.entities.UserCategory;
import wllt.entities.types.StatusType;
import wllt.exceptions.BusinessException;
import wllt.exceptions.ValidationException;
import wllt.manager.remote.CategoryManager;
import wllt.manager.remote.NotificationManager;
import wllt.manager.remote.UserManager;
import wllt.utils.PasswordUtils;
import wllt.utils.email.EmailUtils;
import wllt.validators.UserValidator;

import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

@Component
public class UserManagerImpl implements UserManager {

    @Autowired
    private UserDao userDao;

    @Autowired
    private NotificationManager notificationManager;

    @Autowired
    private CategoryManager categoryManager;

    @Autowired
    private EmailUtils emailUtils;

    private Logger logger = Logger.getLogger(UserManagerImpl.class.getName());

    @Override
    public UserDTO findUser(Integer id) throws BusinessException {
        User user = userDao.findAllByID(id);
        if (user == null) {
            throw new BusinessException("UserBusinessException01", "No user with this id was found!");
        }
        return UserDTOEntityMapper.getDTOFromUser(user);
    }

    @Override
    public List<UserDTO> findAllUsers(){
        List<User> users = userDao.findAll();
        return UserDTOEntityMapper.getUserDTOListFromUserList(users);
    }

    @Override
    public UserDTO findUserByUsernameAndPassword(String username, String password) throws BusinessException {
        User user= userDao.findByUsernameAndPassword(username,password);
        if (user == null) {
            throw new BusinessException("UserBusinessException02", "No user with this username and password was found!");
        }
        return UserDTOEntityMapper.getDTOFromUser(user);
    }

    @Override
    public UserDTO findUserByUsername(String username) throws BusinessException {
        User user= userDao.findByUsername(username);
        if (user == null) {
            throw new BusinessException("UserBusinessException02", "No user with this username was found!");
        }
        return UserDTOEntityMapper.getDTOFromUser(user);
    }

    @Override
    public UserDTO insertUser(UserDTO userDTO) throws ValidationException, BusinessException {
        UserValidator.validate(userDTO);

        userDTO = createUserToInsert(userDTO);

        UserDTO persistedUserDTO =  UserDTOEntityMapper.getDTOFromUser(userDao.save(UserDTOEntityMapper.getUserFromUserDTO(userDTO)));

        this.notificationManager.insertWelcomeNotification(persistedUserDTO);
        this.emailUtils.sendWelcomeMessage(persistedUserDTO);

        return persistedUserDTO;
    }

    @Override
    public UserCategoryDTO insertUserCategory(UserCategoryDTO userCategoryDTO) throws ValidationException, BusinessException {
        User persistedUser = userDao.findAllByID(userCategoryDTO.getUser().getID());

        userCategoryDTO.setUser(UserDTOEntityMapper.getDTOCompleteFromUser(persistedUser));

        return this.categoryManager.insertUserCategoryRelation(userCategoryDTO);
    }

    @Override
    public UserCategoryDTO updateCategoryPriority(UserCategoryDTO userCategoryDTO) throws BusinessException, ValidationException {
        if (userDao.findAllByID(userCategoryDTO.getUser().getID()) != null) {
            return this.categoryManager.updateUserCategoryPriority(userCategoryDTO);
        } else{
            throw new BusinessException("UserBusinessException03", "No user with this id was found!");
        }
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO) throws BusinessException, ValidationException {
        UserValidator.validate(userDTO);
        User persistedUser = userDao.findAllByID(userDTO.getID());

        if (persistedUser == null)
            throw new BusinessException("UserBusinessException04", "No user with this id was found!");

        userDao.update(userDTO.getUsername(),
                PasswordUtils.hash(userDTO.getPassword()),
                userDTO.getFirstName(),
                userDTO.getLastName(),
                userDTO.getMobileNumber(),
                userDTO.getEmail(),
                userDTO.getCounter(),
                userDTO.getStatus());
        UserDTO persistedUserDTO = UserDTOEntityMapper.getDTOFromUser(userDao.findAllByID(userDTO.getID()));

        this.notificationManager.insertUserUpdatedNotification(persistedUserDTO, UserDTOEntityMapper.getDTOFromUser(persistedUser));
        this.emailUtils.sendUpdatedAccountMessage(persistedUserDTO, UserDTOEntityMapper.getDTOFromUser(persistedUser));

        return persistedUserDTO;
    }

    @Override
    public UserDTO deactivateUser(String username) throws BusinessException{
        User persistedUser = userDao.findByUsername(username);

        if (persistedUser == null)
            throw new BusinessException("UserBusinessException05", "No user with this username was found!");

        this.userDao.deactivateUser(username);
        UserDTO persistedUserDTO = UserDTOEntityMapper.getDTOFromUser(userDao.findByUsername(username));

        this.notificationManager.insertDeactivatedUserNotification(persistedUserDTO);

        return persistedUserDTO;
    }

    @Override
    public UserDTO activateUser(String username) throws BusinessException, ValidationException {
        User persistedUser = userDao.findByUsername(username);

        if (persistedUser == null)
            throw new BusinessException("UserBusinessException06", "No user with this username was found!");

        String newPassword = PasswordUtils.generatePassword();
        String hashedNewPassword = PasswordUtils.hash(newPassword);
        this.userDao.activateUser(username, hashedNewPassword);
        UserDTO persistedUserDTO = UserDTOEntityMapper.getDTOFromUser(userDao.findByUsername(username));

        this.emailUtils.sendNewPasswordMessage(persistedUserDTO);

        return persistedUserDTO;
    }

    /**
     * Creates the {@link User} object to be persisted
     * @param userDTO is an {@link UserDTO} object that maps the {@link User}
     *                    object that will be persisted in the database.
     * @return a {@link User} object
     */
    private UserDTO createUserToInsert(UserDTO userDTO) {
        userDTO.setCounter(0);
        userDTO.setStatus(StatusType.ACTIVE);

        String username = generateUsername(userDTO.getFirstName(), userDTO.getLastName());
        userDTO.setUsername(username);

        userDTO.setPassword(PasswordUtils.hash(userDTO.getPassword()));

        return userDTO;
    }

    /**
     * Generates a unique username for the ready to insert {@link User} object
     *      using the user firstname and lastname
     * @param firstName and firstName
     * @param lastName and lastName
     *
     * @return {@link String} the username
     */
    private String generateUsername(String firstName, String lastName) {
        String firstPart;
        if(lastName.length() >= 5){
            firstPart = lastName.substring(0, 5);
        } else {
            firstPart = lastName;
        }

        int charPosition = 0;
        String username = (firstPart + firstName.charAt(charPosition)).toLowerCase();

        while(userDao.findByUsername(username) != null){
            charPosition++;
            if(charPosition < firstName.length()){
                username = (username + firstName.charAt(charPosition)).toLowerCase();
            } else{
                username = username + new Random().nextInt();
            }
        }
        return username;
    }
}