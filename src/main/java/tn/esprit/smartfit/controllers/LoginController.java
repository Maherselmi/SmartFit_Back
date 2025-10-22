package tn.esprit.smartfit.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.smartfit.entities.Client;
import tn.esprit.smartfit.entities.Coach;
import tn.esprit.smartfit.services.loginService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class LoginController {


    private final loginService loginservice;




    @PostMapping("/login/coach")
    public Coach loginCoach(@RequestBody Coach coach) throws Exception {
        return loginservice.loginCoach(coach.getEmail(), coach.getPassword());
    }

    @PostMapping("/login/client")
    public Client loginClient(@RequestBody Client client) throws Exception {
        return loginservice.loginClient(client.getEmail(), client.getPassword());
    }

}
