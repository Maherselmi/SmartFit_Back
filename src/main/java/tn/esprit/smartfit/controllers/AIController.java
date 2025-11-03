// AIController.java
package tn.esprit.smartfit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.smartfit.services.ClientService;
import tn.esprit.smartfit.entities.PlanAlimentaire;

import java.util.List;

@RestController
@RequestMapping("/api/ai")
@CrossOrigin(origins = "http://localhost:4200")
public class AIController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/generate-meal-plan/{clientId}")
    public ResponseEntity<?> generateAIMealPlan(@PathVariable Long clientId) {
        try {
            PlanAlimentaire plan = clientService.generateAIMealPlan(clientId);
            return ResponseEntity.ok(plan);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/meal-plans/{clientId}")
    public ResponseEntity<List<PlanAlimentaire>> getClientMealPlans(@PathVariable Long clientId) {
        List<PlanAlimentaire> plans = clientService.getClientMealPlans(clientId);
        return ResponseEntity.ok(plans);
    }
}