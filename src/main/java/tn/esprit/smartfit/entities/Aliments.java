package tn.esprit.smartfit.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Aliments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private double calories;
    private double proteines;
    private double glucides;
    private double lipides;
    private double quantite; // in grams

    @ManyToOne
    @JoinColumn(name = "repas_id")
    private Repas repas;
}