package de.julian.mensarater.service.mensa;

import de.julian.mensarater.dto.DishDTO;
import de.julian.mensarater.model.OpenMensaDishModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OpenMensaApiService implements MensaService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final WebClient webClient;

    public OpenMensaApiService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://openmensa.org/api/v2").build();
    }

    @Override
    public List<DishDTO> getMensaDishesForToday(long openMensaId) {
        logger.info("Fetching today's dishes for OpenMensa ID: {}", openMensaId);
        Date today = new Date();
        List<OpenMensaDishModel> dishes = fetchMensaDishesForDay(openMensaId, today);
        return new ArrayList<>(dishes.stream().map(OpenMensaDishModel::toDishDTO).toList());
    }

    @Override
    public List<DishDTO> getMensaDishesForDate(long mensaId, Instant date) {
        logger.info("Fetching dishes for OpenMensa ID: {} on date: {}", mensaId, date);
        Date parsedDate = Date.from(date);
        List<OpenMensaDishModel> dishes = fetchMensaDishesForDay(mensaId, parsedDate);
        return new ArrayList<>(dishes.stream().map(OpenMensaDishModel::toDishDTO).toList());
    }

    private List<OpenMensaDishModel> fetchMensaDishesForDay(long mensaId, Date date) {
        String dateString = String.format("%tY-%<tm-%<td", date);
        return webClient.get()
                .uri("/canteens/{mensaId}/days/{date}/meals", mensaId, dateString)
                .retrieve()
                .bodyToFlux(OpenMensaDishModel.class)
                .collectList()
                .onErrorResume(_ -> Mono.just(new ArrayList<>()))
                .block();
    }
}
