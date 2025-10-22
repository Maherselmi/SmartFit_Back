package tn.esprit.smartfit.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.smartfit.entities.Admin;
import tn.esprit.smartfit.entities.Client;
import tn.esprit.smartfit.entities.Coach;
import tn.esprit.smartfit.ripositories.AdminRepo;
import tn.esprit.smartfit.ripositories.ClientRepository;
import tn.esprit.smartfit.ripositories.CoachRepository;

@Service
@RequiredArgsConstructor


public class loginService {
    private final CoachRepository coachRepo;


    private final   ClientRepository clientRepo;
    private final AdminRepo adminRepo;


    public Coach loginCoach(String email, String password) throws Exception {
        Coach coach = coachRepo.findByEmail(email)
                .orElseThrow(() -> new Exception("Email introuvable"));
        if (!coach.getPassword().equals(password)) throw new Exception("Mot de passe incorrect");
        return coach;
    }

    public Client loginClient(String email, String password) throws Exception {
        // Déballer l'Optional avec orElseThrow
        Client client = clientRepo.findByEmail(email)
                .orElseThrow(() -> new Exception("Email introuvable"));

        if (!client.getPassword().equals(password)) {
            throw new Exception("Mot de passe incorrect");
        }

        return client;
    }

    public Admin loginAdmin(String email, String password) throws Exception {
        Admin admin = adminRepo.findByEmail(email)
                .orElseThrow(() -> new Exception("Email introuvable"));
        if (!admin.getPassword().equals(password)) throw new Exception("Mot de passe incorrect");
        return admin;
    }

}
