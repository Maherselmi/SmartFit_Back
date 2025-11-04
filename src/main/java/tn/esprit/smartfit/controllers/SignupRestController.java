package tn.esprit.smartfit.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.smartfit.entities.Admin;
import tn.esprit.smartfit.entities.Client;
import tn.esprit.smartfit.services.SignupService;
@RestController
@RequestMapping("/api/signup")
@CrossOrigin(origins = "http://localhost:4200")
public class SignupRestController {
    private final SignupService signupService;

    public SignupRestController(SignupService signupService) {
        this.signupService = signupService;
    }



    @PostMapping("/client")
    public ResponseEntity<Client> registerClient(@RequestBody Client client) {
        Client savedClient = signupService.registerClient(client);
        return ResponseEntity.ok(savedClient);
    }
    @PostMapping("/admin")
    public ResponseEntity<Admin> registerAdmin(@RequestBody Admin admin) {
        Admin savedAdmin = signupService.registreAdmin(admin);
        return ResponseEntity.ok(savedAdmin);
    }
}
