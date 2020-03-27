package wllt.dto;


import wllt.entities.Token;

import java.io.Serializable;

/**
 * The class maps a {@link Token} object.
 *
 */
public class TokenDTO implements Serializable {
    private Integer id;
    private String token;
    private String username;

    public TokenDTO(String token, String username) {
        this.token = token;
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
