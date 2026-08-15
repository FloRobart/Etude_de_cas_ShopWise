package fr.florobart.shopwise.modules.commercants;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommercantRepository extends JpaRepository<Commercant, Long> {

    // 1. Méthode dérivée (génère: SELECT * FROM products WHERE name ILIKE %:name%)
    List<Commercant> findByNomContainingIgnoreCase(String nom);

    // // 2. Requête JPQL personnalisée
    // @Query("SELECT p FROM commercant p WHERE p.price <= :maxPrice")
    // List<Commercant> findCheapProducts(@Param("maxPrice") BigDecimal maxPrice);

    // // 3. Requête SQL native PostgreSQL
    // @Query(value = "SELECT * FROM commercants ORDER BY price DESC LIMIT 5", nativeQuery = true)
    // List<Commercant> findTop5MostExpensive();
}