package wllt.entities;

import wllt.entities.types.CategoryType;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="categories")
public class Category {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer ID;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private CategoryType type;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.REFRESH)
    private Set<UserCategory> userCategories;

    public Category() {
    }

    public Category(CategoryType type, String name, Set<UserCategory> userCategories) {
        this.type = type;
        this.name = name;
        this.userCategories = userCategories;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public CategoryType getType() {
        return type;
    }

    public void setType(CategoryType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<UserCategory> getUserCategories() {
        return userCategories;
    }

    public void setUserCategories(Set<UserCategory> userCategories) {
        this.userCategories = userCategories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(ID, category.ID) &&
                type == category.type &&
                Objects.equals(name, category.name) &&
                Objects.equals(userCategories, category.userCategories);
    }

}


