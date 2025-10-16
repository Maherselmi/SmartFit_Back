package tn.esprit.smartfit.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Data
@RequiredArgsConstructor
@Entity
@Table(name = "coachs")
public class Coach {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenom;
    private String specialite;
    private String email;
    private String telephone;
}
