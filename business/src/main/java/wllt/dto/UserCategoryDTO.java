package wllt.dto;

import java.util.Objects;

public class UserCategoryDTO {

    private UserDTO user;
    private CategoryDTO category;
    private Integer priority;

    public UserCategoryDTO() {
    }

    public UserCategoryDTO(UserDTO user, CategoryDTO category, Integer priority) {
        this.user = user;
        this.category = category;
        this.priority = priority;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
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
        UserCategoryDTO that = (UserCategoryDTO) o;
        return Objects.equals(user, that.user) &&
                Objects.equals(category, that.category) &&
                Objects.equals(priority, that.priority);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, category, priority);
    }

    @Override
    public String toString() {
        return "UserCategoryDTO{" +
                "user=" + user +
                ", category=" + category +
                ", priority=" + priority +
                '}';
    }
}
