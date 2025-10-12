package com.example.bookingsservice.service;

import com.example.bookingsservice.DTOs.UserDTO;
import com.example.bookingsservice.clients.UserClient;
import com.example.bookingsservice.exception.UserNotFoundException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserClient userClient;

    public UserDTO verifyUserExists(Long userId) {
        try {
            return userClient.getUserById(userId);
        } catch (Exception e) {
            throw new UserNotFoundException(userId);
        }
    }

    @CircuitBreaker(name = "usersService", fallbackMethod = "fallbackGetUser")
    @Retry(name = "usersService")
    public UserDTO getUserWithResilience(Long userId) {
        return userClient.getUserById(userId);
    }

    public UserDTO fallbackGetUser(Long userId, Throwable throwable) {
        UserDTO fallback = new UserDTO();
        fallback.setId(userId);
        fallback.setName("unknown");
        fallback.setEmail("unknown@unknown.com");

        return fallback;
    }
}
