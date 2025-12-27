package com.ivaaan.seen.user;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.ivaaan.seen.uploads.FileStorageService;
import com.ivaaan.seen.user.dto.UserMeDto;
import com.ivaaan.seen.user.dto.UserOtherDto;
import com.ivaaan.seen.user.dto.UserPatchDto;

import static org.springframework.http.HttpStatus.NOT_FOUND;

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
                                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "user.not_found"));

                return new UserMeDto(
                                user.getId(),
                                user.getName(),
                                user.getEmail(),
                                user.getPhoto());
        }

        public UserOtherDto getOtherUserById(Long id) {

                User user = userRepository.findById(id)
                                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "user.not_found"));

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

        // ! CARE WITH STRINGS LIKE " "
        public UserMeDto patchMe(Long id, UserPatchDto dto) {

                User user = userRepository.findById(id)
                                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "user.not_found"));

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

        public UserMeDto uploadPhoto(Long id, MultipartFile file) {

                User user = userRepository.findById(id)
                                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "user.not_found"));

                String urlPhoto = FileStorageService.uploadUserPhotoProfile(file, id);

                user.setPhoto(urlPhoto);
                User saved = userRepository.save(user);

                return new UserMeDto(
                                saved.getId(),
                                saved.getName(),
                                saved.getEmail(),
                                saved.getPhoto());

        }

        // ! NEW ENTITY -> UPDATE USER.JAVA TO DELETE IN CASCADE
        @Transactional
        public void deleteMe(Long userId) {

                User user = userRepository.findById(userId)
                                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "user.not_found"));

                userRepository.delete(user);

        }

}
