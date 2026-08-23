package fr.florobart.shopwise.modules.commercants;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "commercants")
public class Commercant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom", nullable = false)
    private String nom;

    public Commercant() {}

    public Commercant(Long id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    /*=========*/
    /* Getters */
    /*=========*/
    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    /*=========*/
    /* Setters */
    /*=========*/
    public void setId(Long id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
