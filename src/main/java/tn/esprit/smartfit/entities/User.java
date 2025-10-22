package tn.esprit.smartfit.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)


public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;
    private String role;
}
