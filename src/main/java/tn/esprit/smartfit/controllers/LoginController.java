package tn.esprit.smartfit.controllers;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.smartfit.entities.Admin;
import tn.esprit.smartfit.entities.Client;
import tn.esprit.smartfit.services.loginService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class LoginController {


    private final loginService loginservice;






    @PostMapping("/login/client")
    public Client loginClient(@RequestBody Client client) throws Exception {
        return loginservice.loginClient(client.getEmail(), client.getPassword());
    }

    @PostMapping("/login/admin")
    public Admin loginAdmin(@RequestBody Admin admin) throws Exception {
        return loginservice.loginAdmin(admin.getEmail(), admin.getPassword());
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session, HttpServletResponse response) {
        session.invalidate();

        response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        return ResponseEntity.ok("Déconnexion réussie ✅");
    }


}
