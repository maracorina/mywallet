package wllt.dto;
import wllt.entities.User;
import wllt.entities.types.StatusType;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;


/**
 * The class maps a {@link User} object.
 *
 */
public class UserDTO implements Serializable {

    private Integer ID;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private Integer counter;
    private String email;
    private String mobileNumber;
    private StatusType status;

    public UserDTO() {
    }

    public UserDTO(Integer ID, String firstName, String lastName, String username, String password, Integer counter, String email, String mobileNumber, StatusType status) {
        this.ID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.counter = counter;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.status = status;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer id) {
        this.ID = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getCounter() {
        return counter;
    }

    public void setCounter(Integer counter) {
        this.counter = counter;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public StatusType getStatus() {
        return status;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }
}
