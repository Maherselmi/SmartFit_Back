package tn.esprit.smartfit.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;
@Entity
@Data
public class Repas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type; // "Petit-déjeuner", "Déjeuner", "Dîner", "Snack"
    private double caloriesTotales;
    private double proteinesTotales;
    private double glucidesTotales;
    private double lipidesTotales;

    @ManyToOne
    @JoinColumn(name = "plan_id")
    @JsonBackReference // ADD THIS

    private PlanAlimentaire planAlimentaire;

    @OneToMany(mappedBy = "repas", cascade = CascadeType.ALL)
    @JsonManagedReference // ADD THIS
    private List<ComposantRepas> composants = new ArrayList<>();

    // Met à jour les totaux basés sur les composants
    public void calculerTotaux() {
        this.caloriesTotales = composants.stream().mapToDouble(ComposantRepas::getCalories).sum();
        this.proteinesTotales = composants.stream().mapToDouble(ComposantRepas::getProteines).sum();
        this.glucidesTotales = composants.stream().mapToDouble(ComposantRepas::getGlucides).sum();
        this.lipidesTotales = composants.stream().mapToDouble(ComposantRepas::getLipides).sum();
    }

}
