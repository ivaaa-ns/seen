package com.ivaaan.seen.user;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ivaaan.seen.user.dto.UserMeDto;
import com.ivaaan.seen.user.dto.UserNewDto;
import com.ivaaan.seen.user.dto.UserOtherDto;
import com.ivaaan.seen.user.dto.UserPatchDto;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserMeDto getMe(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new UserMeDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhoto());
    }

    public UserOtherDto getOtherUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new UserOtherDto(
                user.getId(),
                user.getName(),
                user.getPhoto());
    }

    public List<UserOtherDto> getOtherUserByName(String name) {

        return userRepository
                .findByNameStartingWithIgnoreCase(name)
                .stream()
                .map(user -> new UserOtherDto(
                        user.getId(),
                        user.getName(),
                        user.getPhoto()))
                .toList();
    }

    public UserMeDto newMe(UserNewDto dto) {

        User user = new User(
                dto.getName(),
                dto.getEmail(),
                passwordEncoder.encode(dto.getPassword()),
                dto.getPhoto());

        User saved = userRepository.save(user);

        return new UserMeDto(
                saved.getId(),
                saved.getName(),
                saved.getEmail(),
                saved.getPhoto());
    }

    public UserMeDto patchMe(UserPatchDto dto) {

        User user = userRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (dto.getName() != null) {
            user.setName(dto.getName());
        }

        if (dto.getPhoto() != null) {
            user.setPhoto(dto.getPhoto());
        }

        User saved = userRepository.save(user);

        return new UserMeDto(
                saved.getId(),
                saved.getName(),
                saved.getEmail(),
                saved.getPhoto());
    }

}
