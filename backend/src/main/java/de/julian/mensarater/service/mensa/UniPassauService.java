package de.julian.mensarater.service.mensa;

import de.julian.mensarater.model.Mensa;
import org.springframework.stereotype.Service;

@Service("UNI-P")
public class UniPassauService extends StwnoService {
    protected UniPassauService(OpenMensaApiService openMensaApiService) {
        super(Mensa.UNI_P, openMensaApiService);
    }
}
