package wllt.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wllt.dto.TransactionDTO;
import wllt.dto.UserCategoryDTO;
import wllt.entities.Transaction;
import wllt.exceptions.BusinessException;
import wllt.exceptions.ValidationException;
import wllt.jwt.config.JwtTokenUtil;
import wllt.manager.remote.TransactionsManager;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * REST Controller for Transaction manipulation.
 *
 * @author Mara Corina
 */
@RestController
@RequestMapping(path = "/transactions")
public class TransactionController extends HttpServlet {

    @Autowired
    public TransactionsManager transactionManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public TransactionController(TransactionsManager transactionManagerRemote) {
        this.transactionManager = transactionManagerRemote;
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public ResponseEntity<?> addTransaction(@RequestBody TransactionDTO transactionDTO){
        try{
            return ResponseEntity.ok(this.transactionManager.insertTransaction(transactionDTO));
        } catch (ValidationException e){
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (BusinessException e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    /**
     * The Controller returns an array of {@link TransactionDTO} Objects that
     *      * map all the {@link wllt.entities.Transaction} objects from the database
     *      * corresponding to the user
     *
     * @param token the token
     * @return a success response containing the array
     * @throws {@link BusinessException} bubbles up to here from {@link TransactionsManager}
     *      *                and we return an ERROR response to the client containing the thrown exception
     */
    @GetMapping(path="/user", produces = "application/json")
    public ResponseEntity<?> getUserTransactions(@RequestHeader (name="Authorization") String token){
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            String username = this.jwtTokenUtil.getUsernameFromToken(token);
            if (username != null) {
                try {
                    List<TransactionDTO> transactionDTOS = this.transactionManager.getUserTransactions(username);
                    return ResponseEntity.ok(transactionDTOS);
                } catch (BusinessException e){
                    return ResponseEntity.status(500).body(e.getMessage());
                }
            } else {
                return ResponseEntity.status(401).body("Unauthorized");
            }
        } else {
            return ResponseEntity.status(401).body("Unauthorized");
        }
    }

    /**
     * The Controller returns an array of {@link TransactionDTO} Objects that
     *      * map all the {@link wllt.entities.Transaction} objects from the database
     *      * corresponding to the user and category with the given id
     *
     * @param userId is an {@link Integer} the user id
     * @param categoryId is an {@link Integer} the category id
     * @return a success response containing the array
     * @throws {@link BusinessException} bubbles up to here from {@link TransactionsManager}
     *      *                and we return an ERROR response to the client containing the thrown exception
     */
    @GetMapping(path="/user/{userId}/category/{categoryId}", produces = "application/json")
    public ResponseEntity<?> getUserCategoryTransactions(@PathVariable("userId") Integer userId,
                                                            @PathVariable("categoryId") Integer categoryId){
        try {
            List<TransactionDTO> transactionDTOS = this.transactionManager.getUserCategoryTransactions(userId, categoryId);
            return ResponseEntity.ok(transactionDTOS);
        } catch (BusinessException e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }


    /**
     * The Controller consumes an id and returns an {@link TransactionDTO} Object that
     * maps the corresponding {@link Transaction} object data persisted in the database.
     *
     * @param id is an {@link Integer}
     * @return a success response containing the {@link TransactionDTO} object that maps the object
     * from teh database
     * @throws {@link BusinessException} bubbles up to here from {@link TransactionsManager}
     *                and we return an ERROR response to the client containing the thrown exception
     */
    @GetMapping(path="/{id}", produces = "application/json")
    public ResponseEntity<?> getTransaction(@PathVariable("id") Integer id) {
        try {
            TransactionDTO transactionDTO = this.transactionManager.findTransaction(id);
            return ResponseEntity.ok(transactionDTO);
        } catch (BusinessException e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }


    @GetMapping(path="/spent-amount", produces = "application/json")
    public ResponseEntity<?> getSpentAmount(@RequestHeader (name="Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            String username = this.jwtTokenUtil.getUsernameFromToken(token);
            if (username != null) {
                Double amount = this.transactionManager.getSpentAmountByUsername(username);
                return ResponseEntity.status(200).body(amount);
            } else {
                return ResponseEntity.status(401).body("Unauthorized");
            }
        } else {
            return ResponseEntity.status(401).body("Unauthorized");
        }
    }

    @GetMapping(path="/total-budget", produces = "application/json")
    public ResponseEntity<?> getTotalBudgetAmount(@RequestHeader (name="Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            String username = this.jwtTokenUtil.getUsernameFromToken(token);
            if (username != null) {
                Double amount = this.transactionManager.getIncomeAmountByUsername(username);
                return ResponseEntity.status(200).body(amount);
            } else {
                return ResponseEntity.status(401).body("Unauthorized");
            }
        } else {
            return ResponseEntity.status(401).body("Unauthorized");
        }
    }
}
