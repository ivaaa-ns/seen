package com.ivaaan.seen.auth;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ivaaan.seen.auth.dto.LoginDto;
import com.ivaaan.seen.auth.dto.RegisterDto;
import com.ivaaan.seen.auth.dto.TokenResponseDto;
import com.ivaaan.seen.user.User;
import com.ivaaan.seen.user.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public TokenResponseDto login(LoginDto dto) {

        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,
                        "auth.invalid_credentials"
                ));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "auth.invalid_credentials"
            );
        }

        String token = jwtService.generateToken(user.getId());
        return new TokenResponseDto(token);
    }

    public TokenResponseDto register(RegisterDto dto) {

        String email = dto.getEmail().toLowerCase().trim();

        if (userRepository.existsByEmail(email)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "auth.email.already_used"
            );
        }

        User user = new User(
                null,
                email,
                passwordEncoder.encode(dto.getPassword()),
                null
        );

        User saved = userRepository.save(user);

        String token = jwtService.generateToken(saved.getId());
        return new TokenResponseDto(token);
    }
}