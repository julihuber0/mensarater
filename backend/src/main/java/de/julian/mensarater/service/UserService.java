package de.julian.mensarater.service;

import de.julian.mensarater.dto.MensaDTO;
import de.julian.mensarater.entity.MensaUser;
import de.julian.mensarater.entity.OpenMensa;
import de.julian.mensarater.repository.OpenMensaRepository;
import de.julian.mensarater.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserRepository userRepository;

    private final OpenMensaRepository openMensaRepository;

    public MensaDTO getUserMensa(String email) {
        logger.info("Fetching mensa for user");
        MensaUser mensaUser = userRepository.findByEmail(email);
        if (mensaUser != null) {
            return new MensaDTO(mensaUser.getOpenMensa().getName(), mensaUser.getOpenMensa().getMensaId());
        }
        return null;
    }

    public MensaDTO saveUserMensa(String email, long openMensaId) {
        logger.info("Saving mensa with OpenMensa-ID {} for user", openMensaId);
        MensaUser mensaUser = userRepository.findByEmail(email);
        OpenMensa openMensa = openMensaRepository.findOpenMensaByMensaId(openMensaId);
        if (mensaUser == null) {
            logger.info("Creating new MensaUser");
            mensaUser = new MensaUser();
            mensaUser.setEmail(email);
        }
        mensaUser.setOpenMensa(openMensa);
        MensaUser updatedUser = userRepository.save(mensaUser);
        return new MensaDTO(updatedUser.getOpenMensa().getName(), updatedUser.getOpenMensa().getMensaId());
    }
}
