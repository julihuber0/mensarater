package de.julian.mensarater.service.mensa;

import de.julian.mensarater.dto.DishDTO;

import java.time.Instant;
import java.util.List;

public interface MensaService {

    List<DishDTO> getMensaDishesForToday(long openMensaId);

    List<DishDTO> getMensaDishesForDate(long openMensaId, Instant date);
}
