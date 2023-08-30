package com.johncla.cards.controller;

import com.johncla.cards.auth.AuthenticationRequest;
import com.johncla.cards.auth.AuthenticationResponse;
import com.johncla.cards.auth.AuthenticationService;
import com.johncla.cards.auth.LoginRequest;
import com.johncla.cards.dto.UserDto;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity responseResponseEntity(@RequestBody UserDto registerRequest){

        log.info("Incoming request");
        return ResponseEntity.ok(authenticationService.register(registerRequest));
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AuthenticationResponse> responseResponseEntity(@RequestBody LoginRequest loginRequestRequest){

        log.info("Incoming request");
        return ResponseEntity.ok(authenticationService.login(loginRequestRequest));
    }

    @PostMapping("/authenticate")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Hidden
    public ResponseEntity<AuthenticationResponse> responseResponseEntity(@RequestBody AuthenticationRequest authenticationRequest){
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }

}
