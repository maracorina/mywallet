package wllt.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import wllt.AuthResponse;
import wllt.dto.TokenDTO;
import wllt.dto.UserDTO;
import wllt.entities.Token;
import wllt.exceptions.BusinessException;
import wllt.exceptions.ValidationException;
import wllt.jwt.config.JwtTokenUtil;
import wllt.jwt.model.JwtRegisterRequest;
import wllt.jwt.model.JwtRequest;
import wllt.jwt.service.JwtService;
import wllt.manager.remote.UserManager;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private JwtService userDetailsService;
    @Autowired
    public UserManager userManager;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest){

        return getJwtResponseEntity(authenticationRequest.getUsername(), authenticationRequest.getPassword());

    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO){
        try{
            UserDTO user = this.userManager.insertUser(userDTO);

            return getJwtResponseEntity(user.getUsername(), userDTO.getPassword());

        } catch (ValidationException e){
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (BusinessException e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }


    private ResponseEntity<?> getJwtResponseEntity(String username, String password){
        try {

            authenticate(username, password);

            final UserDetails userDetails = userDetailsService
                    .loadUserByUsername(username);

            final String token = jwtTokenUtil.generateToken(userDetails);
            Token t = userDetailsService.insertToken(new TokenDTO(token, userDetails.getUsername()));

            UserDTO loggedUser = userManager.findUserByUsernameAndPassword(username, password);

            AuthResponse reponse = new AuthResponse(t.getToken(), loggedUser);

            return ResponseEntity.ok(reponse);

        } catch (Exception e){
            try{
                userManager.findUserByUsername(username);
                return ResponseEntity.status(401).body("No user with this username and password was found!");
            } catch (BusinessException be){
                return ResponseEntity.status(401).body(be.getMessage());
            }
        }
    }


    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
