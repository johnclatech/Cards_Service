package com.johncla.cards.auth;

import com.johncla.cards.config.JwtTokenProvider;
import com.johncla.cards.dto.UserDto;
import com.johncla.cards.exceptions.UserExceptions;
import com.johncla.cards.model.Role;
import com.johncla.cards.model.User;
import com.johncla.cards.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private UserDetails userDetails;

    public ResponseEntity register(UserDto registerRequest){
        var user = userRepository.findByEmail(registerRequest.getEmail());

        if(user.isPresent()){
            throw new UserExceptions.UserAlreadyExistException("User Already Exist");
        }else {
            var localuser = User.builder()
                    .email(registerRequest.getEmail())
                    .password(passwordEncoder.encode(registerRequest.getPassword()))
                    .role(registerRequest.getRole())
                    .build();

            userRepository.save(localuser);
        }
        return ResponseEntity.ok().body("User created Successfully");
    }

    public AuthenticationResponse login(LoginRequest registerRequest){
        var user = userRepository.findByEmail(registerRequest.getEmail());
        if(user.isPresent()){
            var jwtToken = jwtTokenProvider.createToken(registerRequest.getEmail(),user.get().getRole());
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        }else {
            throw new UserExceptions.UserNotFoundException("User not found");
        }

    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()
                )
        );

        var user = userRepository.findByEmail(authenticationRequest.getEmail()).orElseThrow(() -> new UserExceptions.UserNotFoundException("User not Found"));

        var jwtToken = jwtTokenProvider.createToken(user.getEmail(),user.getRole());
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
