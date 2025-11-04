package tn.esprit.smartfit.services;

import tn.esprit.smartfit.entities.Coach;

import java.util.List;

public interface CoachService {
    Coach addCoach(Coach coach);
    Coach updateCoach(Long id, Coach coach);
    void deleteCoach(Long id);
    Coach getCoachById(Long id);
    List<Coach> getAllCoachs();
}
