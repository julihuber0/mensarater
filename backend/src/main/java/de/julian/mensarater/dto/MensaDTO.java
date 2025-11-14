package de.julian.mensarater.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class MensaDTO {

    private final String name;

    private final long openMensaId;
}
