package de.julian.mensarater.service.mensa;

import de.julian.mensarater.model.Mensa;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MensaServiceFactory {

    private final Map<String, MensaService> services;

    public MensaServiceFactory(Map<String, MensaService> services) {
        this.services = services;
    }

    public MensaService getMensaService(Mensa mensa) {
        return services.get(mensa.getLocationString());
    }
}
