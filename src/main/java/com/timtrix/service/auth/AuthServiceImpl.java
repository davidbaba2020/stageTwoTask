package com.timtrix.service.auth;

import com.timtrix.config.security.jwt.JwtUtil;
import com.timtrix.dtos.UserDTO;
import com.timtrix.entities.Organisation;
import com.timtrix.entities.User;
import com.timtrix.exceptions.EmailAlreadyExistsException;
import com.timtrix.exceptions.ResourceNotFoundException;
import com.timtrix.repositories.OrganisationRepository;
import com.timtrix.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final OrganisationRepository organisationRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public Map<String, Object> createUser(UserDTO userDTO) {
        Optional<User> userFound = userRepository.findByEmail(userDTO.getEmail());
        if(userFound.isPresent()) {
            throw new ResourceNotFoundException("User with email already exist");
        }
//        log.info("User exist,  {}", userFound);
        User newUser = User.builder()
                        .firstName(userDTO.getFirstName())
                        .lastName(userDTO.getLastName())
                        .email(userDTO.getEmail())
                        .password(passwordEncoder.encode(userDTO.getPassword()))
                        .phone(userDTO.getPhone())
                        .build();
        userRepository.save(newUser);
        createDefaultOrg(newUser);
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



    private void createDefaultOrg(User user) {
        // Create organisation
        String orgName = user.getFirstName() + "'s Organisation";
        Organisation organisation = new Organisation(
                orgName, user.getFirstName()
                +" "+user.getLastName()+" "
                +"Default organization"
        );

        // Add user to organisation
        organisation.getUsers().add(user);
//        organisationRepository.save(organisation);

        // Add organisation to user
        Set<Organisation> userOrganizations = new HashSet<>();
        userOrganizations.add(organisation);
        organisationRepository.save(organisation);


    }
}
