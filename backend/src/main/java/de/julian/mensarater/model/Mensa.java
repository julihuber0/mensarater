package de.julian.mensarater.model;

import lombok.Getter;

@Getter
public enum Mensa {
    UNI_P("UNI-P"),
    HS_DEG("HS-DEG"),
    WB_HUBLAND_S("WB-HUBLAND-S"),
    WB_HUBLAND_N("WB-HUBLAND-N");

    final String locationString;

    Mensa(String locationString) {
        this.locationString = locationString;
    }
}
