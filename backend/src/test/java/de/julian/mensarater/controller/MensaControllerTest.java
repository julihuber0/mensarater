package de.julian.mensarater.controller;

import de.julian.mensarater.dto.MensaDTO;
import de.julian.mensarater.service.OpenMensaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MensaControllerTest {

    @Mock
    private OpenMensaService openMensaService;

    @InjectMocks
    private MensaController mensaController;

    private MensaDTO mensa1;
    private MensaDTO mensa2;

    @BeforeEach
    void setUp() {
        mensa1 = new MensaDTO("Mensa1", 1L);
        mensa2 = new MensaDTO("Mensa2", 2L);
    }

    @Test
    void getAllMensas_shouldReturnListOfMensas() {
        when(openMensaService.getAllMensas()).thenReturn(Arrays.asList(mensa1, mensa2));
        List<MensaDTO> result = mensaController.getAllMensas();

        assertNotNull(result, "Erwartet: Ergebnis ist nicht null");
        assertEquals(2, result.size(), "Erwartet: zwei Einträge");
        assertSame(mensa1, result.getFirst(), "Erwartet: erstes Element ist mensa1");
        verify(openMensaService, times(1)).getAllMensas();
    }

    @Test
    void getMensaById_shouldReturnMensa() {
        when(openMensaService.getMensaById(1L)).thenReturn(mensa1);
        MensaDTO result = mensaController.getMensaById(1L);

        assertNotNull(result, "Erwartet: Ergebnis ist nicht null");
        assertSame(mensa1, result, "Erwartet: Rückgabe ist genau das gemockte Objekt mensa1");
        verify(openMensaService, times(1)).getMensaById(1L);
    }
}
