package tn.esprit.smartfit.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.smartfit.entities.Coach;
import tn.esprit.smartfit.ripositories.CoachRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class CoachServiceImpl implements CoachService {

    private final CoachRepository coachRepository;

    @Override
    public Coach addCoach(Coach coach) {
        return coachRepository.save(coach);
    }

    @Override
    public Coach updateCoach(Long id, Coach coach) {
        Coach existingCoach = coachRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coach non trouvé avec l'id : " + id));
        existingCoach.setNom(coach.getNom());
        existingCoach.setPrenom(coach.getPrenom());
        existingCoach.setEmail(coach.getEmail());
        existingCoach.setTelephone(coach.getTelephone());
        existingCoach.setSpecialite(coach.getSpecialite());
        return coachRepository.save(existingCoach);
    }

    @Override
    public void deleteCoach(Long id) {
        coachRepository.deleteById(id);
    }

    @Override
    public Coach getCoachById(Long id) {
        return coachRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coach non trouvé avec l'id : " + id));
    }

    @Override
    public List<Coach> getAllCoachs() {
        return coachRepository.findAll();
    }
}
