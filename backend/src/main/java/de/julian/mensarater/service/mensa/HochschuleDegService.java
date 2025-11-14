package de.julian.mensarater.service.mensa;

import de.julian.mensarater.model.Mensa;
import org.springframework.stereotype.Service;

@Service("HS-DEG")
public class HochschuleDegService extends StwnoService {
    protected HochschuleDegService(OpenMensaApiService openMensaApiService) {
        super(Mensa.HS_DEG, openMensaApiService);
    }
}
