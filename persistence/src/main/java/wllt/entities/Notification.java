package wllt.entities;

import wllt.entities.types.NotificationType;
import wllt.utils.LocalDateConverter;
import wllt.utils.LocalTimeConverter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@Table(name="notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer ID;

    @Column(name = "date")
    @Convert(converter = LocalDateConverter.class)
    private LocalDate date;

    @Column(name = "time")
    @Convert(converter = LocalTimeConverter.class)
    private LocalTime time;

    @Column(name = "message")
    private String message;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private NotificationType type;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="user_id",referencedColumnName = "ID")
    private User user;

    public Notification() {
    }

    public Notification(LocalDate date, LocalTime time, String message, NotificationType type, User user) {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notification that = (Notification) o;
        return Objects.equals(ID, that.ID) &&
                Objects.equals(date, that.date) &&
                Objects.equals(time, that.time) &&
                Objects.equals(message, that.message) &&
                type == that.type &&
                Objects.equals(user, that.user);
    }

}
