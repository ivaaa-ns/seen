package com.ivaaan.seen.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import com.ivaaan.seen.auth.dto.LoginDto;
import com.ivaaan.seen.auth.dto.TokenResponseDto;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public TokenResponseDto login(@RequestBody LoginDto dto) {
        log.info("POST /auth/login");
        return authService.login(dto);
    }
}
