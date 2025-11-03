package tn.esprit.smartfit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.smartfit.services.*;
import tn.esprit.smartfit.entities.PlanAlimentaire;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/plans-alimentaires")
@CrossOrigin(origins = "http://localhost:4200")
public class PlanAlimentaireController {
    @Autowired
    private PlanAlimentaireService planAlimentaireService;

    @GetMapping
    public List<PlanAlimentaire> getAllPlans() {
        return planAlimentaireService.getAllPlans();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanAlimentaire> getPlanById(@PathVariable Long id) {
        Optional<PlanAlimentaire> plan = planAlimentaireService.getPlanById(id);
        return plan.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public PlanAlimentaire createPlan(@RequestBody PlanAlimentaire plan) {
        return planAlimentaireService.createPlan(plan);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlanAlimentaire> updatePlan(@PathVariable Long id, @RequestBody PlanAlimentaire planDetails) {
        PlanAlimentaire updatedPlan = planAlimentaireService.updatePlan(id, planDetails);
        return updatedPlan != null ? ResponseEntity.ok(updatedPlan) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePlan(@PathVariable Long id) {
        boolean deleted = planAlimentaireService.deletePlan(id);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/client/{clientId}")
    public List<PlanAlimentaire> getPlansByClient(@PathVariable Long clientId) {
        return planAlimentaireService.getPlansByClient(clientId);
    }

    @GetMapping("/ai-generated")
    public List<PlanAlimentaire> getAIGeneratedPlans() {
        return planAlimentaireService.getAIGeneratedPlans();
    }
}