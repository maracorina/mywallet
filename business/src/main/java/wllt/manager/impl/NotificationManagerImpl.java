package wllt.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import wllt.dao.NotificationDao;
import wllt.dto.NotificationDTO;
import wllt.dto.UserDTO;
import wllt.dto.entityMappers.NotificationDTOEntityMapper;
import wllt.dto.entityMappers.UserDTOEntityMapper;
import wllt.entities.Notification;
import wllt.entities.User;
import wllt.entities.types.NotificationType;
import wllt.exceptions.BusinessException;
import wllt.manager.remote.NotificationManager;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

@Component
@Transactional
public class NotificationManagerImpl implements NotificationManager {

    @Autowired
    private NotificationDao notificationDao;

    @Override
    public NotificationDTO findNotification(Integer id) throws BusinessException {
        Notification notification = this.notificationDao.findAllByID(id);
        if (notification == null) {
            throw new BusinessException("NotificationBusinessException01", "No notification with this id was found!");
        }
        return NotificationDTOEntityMapper.getDTOFromNotification(notification);
    }

    @Override
    public List<NotificationDTO> findAllNotifications() {
        List<Notification> notifications = this.notificationDao.findAll();
        return NotificationDTOEntityMapper.getNotificationDTOListFromNotificationList(notifications);
    }

    @Override
    public List<NotificationDTO> getUserNotifications(Integer userId) {
        User user = new User();
        user.setID(userId);
        List<Notification> notifications = this.notificationDao.findByUser(user);
        return NotificationDTOEntityMapper.getNotificationDTOListFromNotificationList(notifications);
    }

    @Override
    public NotificationDTO insertNotification(NotificationDTO notificationDTO){
        Notification notification = createNotification(notificationDTO.getType(), notificationDTO.getMessage(), notificationDTO.getUser());

        return NotificationDTOEntityMapper.getDTOFromNotification(this.notificationDao.save(notification));
    }

    @Override
    public NotificationDTO insertWelcomeNotification(UserDTO userDTO) {
        String message = "Welcome, " + userDTO.getFirstName() + " " + userDTO.getLastName() + "!" + '\n' +
                '\n' +
                "First Name : " + userDTO.getFirstName() + '\n' +
                "Last Name : " + userDTO.getLastName() + '\n' +
                "Mobile Number : " + userDTO.getMobileNumber() + '\n' +
                "Email : " + userDTO.getEmail() + '\n' +
                "Username : " + userDTO.getUsername() + '\n';
        Notification notification = createNotification(NotificationType.WELCOME_NEW_USER, message,  userDTO);

        return NotificationDTOEntityMapper.getDTOFromNotification(notificationDao.save(notification));
    }

    @Override
    public NotificationDTO insertDeactivatedUserNotification(UserDTO userDTO) {
        String message = "User " + userDTO.getUsername() + " was deleted!" + '\n' +
                '\n' +
                "First Name : " + userDTO.getFirstName() + '\n' +
                "Last Name : " + userDTO.getLastName() + '\n' +
                "Mobile Number : " + userDTO.getMobileNumber() + '\n' +
                "Email : " + userDTO.getEmail() + '\n' +

                "Username : " + userDTO.getUsername() + '\n';

        Notification notification = createNotification(NotificationType.USER_DELETED, message, userDTO);

        return NotificationDTOEntityMapper.getDTOFromNotification(notificationDao.save(notification));
    }

    @Override
    public NotificationDTO insertUserUpdatedNotification(UserDTO newUserDTO, UserDTO oldUserDTO) {
        String message = "User account updated!" + '\n' +
                "NEW INFO " + '\n' +
                "\tFirst Name : " + newUserDTO.getFirstName() + '\n' +
                "\tLast Name : " + newUserDTO.getLastName() + '\n' +
                "\tMobile Number : " + newUserDTO.getMobileNumber() + '\n' +
                "\tEmail : " + newUserDTO.getEmail() + '\n' +
                "\tUsername : " + newUserDTO.getUsername() + '\n' +
                '\n' +
                "OLD INFO " + '\n' +
                "\tFirst Name : " + oldUserDTO.getFirstName() + '\n' +
                "\tLast Name : " + oldUserDTO.getLastName() + '\n' +
                "\tMobile Number : " + oldUserDTO.getMobileNumber() + '\n' +
                "\tEmail : " + oldUserDTO.getEmail() + '\n' +
                "\tUsername : " + oldUserDTO.getUsername() + '\n';

        Notification notification = createNotification(NotificationType.USER_UPDATED, message, newUserDTO);

        return NotificationDTOEntityMapper.getDTOFromNotification(notificationDao.save(notification));
    }

    @Override
    @Scheduled(cron = "0 0 12 1 * ?")
    public void clean() {
        LocalDate date = LocalDate.now().minusMonths(1);
        this.notificationDao.deleteAllByDateBefore(date);
    }

    private Notification createNotification(NotificationType type, String message, UserDTO userDTO) {
        Notification notification = new Notification();
        notification.setDate(LocalDate.now());
        notification.setTime(LocalTime.now());
        notification.setMessage(message);
        notification.setType(type);
        notification.setUser(UserDTOEntityMapper.getUserFromUserDTO(userDTO));

        return notification;
    }
}
