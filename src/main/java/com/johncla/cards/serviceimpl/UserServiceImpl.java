package com.johncla.cards.serviceimpl;

import com.johncla.cards.dto.UserDto;
import com.johncla.cards.exceptions.UserExceptions;
import com.johncla.cards.model.User;
import com.johncla.cards.repository.UserRepository;
import com.johncla.cards.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> getUsersByRole(String role) {
        return userRepository.findByRole(role);
    }

    @Override
    public User authenticateUser(String email, String password) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user;
            }
        }
        return null;
    }

    @Override
    public User createUser(UserDto userDto) {
            validateUserDto(userDto); // Perform validation
            String hashedPassword = passwordEncoder.encode(userDto.getPassword()); // Hash the password

            User newUser = new User();
            newUser.setEmail(userDto.getEmail());
            newUser.setPassword(hashedPassword);
            newUser.setRole(userDto.getRole());

            return userRepository.save(newUser);
        }

        private void validateUserDto(UserDto userDto) {
            // check if email is valid, password meets complexity criteria, etc.
            if (!isValidEmail(userDto.getEmail())) {
                throw new UserExceptions.UserCreationException("Invalid email address");
            }
            if (!isValidPassword(userDto.getPassword())) {
                throw new UserExceptions.UserCreationException("Invalid password");
            }
            // Add more validation rules if you want
        }

        private boolean isValidEmail(String email) {
            String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
            return email.matches(emailRegex);
        }

        private boolean isValidPassword(String password) {
            // Password length should be at least 8 characters
            if (password.length() < 8) {
                return false;
            }

            // Password should contain at least one uppercase letter, one lowercase letter, and one digit
            boolean hasUppercase = false;
            boolean hasLowercase = false;
            boolean hasDigit = false;

            for (char ch : password.toCharArray()) {
                if (Character.isUpperCase(ch)) {
                    hasUppercase = true;
                } else if (Character.isLowerCase(ch)) {
                    hasLowercase = true;
                } else if (Character.isDigit(ch)) {
                    hasDigit = true;
                }

                // Break loop if all conditions are satisfied
                if (hasUppercase && hasLowercase && hasDigit) {
                    break;
                }
            }

            return hasUppercase && hasLowercase && hasDigit;

        }

    @Override
    public User updateUser(String email, UserDto userDto) {
        User existingUser = userRepository.findByEmail(email)

                .orElseThrow(() -> new UserExceptions.UserNotFoundException("User not found with email: " + email));

        validateUserDto(userDto); // Perform validation
        updateUserFields(existingUser, userDto); // Update user fields

        return userRepository.save(existingUser);
    }

    private void updateUserFields(User user, UserDto userDto) {
        user.setRole(userDto.getRole()); // Update role

        if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
            String hashedPassword = passwordEncoder.encode(userDto.getPassword()); // Hash the password
            user.setPassword(hashedPassword); // Update password if provided
        }
    }

    @Override
    public void deleteUser(String email) {
        User existingUser = userRepository.findByEmail(email)

                .orElseThrow(() -> new UserExceptions.UserNotFoundException("User not found with email: " + email));

        userRepository.delete(existingUser);
    }
}
