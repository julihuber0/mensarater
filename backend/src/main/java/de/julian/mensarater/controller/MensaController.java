package de.julian.mensarater.controller;

import de.julian.mensarater.dto.MensaDTO;
import de.julian.mensarater.service.OpenMensaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mensa")
@RequiredArgsConstructor
public class MensaController {

    private final OpenMensaService openMensaService;

    @GetMapping()
    public List<MensaDTO> getAllMensas() {
        return openMensaService.getAllMensas();
    }

    @GetMapping("/{mensaId}")
    public MensaDTO getMensaById(@PathVariable("mensaId") long mensaId) {
        return openMensaService.getMensaById(mensaId);
    }
}
