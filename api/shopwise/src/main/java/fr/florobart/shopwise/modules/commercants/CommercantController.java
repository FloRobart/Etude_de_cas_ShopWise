package fr.florobart.shopwise.modules.commercants;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.florobart.shopwise.modules.clients.Client;

@RestController
@RequestMapping("/commercants")
public class CommercantController {

    private final CommercantService service;

    /**
     * Constructor for ClientController
     * @param commercantService The service injected by Spring
     */
    public CommercantController(CommercantService commercantService) {
        this.service = commercantService;
    }

    @GetMapping
    public List<Commercant> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Commercant> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<Commercant> create(@RequestBody Commercant commercant) {
        Commercant createdCommercant = service.create(commercant);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCommercant);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Commercant> update(@PathVariable Long id, @RequestBody Commercant commercant) {
        Commercant updatedCommercant = service.update(id, commercant);
        return ResponseEntity.ok(updatedCommercant);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
