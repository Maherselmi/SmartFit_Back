package tn.esprit.smartfit.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import tn.esprit.smartfit.services.AbonnementService;
import tn.esprit.smartfit.entities.Abonnement;
import tn.esprit.smartfit.ripositories.AbonnementRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AbonnementServiceImpl implements AbonnementService {

    private final AbonnementRepository abonnementRepository;

    @Override
    public List<Abonnement> getAllAbonnements() {
        return abonnementRepository.findAll();
    }

    @Override
    public Optional<Abonnement> getAbonnementById(Long id) {
        return abonnementRepository.findById(id);
    }

    @Override
    public Abonnement createAbonnement(Abonnement abonnement) {
        return abonnementRepository.save(abonnement);
    }

    @Override
    public Abonnement updateAbonnement(Long id, Abonnement abonnement) {
        return abonnementRepository.findById(id)
                .map(existing -> {
                    existing.setTypeAbonnement(abonnement.getTypeAbonnement());
                    existing.setPrix(abonnement.getPrix());
                    existing.setDateDebut(abonnement.getDateDebut());
                    existing.setDateFin(abonnement.getDateFin());
                    existing.setStatut(abonnement.getStatut());
                    existing.setModePaiement(abonnement.getModePaiement());
                    existing.setRenouvellementAuto(abonnement.getRenouvellementAuto());
                    return abonnementRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Abonnement non trouvé"));
    }

    @Override
    public void deleteAbonnement(Long id) {
        abonnementRepository.deleteById(id);
    }
}