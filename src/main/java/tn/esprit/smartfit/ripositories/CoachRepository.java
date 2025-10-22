package tn.esprit.smartfit.ripositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.smartfit.entities.Coach;

import java.util.Optional;

public interface CoachRepository extends JpaRepository<Coach, Long> {
    Optional<Coach> findById(Long coachId);

    Optional<Coach> findByEmail(String email);
}
