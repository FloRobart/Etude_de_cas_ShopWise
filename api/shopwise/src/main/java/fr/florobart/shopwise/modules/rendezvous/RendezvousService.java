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

    public List<Rendezvous> getByCommercantId(Long id) {
        try {
            return repository.findByCommercantId(id);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des rezndez-vous avec l'ID de commerçant : " + id, e);
        }
    }

    public List<Rendezvous> getByClientId(Long id) {
        try {
            return repository.findByClientId(id);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des rezndez-vous avec l'ID de client : " + id, e);
        }
    }

    public Rendezvous create(Rendezvous rendezvous) {
        rendezvous.setId(null);
        rendezvous.setStatus(rendezvous.getStatus().toLowerCase());
        return repository.save(rendezvous);
    }

    public Rendezvous update(Long id, Rendezvous rendezvous) {
        // Add automatica
        if (rendezvous.getStatus().toLowerCase().equals("completed")) {
            rendezvous.setFidelityPoints(rendezvous.getFidelityPoints() + 10);
        }

        Rendezvous client = getById(id);
        client.setClientId(rendezvous.getClientId());
        client.setCommercantId(rendezvous.getCommercantId());
        client.setAppointmentDate(rendezvous.getAppointmentDate());
        client.setServiceType(rendezvous.getServiceType());
        client.setStatus(rendezvous.getStatus().toLowerCase());
        client.setFidelityPoints(rendezvous.getFidelityPoints());
        return repository.save(client);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Client introuvable avec l'ID : " + id);
        }
        repository.deleteById(id);
    }
}
