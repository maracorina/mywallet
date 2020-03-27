package wllt.jwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import wllt.dao.UserDao;
import wllt.dto.TokenDTO;
import wllt.entities.Token;

import java.util.ArrayList;

@Component
public class JwtService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    public Token insertToken(TokenDTO tokenDTO) {
        return createTokenToInsert(tokenDTO);
    }

    private Token createTokenToInsert(TokenDTO tokenDTO) {
        Token token = new Token(tokenDTO.getToken(), tokenDTO.getUsername());
        return token;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        wllt.entities.User user = userDao.findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        else {
            return new User(user.getUsername(),
                    new BCryptPasswordEncoder().encode(user.getPassword()),
                    new ArrayList<>());
        }
    }
}
