package com.project.shop.controller;

import com.project.shop.config.security.payload.AuthenticationRequest;
import com.project.shop.config.security.payload.AuthenticationResponse;
import com.project.shop.config.security.AuthenticationService;
import com.project.shop.config.security.payload.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/account")
    public ResponseEntity<AuthenticationResponse> createAccount(@RequestBody RegisterRequest registerRequest) throws IOException {
        AuthenticationResponse authenticationResponse = authenticationService.registerAccount(registerRequest);
        return ResponseEntity.ok().body(authenticationResponse);
    }

    @PostMapping("/token")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest) throws IOException {
        AuthenticationResponse authenticationResponse = authenticationService.authenticate(authenticationRequest);
        return ResponseEntity.ok().body(authenticationResponse);
    }

}
