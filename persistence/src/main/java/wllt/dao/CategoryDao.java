package wllt.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import wllt.entities.Category;

import java.util.List;

/**
 * Data Access Object class for {@link Category} objects.
 * It has direct access to the database and all {@link Category} related tables.
 *
 */
@Repository
public interface CategoryDao extends CrudRepository<Category, Integer> {

    Category findAllByID(Integer id);

    List<Category> findAllByIDIn(List<Integer> ids);

    Category findByName(String name);

    List<Category> findAll();
}
