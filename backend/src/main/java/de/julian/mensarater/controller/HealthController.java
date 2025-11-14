package de.julian.mensarater.controller;

import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
@NoArgsConstructor
public class HealthController {

    @GetMapping()
    public ResponseEntity<String> getHealth() {
        return ResponseEntity.ok("healthy");
    }
}
