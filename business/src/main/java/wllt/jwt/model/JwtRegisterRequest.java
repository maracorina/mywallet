package wllt.jwt.model;

import wllt.dto.UserDTO;

import java.io.Serializable;

public class JwtRegisterRequest implements Serializable {

    private UserDTO user;

    public JwtRegisterRequest() {
    }

    public JwtRegisterRequest(UserDTO user) {
        this.user = user;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
}
