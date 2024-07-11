package com.timtrix.repositories;

import com.timtrix.entities.User;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserRepository  {
//    User findByEmail(String email);
//    Optional<User> findByEmail(String email);
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
