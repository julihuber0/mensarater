package de.julian.mensarater.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class HealthControllerTest {
    @InjectMocks
    private HealthController healthController;

    @Test
    void getHealth_shouldReturnHealthyStatus() {
        ResponseEntity<String> response = healthController.getHealth();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("healthy", response.getBody());
    }
}
