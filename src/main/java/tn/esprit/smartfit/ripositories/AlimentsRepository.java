package tn.esprit.smartfit.ripositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.smartfit.entities.Aliments;
import java.util.Optional;

public interface AlimentsRepository extends JpaRepository<Aliments, Long> {
    Optional<Aliments> findByNomContainingIgnoreCase(String nom);
}