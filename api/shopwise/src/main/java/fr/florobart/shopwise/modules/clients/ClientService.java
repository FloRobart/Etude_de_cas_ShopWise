package fr.florobart.shopwise.modules.clients;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.common.hash.Hashing;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    /**
     * Constructor for ClientService
     *
     * @param clientRepository The repository injected by Spring
     */
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    public Client getById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client introuvable avec l'ID : " + id));
    }

    public Client getByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    public Client create(Client client) {
        client.setId(null);
        return clientRepository.save(client);
    }

    public Client update(Long id, Client clientDetails) {
        Client client = getById(id);
        client.setNom(clientDetails.getNom());
        client.setPrenom(clientDetails.getPrenom());
        client.setEmail(clientDetails.getEmail());
        client.setPhone(clientDetails.getPhone());

        if (clientDetails.getHashPassword() != null && !clientDetails.getHashPassword().isEmpty()) {
            String sha256hex = Hashing.sha256()
                    .hashString(clientDetails.getHashPassword(), StandardCharsets.UTF_8)
                    .toString();
            client.setHashPassword(sha256hex);
        } else {
            client.setHashPassword(null);
        }

        return clientRepository.save(client);
    }

    public void delete(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new RuntimeException("Client introuvable avec l'ID : " + id);
        }
        clientRepository.deleteById(id);
    }
}
