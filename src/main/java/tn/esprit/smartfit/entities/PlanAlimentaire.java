package tn.esprit.smartfit.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class PlanAlimentaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom; // "Plan Minceur - Semaine 1"
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private double totalCaloriesCible; // calories target
    private double proteinesCible;
    private double glucidesCible;
    private double lipidesCible;
    private boolean genereParIA;
    private String typePlan; // "MAIGRIR", "PRISE_MASSE", "EQUILIBRE"
    @ManyToOne
    @JoinColumn(name = "client_id")
    @JsonIgnoreProperties({"plansAlimentaires"}) // ADD THIS

    private Client client;

    @OneToMany(mappedBy = "planAlimentaire", cascade = CascadeType.ALL)
    @JsonManagedReference // ADD THIS

    private List<Repas> repas = new ArrayList<>();

    // Méthodes utilitaires
    public double getCaloriesConsommees() {
        return repas.stream().mapToDouble(Repas::getCaloriesTotales).sum();
    }

    public double getProgressionCalories() {
        if (totalCaloriesCible == 0) return 0;
        return (getCaloriesConsommees() / totalCaloriesCible) * 100;
    }
}
