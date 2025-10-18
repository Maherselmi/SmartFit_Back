package tn.esprit.smartfit.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "plannings")
public class Planning {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre; // exemple : "Séance de cardio"
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private String description; // optionnel

    @ManyToOne
    @JoinColumn(name = "coach_id", nullable = false)
    private Coach coach;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
}
