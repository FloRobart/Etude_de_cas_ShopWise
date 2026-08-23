package fr.florobart.shopwise.modules.rendezvous;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RendezvousRepository extends JpaRepository<Rendezvous, Long> {
    List<Rendezvous> findByCommercantId(Long commercantId);

    List<Rendezvous> findByClientId(Long clientId);
}
