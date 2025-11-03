package tn.esprit.smartfit.controllers;



import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.smartfit.entities.Abonnement;
import tn.esprit.smartfit.services.AbonnementService;

import java.util.List;

@RestController
@RequestMapping("/api/abonnements")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AbonnementController {

    private final AbonnementService abonnementService;

    @GetMapping
    public List<Abonnement> getAll() {
        return abonnementService.getAllAbonnements();
    }

    @GetMapping("/{id}")
    public Abonnement getById(@PathVariable Long id) {
        return abonnementService.getAbonnementById(id)
                .orElseThrow(() -> new RuntimeException("Abonnement non trouvé"));
    }

    @PostMapping
    public Abonnement create(@RequestBody Abonnement abonnement) {
        return abonnementService.createAbonnement(abonnement);
    }

    @PutMapping("/{id}")
    public Abonnement update(@PathVariable Long id, @RequestBody Abonnement abonnement) {
        return abonnementService.updateAbonnement(id, abonnement);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        abonnementService.deleteAbonnement(id);
    }
}
