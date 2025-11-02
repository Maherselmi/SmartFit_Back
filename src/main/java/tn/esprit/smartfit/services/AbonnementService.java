package tn.esprit.smartfit.services;

import tn.esprit.smartfit.entities.Abonnement;

import java.util.List;
import java.util.Optional;

public interface AbonnementService {

    List<Abonnement> getAllAbonnements();

    Optional<Abonnement> getAbonnementById(Long id);

    Abonnement createAbonnement(Abonnement abonnement);

    Abonnement updateAbonnement(Long id, Abonnement abonnement);

    void deleteAbonnement(Long id);
}