package wllt.dto;

import wllt.entities.Transaction;
import wllt.entities.types.CategoryType;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * The class maps a {@link Transaction} object.
 *
 */
public class TransactionDTO implements Serializable {
    private Integer ID;
    private UserDTO user;
    private CategoryDTO category;
    private Double sum;
    private LocalDate date;

    public TransactionDTO() {
    }

    public TransactionDTO(Integer ID, UserDTO user, CategoryDTO category, Double sum, LocalDate date) {
        this.ID = ID;
        this.user = user;
        this.category = category;
        this.sum = sum;
        this.date = date;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
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

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionDTO that = (TransactionDTO) o;
        return Objects.equals(ID, that.ID) &&
                Objects.equals(user, that.user) &&
                Objects.equals(category, that.category) &&
                Objects.equals(sum, that.sum) &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, user, category, sum, date);
    }

    @Override
    public String toString() {
        return "TransactionDTO{" +
                "ID=" + ID +
                ", user=" + user +
                ", category=" + category +
                ", sum=" + sum +
                ", date=" + date +
                '}';
    }
}
