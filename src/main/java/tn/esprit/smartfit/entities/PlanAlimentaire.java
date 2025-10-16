package tn.esprit.smartfit.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class PlanAlimentaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom; // "Plan Minceur - Semaine 1"
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private double totalCaloriesCible; // calories target
    private boolean genereParIA;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private Client utilisateur;

    @OneToMany(mappedBy = "planAlimentaire", cascade = CascadeType.ALL)
    private List<Repas> repasList;
}
