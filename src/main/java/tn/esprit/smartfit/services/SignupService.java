package tn.esprit.smartfit.services;

import org.springframework.stereotype.Service;
import tn.esprit.smartfit.entities.Client;
import tn.esprit.smartfit.entities.Coach;
import tn.esprit.smartfit.ripositories.ClientRepository;
import tn.esprit.smartfit.ripositories.CoachRepository;
@Service

public class SignupService {
    private final CoachRepository coachRepo;
    private final ClientRepository clientRepo;

    public SignupService(CoachRepository coachRepo, ClientRepository clientRepo) {
        this.coachRepo = coachRepo;
        this.clientRepo = clientRepo;
    }

    public Coach registerCoach(Coach coach) {
        coach.setRole("COACH");
        return coachRepo.save(coach);
    }

    public Client registerClient(Client client) {
        client.setRole("CLIENT");
        return clientRepo.save(client);
    }
}
