package wllt.dto.entityMappers;

import wllt.dto.NotificationDTO;
import wllt.entities.Notification;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Entity Mapper class for {@link Notification} & {@link NotificationDTO} objects.
 * The class maps an object that has been stated above, to its counterpart.
 *
 */
public class NotificationDTOEntityMapper {

    private NotificationDTOEntityMapper(){

    }

    public static Notification getNotificationFromDTO(NotificationDTO notificationDTO){

        Notification notification = new Notification();

        if (notificationDTO != null) {
            notification.setID(notificationDTO.getID());
            notification.setDate(notificationDTO.getDate());
            notification.setMessage(notificationDTO.getMessage());
            notification.setType(notificationDTO.getType());
            notification.setTime(notificationDTO.getTime());
            notification.setUser(UserDTOEntityMapper.getUserFromUserDTO(notificationDTO.getUser()));
        }

        return notification;
    }

    public static NotificationDTO getDTOFromNotification(Notification notification){

        NotificationDTO notificationDTO = new NotificationDTO();

        if (notification != null) {
            notificationDTO.setID(notification.getID());
            notificationDTO.setDate(notification.getDate());
            notificationDTO.setMessage(notification.getMessage());
            notificationDTO.setType(notification.getType());
            notificationDTO.setTime(notification.getTime());
            notificationDTO.setUser(UserDTOEntityMapper.getDTOFromUser(notification.getUser()));
        }

        return notificationDTO;

    }

    public static List<NotificationDTO> getNotificationDTOListFromNotificationList(List<Notification> notifications) {
        List<NotificationDTO> notificationDTOS = new ArrayList<>();

        for (Notification notification : notifications) {
            notificationDTOS.add(getDTOFromNotification(notification));
        }

        return notificationDTOS;
    }

    public static Set<Notification> getNotificationListFromNotificationDTOList(Set<NotificationDTO> notificationDTOS) {
        Set<Notification> notifications = new HashSet<>();

        for (NotificationDTO notificationDTO : notificationDTOS) {
            notifications.add(getNotificationFromDTO(notificationDTO));
        }

        return notifications;
    }
}
