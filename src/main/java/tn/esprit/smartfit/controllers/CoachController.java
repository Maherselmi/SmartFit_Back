package tn.esprit.smartfit.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.smartfit.entities.Coach;
import tn.esprit.smartfit.services.CoachService;

import java.util.List;

@RestController
@RequestMapping("/api/coachs")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class CoachController {

     CoachService coachService;

    @PostMapping
    public Coach addCoach(@RequestBody Coach coach) {
        return coachService.addCoach(coach);
    }

    @PutMapping("/{id}")
    public Coach updateCoach(@PathVariable Long id, @RequestBody Coach coach) {
        return coachService.updateCoach(id, coach);
    }

    @DeleteMapping("/{id}")
    public void deleteCoach(@PathVariable Long id) {
        coachService.deleteCoach(id);
    }

    @GetMapping("/{id}")
    public Coach getCoachById(@PathVariable Long id) {
        return coachService.getCoachById(id);
    }

    @GetMapping
    public List<Coach> getAllCoachs() {
        return coachService.getAllCoachs();
    }
}
