package wllt.dto;

import wllt.entities.types.NotificationType;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class NotificationDTO implements Serializable {

    private Integer ID;
    private LocalDate date;
    private LocalTime time;
    private String message;
    private NotificationType type;
    private UserDTO user;

    public NotificationDTO() {
    }

    public NotificationDTO(Integer ID, LocalDate date, LocalTime time, String message, NotificationType type, UserDTO user) {
        this.ID = ID;
        this.date = date;
        this.time = time;
        this.message = message;
        this.type = type;
        this.user = user;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationDTO that = (NotificationDTO) o;
        return Objects.equals(ID, that.ID) &&
                Objects.equals(date, that.date) &&
                Objects.equals(time, that.time) &&
                Objects.equals(message, that.message) &&
                type == that.type &&
                Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, date, time, message, type, user);
    }

    @Override
    public String toString() {
        return "NotificationDTO{" +
                "ID=" + ID +
                ", date=" + date +
                ", time=" + time +
                ", message='" + message + '\'' +
                ", type=" + type +
                ", user=" + user +
                '}';
    }
}
