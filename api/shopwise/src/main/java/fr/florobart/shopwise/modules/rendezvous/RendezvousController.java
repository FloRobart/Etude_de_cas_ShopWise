package fr.florobart.shopwise.modules.rendezvous;

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

@RestController
@RequestMapping("/rendez-vous")
public class RendezvousController {

    private final RendezvousService service;

    /**
     * Constructor for ClientController
     * @param rendezvousService The service injected by Spring
     */
    public RendezvousController(RendezvousService rendezvousService) {
        this.service = rendezvousService;
    }

    @GetMapping
    public List<Rendezvous> getAll() {
        try {
            return service.getAll();
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while fetching clients", e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rendezvous> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<Rendezvous> create(@RequestBody Rendezvous client) {
        Rendezvous createdClient = service.create(client);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdClient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rendezvous> update(@PathVariable Long id, @RequestBody Rendezvous client) {
        Rendezvous updatedClient = service.update(id, client);
        return ResponseEntity.ok(updatedClient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
