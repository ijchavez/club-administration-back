package uy.com.club.administration.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uy.com.club.administration.domain.Partner;
import uy.com.club.administration.dto.request.UserDTO;
import uy.com.club.administration.services.UserService;

import java.math.BigDecimal;
import java.math.RoundingMode;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/account")
    public ResponseEntity<UserDTO> getAccount() {
        return ResponseEntity.ok(userService.getAccount());
    }

    @GetMapping("/account/{email}")
    public ResponseEntity<UserDTO> getAccountByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.getAccountByEmail(email));
    }
}