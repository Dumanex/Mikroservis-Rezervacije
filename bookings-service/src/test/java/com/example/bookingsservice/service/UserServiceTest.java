package com.example.bookingsservice.service;

import com.example.bookingsservice.DTOs.UserDTO;
import com.example.bookingsservice.clients.UserClient;
import com.example.bookingsservice.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {
    @Test
    void verifyUserExists_WhenUserFound_ShouldReturnUser() {
        UserClient mockClient = Mockito.mock(UserClient.class);
        UserDTO mockUser = new UserDTO();
        mockUser.setId(1L);
        mockUser.setName("Vladimir");

        Mockito.when(mockClient.getUserById(1L)).thenReturn(mockUser);

        UserService userService = new UserService(mockClient);
        UserDTO result = userService.verifyUserExists(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    void verifyUserExists_WhenUserNotFound_ShouldThrowException () {
        UserClient mockClient = Mockito.mock(UserClient.class);
        Mockito.when(mockClient.getUserById(2L)).thenThrow(new RuntimeException("Korisnik nije pronadjen"));

        UserService userService = new UserService(mockClient);

        RuntimeException ex = assertThrows(UserNotFoundException.class, () -> userService.verifyUserExists(2L));
        assertTrue(ex.getMessage().contains("2"));
    }
}
