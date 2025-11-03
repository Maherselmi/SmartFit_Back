package tn.esprit.smartfit.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.smartfit.ripositories.*;
import tn.esprit.smartfit.entities.Client;
import tn.esprit.smartfit.entities.PlanAlimentaire;

import java.util.List;
import java.util.Optional;

@Service
public class PlanAlimentaireService {
    @Autowired
    private PlanAlimentaireRepository planAlimentaireRepository;

    @Autowired
    private ClientRepository clientRepository;

    public List<PlanAlimentaire> getAllPlans() {
        return planAlimentaireRepository.findAll();
    }

    public Optional<PlanAlimentaire> getPlanById(Long id) {
        return planAlimentaireRepository.findById(id);
    }

    public PlanAlimentaire createPlan(PlanAlimentaire plan) {
        // Vérifier si le client existe
        if (plan.getClient() != null && plan.getClient().getId() != null) {
            Optional<Client> client = clientRepository.findById(plan.getClient().getId());
            if (client.isPresent()) {
                plan.setClient(client.get());
            }
        }
        return planAlimentaireRepository.save(plan);
    }

    public PlanAlimentaire updatePlan(Long id, PlanAlimentaire planDetails) {
        Optional<PlanAlimentaire> optionalPlan = planAlimentaireRepository.findById(id);
        if (optionalPlan.isPresent()) {
            PlanAlimentaire plan = optionalPlan.get();
            plan.setNom(planDetails.getNom());
            plan.setDateDebut(planDetails.getDateDebut());
            plan.setDateFin(planDetails.getDateFin());
            plan.setTotalCaloriesCible(planDetails.getTotalCaloriesCible());
            plan.setProteinesCible(planDetails.getProteinesCible());
            plan.setGlucidesCible(planDetails.getGlucidesCible());
            plan.setLipidesCible(planDetails.getLipidesCible());
            plan.setGenereParIA(planDetails.isGenereParIA());
            plan.setTypePlan(planDetails.getTypePlan());
            return planAlimentaireRepository.save(plan);
        }
        return null;
    }

    public boolean deletePlan(Long id) {
        if (planAlimentaireRepository.existsById(id)) {
            planAlimentaireRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<PlanAlimentaire> getPlansByClient(Long clientId) {
        return planAlimentaireRepository.findByClientId(clientId);
    }

    public List<PlanAlimentaire> getAIGeneratedPlans() {
        return planAlimentaireRepository.findByGenereParIA(true);
    }
}