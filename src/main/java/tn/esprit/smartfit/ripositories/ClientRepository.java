package tn.esprit.smartfit.ripositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.smartfit.entities.Client;

import java.util.List;
import java.util.Optional;
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findById(Long clientId);

    Optional<Client> findByEmail(String email);

    List<Client> findByObjectif(String objectif);
}
