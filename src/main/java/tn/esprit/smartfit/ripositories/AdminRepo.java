package tn.esprit.smartfit.ripositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.smartfit.entities.Admin;

import java.util.Optional;

public interface AdminRepo extends JpaRepository<Admin, Long> {
    Optional<Admin> findById(Long adminId);

    Optional<Admin> findByEmail(String email);
}
