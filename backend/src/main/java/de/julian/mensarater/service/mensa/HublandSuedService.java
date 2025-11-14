package de.julian.mensarater.service.mensa;

import de.julian.mensarater.model.Mensa;
import org.springframework.stereotype.Service;

@Service("WB-HUBLAND-S")
public class HublandSuedService extends HochschuleWbService {

    protected HublandSuedService() {
        super(Mensa.WB_HUBLAND_S);
    }
}
