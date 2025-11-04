package tn.esprit.smartfit.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.smartfit.entities.Admin;
import tn.esprit.smartfit.entities.Client;
import tn.esprit.smartfit.ripositories.AdminRepo;
import tn.esprit.smartfit.ripositories.ClientRepository;

@Service
@RequiredArgsConstructor

public class SignupService {
    private final ClientRepository clientRepo;
    private final AdminRepo adminRepo;





    public Client registerClient(Client client) {
        client.setRole("CLIENT");
        return clientRepo.save(client);
    }
    public Admin registreAdmin(Admin admin) {
        admin.setRole("Admin");
        return adminRepo.save(admin);
    }
}
