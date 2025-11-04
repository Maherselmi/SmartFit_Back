package tn.esprit.smartfit.services;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.smartfit.entities.Admin;
import tn.esprit.smartfit.entities.Client;
import tn.esprit.smartfit.ripositories.AdminRepo;
import tn.esprit.smartfit.ripositories.ClientRepository;

@Service
@RequiredArgsConstructor


public class loginService {



    private final   ClientRepository clientRepo;
    private final AdminRepo adminRepo;




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
    public static void logout(HttpSession session) {
        session.invalidate();
    }

}
