package tn.esprit.smartfit.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.smartfit.ripositories.*;
import tn.esprit.smartfit.entities.Client;
import tn.esprit.smartfit.entities.PlanAlimentaire;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private PlanAlimentaireService planAlimentaireService;

    @Autowired
    private MealPlanAIService mealPlanAIService;

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Optional<Client> getClientById(Long id) {
        return clientRepository.findById(id);
    }

    public Client createClient(Client client) {
        return clientRepository.save(client);
    }

    public Client updateClient(Long id, Client clientDetails) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        if (optionalClient.isPresent()) {
            Client client = optionalClient.get();
            client.setNom(clientDetails.getNom());
            client.setAge(clientDetails.getAge());
            client.setPoids(clientDetails.getPoids());
            client.setObjectif(clientDetails.getObjectif());
            client.setProgressionEstimee(clientDetails.getProgressionEstimee());
            return clientRepository.save(client);
        }
        return null;
    }

    public boolean deleteClient(Long id) {
        if (clientRepository.existsById(id)) {
            clientRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Client> getClientsByObjectif(String objectif) {
        return clientRepository.findByObjectif(objectif);
    }


    public PlanAlimentaire generateAIMealPlan(Long clientId) {
        Optional<Client> clientOpt = clientRepository.findById(clientId);
        if (clientOpt.isPresent()) {
            Client client = clientOpt.get();
            PlanAlimentaire aiPlan = mealPlanAIService.generateMealPlan(client);
            return planAlimentaireService.createPlan(aiPlan);
        }
        throw new RuntimeException("Client not found");
    }

    public List<PlanAlimentaire> getClientMealPlans(Long clientId) {
        return planAlimentaireService.getPlansByClient(clientId);
    }
    // REMOVE all the AI meal plan generation methods from here
    // They are already in MealPlanService
}