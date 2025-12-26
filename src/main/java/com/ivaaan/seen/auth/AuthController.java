package com.ivaaan.seen.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import com.ivaaan.seen.auth.dto.LoginDto;
import com.ivaaan.seen.auth.dto.RegisterDto;
import com.ivaaan.seen.auth.dto.TokenResponseDto;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // ! Maybe in the future register and login with google
    @PostMapping("/login")
    public TokenResponseDto login(@RequestBody LoginDto dto) {
        log.info("POST /auth/login");
        return authService.login(dto);
    }
    // ! Maybe in the future register and login with google
    @PostMapping("/register")
    public TokenResponseDto register(@Valid @RequestBody RegisterDto dto) {
        log.info("POST /auth/register");
        return authService.register(dto);
    }
}
