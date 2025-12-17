package com.ivaaan.seen.user;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

        // TODO We need to upload the photo too
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

                User saved = userRepository.save(user);

                return new UserMeDto(
                                saved.getId(),
                                saved.getName(),
                                saved.getEmail(),
                                saved.getPhoto());
        }

        // TODO This creates multiple files per user; only one active file should exist

        public UserMeDto uploadPhoto(Long id, MultipartFile file) {

                if (file.isEmpty()) {
                        throw new IllegalArgumentException("File is empty");
                }

                String contentType = file.getContentType();
                if (contentType == null || !contentType.startsWith("image/")) {
                        throw new IllegalArgumentException("Invalid file type");
                }

                User user = userRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("User not found"));

                try {
                        Path uploadDir = Paths.get("uploads/users");
                        Files.createDirectories(uploadDir);

                        String filename = UUID.randomUUID() + "-" + file.getOriginalFilename();
                        Path path = uploadDir.resolve(filename);

                        Files.copy(file.getInputStream(), path);

                        user.setPhoto(path.toString());
                        User saved = userRepository.save(user);

                        return new UserMeDto(
                                        saved.getId(),
                                        saved.getName(),
                                        saved.getEmail(),
                                        saved.getPhoto());

                } catch (IOException e) {
                        throw new RuntimeException("Error saving file", e);
                }
        }

}
