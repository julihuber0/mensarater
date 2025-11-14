package de.julian.mensarater.controller;

import de.julian.mensarater.dto.MensaDTO;
import de.julian.mensarater.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/mensa")
    @PreAuthorize("hasRole('mensarater-user')")
    public ResponseEntity<MensaDTO> getUserMensa(@AuthenticationPrincipal Jwt jwt) {
        String email = jwt.getClaimAsString("email");
        MensaDTO mensaDTO = userService.getUserMensa(email);
        return ResponseEntity.ok(mensaDTO);
    }

    @PostMapping("/mensa")
    @PreAuthorize("hasRole('mensarater-user')")
    public ResponseEntity<MensaDTO> saveUserMensa(@RequestBody MensaDTO mensaDTO, @AuthenticationPrincipal Jwt jwt) {
        String email = jwt.getClaimAsString("email");
        MensaDTO savedMensaDTO = userService.saveUserMensa(email, mensaDTO.getOpenMensaId());
        return ResponseEntity.ok(savedMensaDTO);
    }
}
