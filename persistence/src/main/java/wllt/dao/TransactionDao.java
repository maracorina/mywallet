package wllt.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import wllt.entities.Transaction;
import wllt.entities.User;
import wllt.entities.types.CategoryType;

import java.time.LocalDate;
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

    @Query(value = "SELECT sum(t.sum) " +
            "FROM Transaction t " +
            "inner join t.category c " +
            "inner join t.user u " +
            "where c.type = :categoryType " +
            "and u.username = :username")
    Double getTotalSumByTypeAndUsername(@Param("categoryType")CategoryType categoryType, @Param("username")String username);

    @Query(value = "SELECT t FROM Transaction t WHERE t.ID=:id AND t.date > :date")
    List<Transaction> findByIDAndAfterDate(@Param("id")Integer id, @Param("date")LocalDate date);
}
