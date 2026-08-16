package fr.florobart.shopwise.modules.rendezvous;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RendezvousRepository extends JpaRepository<Rendezvous, Long> {

    // 1. Méthode dérivée (génère: SELECT * FROM products WHERE name ILIKE %:name%)
    List<Rendezvous> findByCommercantIdContainingIgnoreCase(Long commercantId);
}
