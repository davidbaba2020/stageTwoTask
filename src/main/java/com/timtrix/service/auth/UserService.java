package com.timtrix.service.auth;

import com.timtrix.entities.User;
import com.timtrix.exceptions.AccessDeniedException;
import com.timtrix.exceptions.UserNotFoundException;
import com.timtrix.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public Map<String, Object> getUserData(Long id, String currentUserEmail) {
        User currentUser = findByEmail(currentUserEmail);

        if (currentUser == null) {
            throw new UserNotFoundException("Current user not found");
        }

        User user = findById(id);

        if (user == null) {
            throw new UserNotFoundException("User not found");
        }

//        boolean isAuthorized = currentUser.getId().equals(id) || currentUser.getOrganisations().stream()
//                .anyMatch(org -> org.getUsers().contains(user));


        if (!isAuthorized()) {
            throw new AccessDeniedException("Access denied");
        }

        return Map.of(
                "userId", user.getId(),
                "firstName", user.getFirstName(),
                "lastName", user.getLastName(),
                "email", user.getEmail(),
                "phone", user.getPhone()
        );
    }


    private boolean isAuthorized() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User customUserDetails = (User) authentication.getPrincipal();
        return authentication.isAuthenticated();
    }
}
