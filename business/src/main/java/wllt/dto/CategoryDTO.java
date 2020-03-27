package wllt.dto;

import wllt.entities.types.CategoryType;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

public class CategoryDTO implements Serializable {

    private Integer ID;
    private CategoryType type;
    private String name;

    public CategoryDTO() {
    }

    public CategoryDTO(Integer ID, CategoryType type, String name) {
        this.ID = ID;
        this.type = type;
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryDTO that = (CategoryDTO) o;
        return Objects.equals(ID, that.ID) &&
                type == that.type &&
                Objects.equals(name, that.name);
    }
}
