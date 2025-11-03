package tn.esprit.smartfit.ripositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.smartfit.entities.PlanAlimentaire;

import java.util.List;

@Repository
public interface PlanAlimentaireRepository extends JpaRepository<PlanAlimentaire, Long> {
    List<PlanAlimentaire> findByClientId(Long clientId);
    List<PlanAlimentaire> findByGenereParIA(boolean genereParIA);
    List<PlanAlimentaire> findByTypePlan(String typePlan);
}

