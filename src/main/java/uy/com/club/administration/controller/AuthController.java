package uy.com.club.administration.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uy.com.club.administration.domain.user.UserApp;
import uy.com.club.administration.dto.request.LoginRequest;
import uy.com.club.administration.dto.request.SignupRequest;
import uy.com.club.administration.dto.responses.JwtResponse;
import uy.com.club.administration.services.UserService;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@Slf4j
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(JwtResponse.builder()
                .token(userService.login(loginRequest))
                .build());
    }

    @PostMapping("/register")
    public ResponseEntity<UserApp> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.registerUser(signUpRequest));
    }
}