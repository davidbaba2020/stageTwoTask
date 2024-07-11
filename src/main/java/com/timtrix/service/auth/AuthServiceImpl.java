package com.timtrix.service.auth;

import com.timtrix.config.security.jwt.JwtUtil;
import com.timtrix.dtos.UserDTO;
import com.timtrix.entities.User;
import com.timtrix.exceptions.EmailAlreadyExistsException;
import com.timtrix.exceptions.ResourceNotFoundException;
import com.timtrix.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public Map<String, Object> createUser(UserDTO userDTO) {
        User userFound = userRepository.findByEmail(userDTO.getEmail());
        log.info("User exist,  {}", userFound);
        if(userFound!=null){
            throw new EmailAlreadyExistsException("User with email already exist");
        }
        User newUser = User.builder()
                        .id(UUID.randomUUID().toString())
                        .firstName(userDTO.getFirstName())
                        .lastName(userDTO.getLastName())
                        .email(userDTO.getEmail())
                        .password(passwordEncoder.encode(userDTO.getPassword()))
                        .phone(userDTO.getPhone())
                        .build();
        userRepository.save(newUser);

        log.info("New user,  {}", newUser);
        String accessToken = jwtUtil.generateToken(newUser.getEmail());

        Map<String, Object> userData = Map.of(
                "userId", newUser.getId(),
                "firstName", newUser.getFirstName(),
                "lastName", newUser.getLastName(),
                "email", newUser.getEmail(),
                "phone", newUser.getPhone()
        );

        return Map.of(
                "accessToken", accessToken,
                "user", userData
        );
    }
}
