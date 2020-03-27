package wllt.entities.utils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserCategoryId implements Serializable {

    private Integer userId;
    private Integer categoryId;

    public UserCategoryId() {
    }

    public UserCategoryId(Integer userId, Integer categoryId) {
        this.userId = userId;
        this.categoryId = categoryId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserCategoryId that = (UserCategoryId) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(categoryId, that.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, categoryId);
    }

    @Override
    public String toString() {
        return "UserCategoryId{" +
                "userId=" + userId +
                ", categoryId=" + categoryId +
                '}';
    }
}
