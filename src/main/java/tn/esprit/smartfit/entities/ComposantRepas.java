    package tn.esprit.smartfit.entities;

    import com.fasterxml.jackson.annotation.JsonBackReference;
    import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
    import jakarta.persistence.*;
    import lombok.Data;

    @Entity
    @Data

    public class ComposantRepas {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JsonIgnoreProperties({"composants"}) // ADD THIS
        private Aliments aliment; // Référence à l'aliment de base

        private double quantite; // 150g, 200ml, etc.
        private String description; // "Poulet grillé aux herbes"
        private String modeCuisson; // "GRILLE", "CUIT", "CRU"

        @ManyToOne
        @JoinColumn(name = "repas_id")
        @JsonIgnoreProperties({"composants"}) // ADD THIS
        private Repas repas;              // Repas parent

           // Méthodes pour calculer les nutriments de ce composant
           public double getCalories() {
            if (aliment == null) return 0;
            return (aliment.getCalories() * quantite) / 100;
        }

        public double getProteines() {
            if (aliment == null) return 0;
            return (aliment.getProteines() * quantite) / 100;
        }

        public double getGlucides() {
            if (aliment == null) return 0;
            return (aliment.getGlucides() * quantite) / 100;
        }

        public double getLipides() {
            if (aliment == null) return 0;
            return (aliment.getLipides() * quantite) / 100;
        }
    }