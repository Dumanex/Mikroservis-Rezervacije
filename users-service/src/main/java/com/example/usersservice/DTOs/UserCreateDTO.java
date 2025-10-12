package com.example.usersservice.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserCreateDTO {

    @NotBlank(message = "Ime ne sme biti prazno")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Ime mora sadrzati samo slova i prazna polja")
    private String name;

    @NotBlank(message = "Email ne sme biti prazan")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "Email mora biti u formatu pera@peric.com")
    private String email;
}
