package wllt.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wllt.dto.TransactionDTO;
import wllt.dto.UserCategoryDTO;
import wllt.dto.UserDTO;
import wllt.entities.Transaction;
import wllt.exceptions.BusinessException;
import wllt.exceptions.ValidationException;
import wllt.manager.remote.TransactionsManager;
import wllt.manager.remote.UserManager;

import javax.servlet.http.HttpServlet;

/**
 * REST Controller for User manipulation.
 *
 */
@RestController
@RequestMapping(path = "/users")
public class UserController extends HttpServlet {

    @Autowired
    public UserManager userManager;

    public UserController(UserManager userManager) {
        this.userManager = userManager;
    }

    @RequestMapping(value = "/new-category", method = RequestMethod.POST)
    public ResponseEntity<?> registerUser(@RequestBody UserCategoryDTO userCategoryDTO){
        try{
            return ResponseEntity.ok(this.userManager.insertUserCategory(userCategoryDTO));
        } catch (ValidationException e){
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (BusinessException e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/category-update", method = RequestMethod.POST)
    public ResponseEntity<?> updateCategoryPriority(@RequestBody UserCategoryDTO userCategoryDTO){
        try{
            return ResponseEntity.ok(this.userManager.updateCategoryPriority(userCategoryDTO));
        } catch (ValidationException e){
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (BusinessException e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

}
