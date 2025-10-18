package tn.esprit.smartfit.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.smartfit.entities.Planning;
import tn.esprit.smartfit.entities.Client;
import tn.esprit.smartfit.entities.Coach;
import tn.esprit.smartfit.ripositories.ClientRepository;
import tn.esprit.smartfit.ripositories.CoachRepository;
import tn.esprit.smartfit.services.PlanningService;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")

@RequestMapping("/api/plannings")
@RequiredArgsConstructor
public class PlanningController {

    private final PlanningService planningService;
    private final ClientRepository clientRepository;
    private final  CoachRepository coachRepository;

    // ✅ Ajouter une séance
    @PostMapping("/add")
    public ResponseEntity<?> addSeance(
            @RequestParam Long clientId,
            @RequestParam Long coachId,
            @RequestBody Planning planning) {

        Optional<Client> client = clientRepository.findById(clientId);
        Optional<Coach> coach = coachRepository.findById(coachId);

        if (client.isEmpty() || coach.isEmpty()) {
            return ResponseEntity.badRequest().body("Client ou Coach introuvable");
        }

        planning.setClient(client.get());
        planning.setCoach(coach.get());

        Planning saved = planningService.addSeance(planning);
        return ResponseEntity.ok(saved);
    }

    // 🔹 Afficher toutes les séances
    @GetMapping("/all")
    public List<Planning> getAllSeances() {
        return planningService.getAllSeances();
    }

    // 🔹 Supprimer une séance
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSeance(@PathVariable Long id) {
        planningService.deleteSeance(id);
        return ResponseEntity.ok("Séance supprimée avec succès !");
    }
}
