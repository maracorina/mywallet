package wllt.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import wllt.entities.User;
import wllt.entities.types.StatusType;

import java.util.List;


/**
 * Data Access Object class for {@link User} objects.
 * It has direct access to the database and all {@link User} related tables.
 *
 */
@Repository
public interface UserDao extends CrudRepository<User, Integer> {

    User findAllByID(Integer id);

    List<User> findAll();

    User findByUsername(String username);

    User findByUsernameAndPassword(String username, String password);

    @Transactional
    @Modifying
    @Query(value = "UPDATE User " +
            "SET password = :newPassword, " +
            "firstname = :newFirstname," +
            "lastname = :newLastname," +
            "mobileNumber = :newMobileNumber," +
            "email = :newEmail," +
            "counter = :newCounter, " +
            "status = :newStatus " +
            "where username = :givenUsername")
    void update(@Param("givenUsername") String givenUsername,
                       @Param("newPassword") String newPassword,
                       @Param("newFirstname") String newFirstname,
                       @Param("newLastname") String newLastname,
                       @Param("newMobileNumber") String newMobileNumber,
                       @Param("newEmail") String newEmail,
                       @Param("newCounter") Integer newCounter,
                       @Param("newStatus") StatusType newStatus);

    @Transactional
    @Modifying
    @Query(value = "UPDATE User " +
            "SET status = 0 " +
            "where username = :username")
    void deactivateUser(@Param("username") String username);

    @Transactional
    @Modifying
    @Query(value = "UPDATE User " +
            "SET status = 1, " +
            "counter = 0, " +
            "password = :password " +
            "where username = :username")
    void activateUser(@Param("username") String username, @Param("password") String password);
}
