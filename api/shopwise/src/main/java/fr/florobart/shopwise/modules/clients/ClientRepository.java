package fr.florobart.shopwise.modules.clients;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    List<Client> findByNomContainingIgnoreCase(String nom);

    Client findByEmail(String email);
}
