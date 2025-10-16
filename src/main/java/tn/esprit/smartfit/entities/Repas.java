package tn.esprit.smartfit.entities;

import jakarta.persistence.*;

@Entity
public class Repas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type; // "Petit-déjeuner", "Déjeuner", "Dîner", "Snack"
    private double caloriesTotales;

    @ManyToOne
    @JoinColumn(name = "plan_id")
    private PlanAlimentaire planAlimentaire;


}
