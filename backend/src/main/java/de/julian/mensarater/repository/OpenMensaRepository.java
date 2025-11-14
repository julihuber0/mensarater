package de.julian.mensarater.repository;

import de.julian.mensarater.entity.OpenMensa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OpenMensaRepository extends JpaRepository<OpenMensa, Long> {
    OpenMensa findOpenMensaByMensaId(long mensaId);
}
