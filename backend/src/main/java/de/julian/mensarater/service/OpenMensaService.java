package de.julian.mensarater.service;

import de.julian.mensarater.dto.MensaDTO;
import de.julian.mensarater.entity.OpenMensa;
import de.julian.mensarater.repository.OpenMensaRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OpenMensaService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final OpenMensaRepository openMensaRepository;

    public List<MensaDTO> getAllMensas() {
        logger.info("Fetching all mensas from OpenMensa repository");
        return openMensaRepository.findAll().stream()
                .map(openMensa -> new MensaDTO(openMensa.getName(), openMensa.getMensaId()))
                .toList();
    }

    public MensaDTO getMensaById(long mensaId) {
        logger.info("Fetching mensa with OpenMensa-ID: {}", mensaId);
        OpenMensa openMensa = openMensaRepository.findOpenMensaByMensaId(mensaId);
        return openMensa != null ?
                new MensaDTO(openMensa.getName(), openMensa.getMensaId()) :
                null;
    }
}
