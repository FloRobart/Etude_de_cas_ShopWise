package fr.florobart.shopwise.modules.clients;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    // 1. Méthode dérivée (génère: SELECT * FROM products WHERE name ILIKE %:name%)
    List<Client> findByNomContainingIgnoreCase(String nom);
}
