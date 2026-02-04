package de.julian.mensarater.service.mensa;

import de.julian.mensarater.enums.OtherMensa;
import de.julian.mensarater.model.LengauerDishResponse;
import de.julian.mensarater.model.OpenMensaDishModel;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class OtherMensaService {

    private final WebClient lengauerClient;

    public OtherMensaService(WebClient.Builder webClientBuilder) {
        this.lengauerClient = webClientBuilder.baseUrl("https://lengauer-api.jules-labs.de/api").build();
    }

    public List<OpenMensaDishModel> fetchMensaDishesForDay(long mensaId, Date date) {
        OtherMensa otherMensa = OtherMensa.fromId(mensaId);
        if (otherMensa == null) {
            return List.of();
        }
        switch (otherMensa) {
            case LENGAUERS_BISTRO -> {
                return this.fetchLengauerDishesForDay(date);
            }
        }
        return null;
    }

    private List<OpenMensaDishModel> fetchLengauerDishesForDay(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = sdf.format(date);
        return lengauerClient.get()
                .uri("/menu?date={date}", formattedDate)
                .retrieve()
                .bodyToFlux(LengauerDishResponse.class)
                .map(LengauerDishResponse::mapToOpenMensaDish)
                .onErrorResume(WebClientResponseException.NotFound.class, e -> Flux.empty())
                .collectList()
                .block();
    }
}
