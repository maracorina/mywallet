package wllt.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import wllt.entities.Notification;
import wllt.entities.User;
import wllt.entities.types.StatusType;

import java.time.LocalDate;
import java.util.List;

/**
 * Data Access Object class for {@link Notification} objects.
 * It has direct access to the database and all {@link Notification} related tables.
 *
 */
@Repository
public interface NotificationDao extends CrudRepository<Notification, Integer> {

    Notification save(Notification notification);

    Notification findAllByID(Integer id);

    List<Notification> findAll();

    List<Notification> findByUser(User user);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Notification WHERE date < :date")
    void deleteAllByDateBefore(@Param("date")LocalDate date);
}

