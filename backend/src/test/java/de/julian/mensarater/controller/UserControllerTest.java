package de.julian.mensarater.controller;

import de.julian.mensarater.dto.MensaDTO;
import de.julian.mensarater.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private Jwt jwt;

    @InjectMocks
    private UserController userController;

    private MensaDTO mensaDTO;
    private static final String TEST_EMAIL = "test@example.com";
    private static final String TEST_NAME = "Test";
    private static final Integer TEST_MENSA_ID = 123;

    @BeforeEach
    void setUp() {
        mensaDTO = new MensaDTO(TEST_NAME, TEST_MENSA_ID);
    }

    @Test
    void getUserMensa_shouldReturnMensaDTO() {
        when(jwt.getClaimAsString("email")).thenReturn(TEST_EMAIL);
        when(userService.getUserMensa(TEST_EMAIL)).thenReturn(mensaDTO);

        ResponseEntity<MensaDTO> response = userController.getUserMensa(jwt);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mensaDTO, response.getBody());
        verify(jwt).getClaimAsString("email");
        verify(userService).getUserMensa(TEST_EMAIL);
    }

    @Test
    void saveUserMensa_shouldReturnSavedMensaDTO() {
        MensaDTO savedMensaDTO = new MensaDTO(TEST_NAME, TEST_MENSA_ID);

        when(jwt.getClaimAsString("email")).thenReturn(TEST_EMAIL);
        when(userService.saveUserMensa(TEST_EMAIL, TEST_MENSA_ID)).thenReturn(savedMensaDTO);

        ResponseEntity<MensaDTO> response = userController.saveUserMensa(mensaDTO, jwt);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(savedMensaDTO, response.getBody());
        verify(jwt).getClaimAsString("email");
        verify(userService).saveUserMensa(TEST_EMAIL, TEST_MENSA_ID);
    }
}
