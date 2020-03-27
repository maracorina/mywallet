package wllt.entities;


import wllt.utils.LocalDateConverter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * @author Mara Corina
 */
@Entity
@Table(name = "transactions")
public class Transaction{

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer ID;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="user_id",referencedColumnName = "ID")
    private User user;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="category_id",referencedColumnName = "ID")
    private Category category;

    @Column(name = "sum")
    private Double sum;

    @Column(name = "date")
    @Convert(converter = LocalDateConverter.class)
    private LocalDate date;

    public Transaction() {
    }

    public Transaction(User user, Category category, Double sum, LocalDate date) {
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
        Transaction that = (Transaction) o;
        return Objects.equals(ID, that.ID) &&
                Objects.equals(user, that.user) &&
                Objects.equals(category, that.category) &&
                Objects.equals(sum, that.sum) &&
                Objects.equals(date, that.date);
    }

}
