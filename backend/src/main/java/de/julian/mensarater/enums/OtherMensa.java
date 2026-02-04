package de.julian.mensarater.enums;

import java.util.Arrays;

public enum OtherMensa {
    LENGAUERS_BISTRO(-1L);

    final long id;

    OtherMensa(long id) {
        this.id = id;
    }

    public static OtherMensa fromId(long id) {
        return Arrays.stream(values())
                .filter(mensa -> mensa.id == id)
                .findFirst()
                .orElse(null);
    }
}
