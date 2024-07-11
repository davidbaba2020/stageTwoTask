package com.timtrix.repositories;

import com.timtrix.entities.User;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;


@Service
public class UserRepository  {
private Map<String, User> users = new ConcurrentHashMap<>();

    public User save(User user) {
        users.put(user.getEmail(), user);
        return user;
    }

    public User findByEmail(String email) {
        return users.get(email);
    }

    public boolean existsByEmail(String email) {
        return users.containsKey(email);
    }

    public void deleteByEmail(String email) {
        users.remove(email);
    }

    public void deleteAll() {
        users.clear();
    }
}
