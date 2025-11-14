package de.julian.mensarater.repository;

import de.julian.mensarater.entity.MensaUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<MensaUser, Long> {
    MensaUser findByEmail(String email);
}
