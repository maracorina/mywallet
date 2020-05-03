package wllt.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import wllt.dao.CategoryDao;
import wllt.dao.NotificationDao;
import wllt.dao.UserCategoryDao;
import wllt.dto.CategoryDTO;
import wllt.dto.UserCategoryDTO;
import wllt.dto.UserDTO;
import wllt.dto.entityMappers.CategoryDTOEntityMapper;
import wllt.dto.entityMappers.UserCategoryDTOEntityMapper;
import wllt.dto.entityMappers.UserDTOEntityMapper;
import wllt.entities.Category;
import wllt.entities.UserCategory;
import wllt.entities.types.CategoryType;
import wllt.entities.utils.UserCategoryId;
import wllt.exceptions.BusinessException;
import wllt.exceptions.ValidationException;
import wllt.manager.remote.CategoryManager;
import wllt.manager.remote.NotificationManager;
import wllt.validators.CategoryValidator;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryManagerImpl implements CategoryManager {

    @Autowired
    private CategoryDao categoryDao;


    @Autowired
    private UserCategoryDao userCategoryDao;

    @Override
    public CategoryDTO insertCategory(CategoryDTO categoryDTO) throws ValidationException{
        Category category = this.categoryDao.findByName(categoryDTO.getName());

        if(category == null) {
            CategoryValidator.validate(categoryDTO);
            category = this.categoryDao.save(CategoryDTOEntityMapper.getCategoryFromDTO(categoryDTO));
        }

        return CategoryDTOEntityMapper.getDTOFromCategory(category);
    }

    @Override
    public CategoryDTO getCategory(Integer id) throws BusinessException {

        Category category = this.categoryDao.findAllByID(id);

        if(category == null){
            throw new BusinessException("CategoryBusinessException01", "No category with this id was found!");
        }

        return CategoryDTOEntityMapper.getDTOFromCategory(category);
    }

    @Override
    public List<CategoryDTO> getAllByUsername(String username) {
        List<Integer> categoriesIds = this.userCategoryDao.findAllByUserUsername(username).
                stream().
                map(userCategory -> userCategory.getCategory().getID()).
                collect(Collectors.toList());
        return CategoryDTOEntityMapper.getCategoryDTOListFromCategoryList(this.categoryDao.findAllByIDIn(categoriesIds));
    }

    @Override
    public List<CategoryDTO> getAllOutCategoriesByUsername(String username) {
        List<Integer> categoriesIds = this.userCategoryDao.findAllByUserUsername(username).
                stream().
                filter(userCategory -> userCategory.getCategory().getType().equals(CategoryType.BUDGET) ||
                        userCategory.getCategory().getType().equals(CategoryType.CATEGORY)).
                map(userCategory -> userCategory.getCategory().getID()).
                collect(Collectors.toList());
        return CategoryDTOEntityMapper.getCategoryDTOListFromCategoryList(this.categoryDao.findAllByIDIn(categoriesIds));
    }

    @Override
    public UserCategoryDTO insertUserCategoryRelation(UserCategoryDTO userCategoryDTO) throws ValidationException {
        UserCategory userCategory = new UserCategory();
        userCategory.setUser(UserDTOEntityMapper.getUserFromUserDTO(userCategoryDTO.getUser()));

        Category category = this.categoryDao.findByName(userCategory.getCategory().getName());

        if(category == null) {
            CategoryDTO newCategory = userCategoryDTO.getCategory();
            newCategory.setName(newCategory.getName().toUpperCase());

            CategoryValidator.validate(newCategory);
            CategoryValidator.validatePriority(userCategoryDTO.getPriority());
            category = this.categoryDao.save(CategoryDTOEntityMapper.getCategoryFromDTO(newCategory));
        }
        userCategory.setCategory(category);
        userCategory.setPriority(userCategoryDTO.getPriority());

        return UserCategoryDTOEntityMapper.getDTOFromUserCategory(this.userCategoryDao.save(userCategory));
    }

    @Override
    public UserCategoryDTO updateUserCategoryPriority(UserCategoryDTO userCategoryDTO) throws ValidationException, BusinessException{
        if(this.categoryDao.findAllByID(userCategoryDTO.getCategory().getID()) == null) {
            throw new BusinessException("CategoryBusinessException02", "No category with this id was found!");
        }
        else if(this.userCategoryDao.findAllByID(new UserCategoryId(userCategoryDTO.getUser().getID(),
                    userCategoryDTO.getCategory().getID())) == null) {
            return insertUserCategoryRelation(userCategoryDTO);
        }
        else {
            CategoryValidator.validatePriority(userCategoryDTO.getPriority());
            this.userCategoryDao.updatePriority(
                    userCategoryDTO.getPriority(),
                    userCategoryDTO.getUser().getID(),
                    userCategoryDTO.getCategory().getID()
            );
            return UserCategoryDTOEntityMapper.getDTOFromUserCategory(this.userCategoryDao.findAllByID(
                        new UserCategoryId(userCategoryDTO.getUser().getID(), userCategoryDTO.getCategory().getID())));
        }
    }
}
