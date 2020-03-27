package wllt.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wllt.dto.NotificationDTO;
import wllt.manager.remote.NotificationManager;

import javax.servlet.http.HttpServlet;
import java.util.List;

/**
 * REST Controller for User manipulation.
 *
 */
@RestController
@RequestMapping(path = "/notifications")
public class NotificationController extends HttpServlet {

    @Autowired
    public NotificationManager notificationManager;

    public NotificationController(NotificationManager notificationManager) {
        this.notificationManager = notificationManager;
    }

    /**
     * The Controller returns an array of {@link NotificationDTO} Objects that
     *      * map all the {@link wllt.entities.Transaction} objects from the database
     *      * corresponding to the user with the given id
     *
     * @param userId is an {@link Integer} the user id
     * @return a success response containing the array
     */
    @GetMapping(path="/{userId}", produces = "application/json")
    public ResponseEntity<?> getUserNotifications(@PathVariable("userId") Integer userId) throws JsonProcessingException {

        List<NotificationDTO> notificationDTOS = this.notificationManager.getUserNotifications(userId);
        return ResponseEntity.ok(new ObjectMapper().writeValueAsString(notificationDTOS));
    }

}