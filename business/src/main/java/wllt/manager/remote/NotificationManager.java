package wllt.manager.remote;

import org.springframework.scheduling.annotation.Scheduled;
import wllt.dto.NotificationDTO;
import wllt.dto.UserDTO;
import wllt.exceptions.BusinessException;
import wllt.exceptions.ValidationException;

import java.util.List;

public interface NotificationManager {

    NotificationDTO findNotification(Integer id) throws BusinessException;

    List<NotificationDTO> findAllNotifications();

    List<NotificationDTO> getUserNotifications(Integer userId);

    NotificationDTO insertNotification(NotificationDTO notification);

    NotificationDTO insertWelcomeNotification(UserDTO userDTO);

    NotificationDTO insertDeactivatedUserNotification(UserDTO userDTO);

    NotificationDTO insertUserUpdatedNotification(UserDTO newUserDTO, UserDTO oldUserDTO);

    @Scheduled(cron="0 * * ? * *")
    void clean();
}
