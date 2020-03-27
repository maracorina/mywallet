package wllt.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import wllt.entities.Category;
import wllt.entities.UserCategory;
import wllt.entities.utils.UserCategoryId;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Data Access Object class for {@link UserCategory} objects.
 * It has direct access to the database and all {@link UserCategory} related tables.
 *
 */
@Repository
public interface UserCategoryDao extends CrudRepository<UserCategory, Integer> {

    UserCategory findAllByID(UserCategoryId id);

    List<UserCategory> findAll();

    @Transactional
    @Modifying
    @Query(value = "UPDATE UserCategory " +
            "SET priority = :newPriority " +
            "where user.ID = :userId " +
            "and category.ID = :categoryId ")
    void updatePriority(@Param("newPriority") Integer newPriority,
                @Param("userId") Integer userId,
                @Param("categoryId") Integer categoryId);
}
