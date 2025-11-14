package de.julian.mensarater.controller;

import de.julian.mensarater.dto.DishDTO;
import de.julian.mensarater.service.RatingService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping()
    public ResponseEntity<List<DishDTO>> getAvgRatings(
            @RequestParam(value = "openMensaId", required = false) Long openMensaId,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant date) {
        if (openMensaId == null) {
            return ResponseEntity.ok(List.of());
        }
        List<DishDTO> dishes = ratingService.getAvgRatingsForDate(openMensaId, date);
        return ResponseEntity.ok(dishes);
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('mensarater-user')")
    public ResponseEntity<List<DishDTO>> getUserRatings(
            @AuthenticationPrincipal Jwt jwt,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant date) {
        String email = jwt.getClaimAsString("email");
        List<DishDTO> dishes = ratingService.getUserRatingsForDate(email, date);
        return ResponseEntity.ok(dishes);
    }

    @PostMapping("/user")
    @PreAuthorize("hasRole('mensarater-user')")
    public ResponseEntity<DishDTO> saveRating(@RequestBody DishDTO dishDTO, @AuthenticationPrincipal Jwt jwt) {
        String email = jwt.getClaimAsString("email");
        DishDTO dish = ratingService.saveRating(dishDTO, email);
        return ResponseEntity.ok(dish);
    }
}
