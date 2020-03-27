package wllt.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import wllt.entities.Transaction;
import wllt.entities.User;

import java.util.List;
import java.util.Set;

/**
 * Data Access Object class for {@link Transaction} objects.
 * It has direct access to the database and all {@link Transaction} related tables.
 *
 */
@Repository
public interface TransactionDao extends CrudRepository<Transaction, Integer> {

    Transaction findAllByID(Integer id);

    Set<Transaction> findAll();

    Transaction save(Transaction transaction);

    List<Transaction> findByUserID(Integer userId);

    List<Transaction> findByUserIDAndCategoryID(Integer userId, Integer categoryId);
}
