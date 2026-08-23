package fr.florobart.shopwise.modules.clients;

import java.nio.charset.StandardCharsets;
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

import com.google.common.hash.Hashing;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    /**
     * Constructor for ClientController
     * @param clientService The service injected by Spring
     */
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public List<Client> getAll() {
        return clientService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getById(@PathVariable Long id) {
        return ResponseEntity.ok(clientService.getById(id));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Client> getByEmail(@PathVariable String email) {
        return ResponseEntity.ok(clientService.getByEmail(email));
    }

    @PostMapping
    public ResponseEntity<Client> create(@RequestBody Client client) {
        Client createdClient = clientService.create(client);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdClient);
    }

    @PostMapping("/login")
    public ResponseEntity<Client> login(@RequestBody Client client) {
        Client existingClient = clientService.getById(client.getId());
        String sha256hex = Hashing.sha256().hashString(client.getHashPassword(), StandardCharsets.UTF_8).toString();
        if (existingClient != null && existingClient.getHashPassword().equals(sha256hex)) {
            return ResponseEntity.ok(existingClient);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> update(@PathVariable Long id, @RequestBody Client client) {
        Client updatedClient = clientService.update(id, client);
        return ResponseEntity.ok(updatedClient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        clientService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
