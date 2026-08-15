package fr.florobart.shopwise.modules.commercants;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/commercants")
public class CommercantController {

    private final CommercantService commercantService;

    /**
     * Constructor for ClientController
     * @param commercantService The service injected by Spring
     */
    public CommercantController(CommercantService commercantService) {
        this.commercantService = commercantService;
    }

    @GetMapping
    public List<Commercant> getAll() {
        try {
            return commercantService.getAll();
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while fetching clients", e);
        }
    }

    @PostMapping
    public void create() {
        try {
            // Logic to create a new user
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while fetching clients", e);
        }
    }

    @PutMapping
    public void update() {
        try {
            // Logic to update a user
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while fetching clients", e);
        }
    }

    @DeleteMapping
    public void delete() {
        try {
            // Logic to delete a user
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while fetching clients", e);
        }
    }
}
