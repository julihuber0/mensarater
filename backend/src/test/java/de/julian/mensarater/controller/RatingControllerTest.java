package de.julian.mensarater.controller;

import de.julian.mensarater.dto.DishDTO;
import de.julian.mensarater.service.RatingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class RatingControllerTest {

    @Mock
    private RatingService ratingService;

    @Mock
    private Jwt jwt;

    @InjectMocks
    private RatingController ratingController;

    private static final String TEST_EMAIL = "user@example.com";

    @Test
    void getAvgRatings_whenOpenMensaIdIsNull_returnsEmptyList() {
        Instant date = Instant.now();

        ResponseEntity<List<DishDTO>> response = ratingController.getAvgRatings(null, date);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty(), "Erwartet: leere Liste wenn openMensaId null ist");
        verifyNoInteractions(ratingService);
    }

    @Test
    void getAvgRatings_withId_returnsDishes() {
        Instant date = Instant.parse("2024-01-01T00:00:00Z");
        DishDTO d1 = new DishDTO();
        d1.setDishName("Dish1");
        DishDTO d2 = new DishDTO();
        d2.setDishName("Dish2");
        List<DishDTO> expected = List.of(d1, d2);

        when(ratingService.getAvgRatingsForDate(1L, date)).thenReturn(expected);

        ResponseEntity<List<DishDTO>> response = ratingController.getAvgRatings(1L, date);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());
        verify(ratingService, times(1)).getAvgRatingsForDate(1L, date);
    }

    @Test
    void getUserRatings_returnsUserDishes() {
        Instant date = Instant.parse("2024-02-01T12:00:00Z");
        DishDTO d = new DishDTO();
        d.setDishName("UserDish");
        List<DishDTO> expected = List.of(d);

        when(jwt.getClaimAsString("email")).thenReturn(TEST_EMAIL);
        when(ratingService.getUserRatingsForDate(TEST_EMAIL, date)).thenReturn(expected);

        ResponseEntity<List<DishDTO>> response = ratingController.getUserRatings(jwt, date);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());
        verify(jwt, times(1)).getClaimAsString("email");
        verify(ratingService, times(1)).getUserRatingsForDate(TEST_EMAIL, date);
    }

    @Test
    void saveRating_savesAndReturnsDish() {
        DishDTO request = new DishDTO();
        request.setDishName("NewDish");
        DishDTO saved = new DishDTO();
        saved.setDishName("NewDish");

        when(jwt.getClaimAsString("email")).thenReturn(TEST_EMAIL);
        when(ratingService.saveRating(request, TEST_EMAIL)).thenReturn(saved);

        ResponseEntity<DishDTO> response = ratingController.saveRating(request, jwt);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(saved, response.getBody());
        verify(jwt, times(1)).getClaimAsString("email");
        verify(ratingService, times(1)).saveRating(request, TEST_EMAIL);
    }
}
