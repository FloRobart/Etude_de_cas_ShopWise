package fr.florobart.shopwise.modules.commercants;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CommercantService {
    private final CommercantRepository repository;

    /**
     * Constructor for CommercantService
     * @param commercantRepository The repository injected by Spring
     */
    public CommercantService(CommercantRepository commercantRepository) {
        this.repository = commercantRepository;
    }

    public List<Commercant> getAll() {
        return repository.findAll();
    }

    public Commercant getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commercant introuvable avec l'ID : " + id));
    }

    public Commercant create(Commercant commercant) {
        commercant.setId(null);
        return repository.save(commercant);
    }

    public Commercant update(Long id, Commercant commercantDetails) {
        Commercant commercant = getById(id);
        commercant.setNom(commercantDetails.getNom());
        return repository.save(commercant);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Commercant introuvable avec l'ID : " + id);
        }
        repository.deleteById(id);
    }
}
