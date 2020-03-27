package wllt.entities;

import wllt.entities.utils.UserCategoryId;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "users_categories")
public class UserCategory implements Serializable {

    @EmbeddedId
    private UserCategoryId ID;

    @ManyToOne(cascade = CascadeType.MERGE)
    @MapsId("userId")
    @JoinColumn(name="user_id",referencedColumnName = "ID", insertable = false, updatable = false)
    private User user;

    @ManyToOne(cascade = CascadeType.MERGE)
    @MapsId("categoryId")
    @JoinColumn(name="category_id",referencedColumnName = "ID", insertable = false, updatable = false)
    private Category category;

    @Column(name = "priority")
    private Integer priority;

    public UserCategory() {
        this.ID = new UserCategoryId();
    }

    public UserCategory(UserCategoryId ID, User user, Category category, Integer priority) {
        this.ID = ID;
        this.user = user;
        this.category = category;
        this.priority = priority;
    }

    public UserCategoryId getID() {
        return ID;
    }

    public void setID(UserCategoryId ID) {
        this.ID = ID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserCategory that = (UserCategory) o;
        return Objects.equals(ID, that.ID) &&
                Objects.equals(user, that.user) &&
                Objects.equals(category, that.category) &&
                Objects.equals(priority, that.priority);
    }

}
