package de.julian.mensarater.service.mensa;

import de.julian.mensarater.model.Mensa;
import org.springframework.stereotype.Service;

@Service("WB-HUBLAND-N")
public class HublandNordService extends HochschuleWbService {

    protected HublandNordService() {
        super(Mensa.WB_HUBLAND_N);
    }
}
