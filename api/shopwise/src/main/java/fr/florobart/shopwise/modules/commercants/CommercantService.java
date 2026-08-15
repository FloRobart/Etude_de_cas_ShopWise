package fr.florobart.shopwise.modules.commercants;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CommercantService {
    private final CommercantRepository commercantRepository;

    /**
     * Constructor for ClientService
     * @param commercantRepository The repository injected by Spring
     */
    public CommercantService(CommercantRepository commercantRepository) {
        this.commercantRepository = commercantRepository;
    }

    public List<Commercant> getAll() {
        return commercantRepository.findAll();
    }
}
