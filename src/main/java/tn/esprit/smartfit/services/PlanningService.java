package tn.esprit.smartfit.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.smartfit.entities.Planning;
import tn.esprit.smartfit.ripositories.PlanningRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlanningService {

    private final PlanningRepository planningRepository;

    // Ajouter une séance
    public Planning addSeance(Planning planning) {
        return planningRepository.save(planning);
    }

    // Afficher toutes les séances
    public List<Planning> getAllSeances() {
        return planningRepository.findAll();
    }

    // Trouver une séance par ID
    public Optional<Planning> getSeanceById(Long id) {
        return planningRepository.findById(id);
    }

    // Supprimer une séance
    public void deleteSeance(Long id) {
        planningRepository.deleteById(id);
    }
}
