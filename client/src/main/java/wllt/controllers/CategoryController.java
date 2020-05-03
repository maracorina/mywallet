package wllt.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wllt.dto.CategoryDTO;
import wllt.jwt.config.JwtTokenUtil;
import wllt.manager.remote.CategoryManager;

import javax.servlet.http.HttpServlet;
import java.util.List;

/**
 * REST Controller for Category manipulation.
 *
 */
@RestController
@RequestMapping(path = "/categories")
public class CategoryController extends HttpServlet {

    @Autowired
    public CategoryManager categoryManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public CategoryController(CategoryManager categoryManager) {
        this.categoryManager = categoryManager;
    }

    /**
     * The Controller returns an array of {@link CategoryDTO} Objects that
     *      * map all the {@link wllt.entities.Category} objects from the database
     *
     * @param token the token
     * @return a success response containing the array
     */
    @GetMapping(path="/user", produces = "application/json")
    public ResponseEntity<?> getUserCategories(@RequestHeader(name="Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            String username = this.jwtTokenUtil.getUsernameFromToken(token);
            if (username != null) {
                List<CategoryDTO> categoryDTOList = this.categoryManager.getAllOutCategoriesByUsername(username);
                return ResponseEntity.status(200).body(categoryDTOList);
            } else {
                return ResponseEntity.status(401).body("Unauthorized");
            }
        } else {
            return ResponseEntity.status(401).body("Unauthorized");
        }
    }

}
