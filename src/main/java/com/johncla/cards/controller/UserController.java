package com.johncla.cards.controller;

import com.johncla.cards.dto.UserDto;
import com.johncla.cards.exceptions.UserExceptions;
import com.johncla.cards.model.Card;
import com.johncla.cards.model.Role;
import com.johncla.cards.model.User;
import com.johncla.cards.serviceimpl.CardServiceImpl;
import com.johncla.cards.serviceimpl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "UserManagement")
public class UserController {
    private final UserServiceImpl userService;
    private Role role;
    private final CardServiceImpl cardService;

    @Operation(
            description = "Get endpoint: get-user by email",
            summary = "GET",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized/ Invalid Token",
                            responseCode = "401"
                    )
            }
    )
    @GetMapping("/getUser-byEmail/{email}")
    public ResponseEntity<Optional<User>> getUserByEmail(@PathVariable String email) {
        Optional<User> user = userService.getUserByEmail(email);

        if(user.isEmpty() || user == null){
           throw new UserExceptions.UserNotFoundException("User not Found!") ;
        }else {
            log.info("User Obtained : " + user.get().getId());
        }

        return ResponseEntity.ok(user);
    }

    @PostMapping("/create-user")
    public ResponseEntity<User> createUser(@RequestBody UserDto userDto) {
        User createdUser = userService.createUser(userDto);
        return ResponseEntity.ok(createdUser);
    }

    @PutMapping("/update-user/{email}")
    public ResponseEntity<User> updateUser(@PathVariable String email, @RequestBody UserDto userDto) {
        User updatedUser = userService.updateUser(email, userDto); // Implement user update logic
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/delete-user/{email}")
    public ResponseEntity<Void> deleteUser(@PathVariable String email) {
            userService.deleteUser(email); // Delegate to UserService
            return ResponseEntity.noContent().build();
    }

    // Add more endpoints for user-related operations
}