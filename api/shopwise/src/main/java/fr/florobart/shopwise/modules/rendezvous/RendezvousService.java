package fr.florobart.shopwise.modules.rendezvous;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class RendezvousService {

    private final RendezvousRepository repository;

    /**
     * Constructor for ClientService
     *
     * @param rendezvousRepository The repository injected by Spring
     */
    public RendezvousService(RendezvousRepository rendezvousRepository) {
        this.repository = rendezvousRepository;
    }

    public List<Rendezvous> getAll() {
        return repository.findAll();
    }

    public Rendezvous getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client introuvable avec l'ID : " + id));
    }

    public Rendezvous create(Rendezvous client) {
        client.setId(null);
        return repository.save(client);
    }

    public Rendezvous update(Long id, Rendezvous clientDetails) {
        Rendezvous client = getById(id);
        client.setClientId(clientDetails.getClientId());
        client.setCommercantId(clientDetails.getCommercantId());
        client.setAppointmentDate(clientDetails.getAppointmentDate());
        client.setServiceType(clientDetails.getServiceType());
        client.setStatus(clientDetails.getStatus());
        return repository.save(client);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Client introuvable avec l'ID : " + id);
        }
        repository.deleteById(id);
    }
}
