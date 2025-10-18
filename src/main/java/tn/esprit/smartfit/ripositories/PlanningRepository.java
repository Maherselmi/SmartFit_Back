package tn.esprit.smartfit.ripositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.smartfit.entities.Planning;

@Repository
public interface PlanningRepository extends JpaRepository<Planning, Long> {
}
