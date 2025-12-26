package com.ivaaan.seen.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterDto {
    // TODO Maybe in the future register and login with google

    @Email(message = "auth.email.invalid")
    @NotBlank(message = "auth.email.required")
    private String email;

    @NotBlank(message = "auth.password.required")
    @Size(min = 8, message = "auth.password.min_length")
    private String password;

    public RegisterDto() {
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}
