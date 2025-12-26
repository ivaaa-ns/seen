package com.ivaaan.seen.user;

import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ivaaan.seen.uploads.FileStorageService;
import com.ivaaan.seen.user.dto.UserMeDto;
import com.ivaaan.seen.user.dto.UserNewDto;
import com.ivaaan.seen.user.dto.UserOtherDto;
import com.ivaaan.seen.user.dto.UserPatchDto;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public UserMeDto getMe(Authentication authentication) {

        Long userId = (Long) authentication.getPrincipal();
        log.info("GET /users/me", userId);
        return userService.getMe(userId);
    }

    @GetMapping("/id/{id}")
    public UserOtherDto getOtherUserById(@PathVariable String id) {
        log.info("GET /users/id/{}", id);
        return userService.getOtherUserById(Long.parseLong(id));
    }

    @GetMapping("/name/{name}")
    public List<UserOtherDto> getOtherUserByName(@PathVariable String name) {
        log.info("GET /users/name/{}", name);
        return userService.getOtherUserByName(name);
    }

    @PostMapping(value = "/me/photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public UserMeDto uploadPhoto(
            Authentication authentication,
            @RequestPart("file") MultipartFile file) {

        Long userId = (Long) authentication.getPrincipal();
        log.info("POST /users/me/photo", userId);
        FileStorageService.validatePhotoProfile(file);

        return userService.uploadPhoto(userId, file);
    }

    @PatchMapping("/me")
    public UserMeDto patchMe(Authentication authentication, @RequestBody UserPatchDto dto) {

        Long userId = (Long) authentication.getPrincipal();
        log.info("PATCH /users/me", userId);
        return userService.patchMe(userId, dto);
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMe(Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        log.info("DELETE /users/me", userId);
        userService.deleteMe(userId);
    }

}