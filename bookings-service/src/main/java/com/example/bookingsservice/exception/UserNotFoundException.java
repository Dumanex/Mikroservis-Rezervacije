package com.example.bookingsservice.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(Long userId){
        super("Korisnik sa ID " + userId + " ne postoji ili servis nije dostupan.");
    }
}
