package com.ivaaan.seen.user;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
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
        log.info("GET /users/me");
        Long userId = (Long) authentication.getPrincipal();
        return userService.getMe(userId);
    }

    // TODO Care with my own profile
    @GetMapping("/id/{id}")
    public UserOtherDto getOtherUserById(@PathVariable String id) {
        log.info("GET /users/id/{}", id);
        return userService.getOtherUserById(Long.parseLong(id));
    }

    // TODO Care with my own profile
    @GetMapping("/name/{name}")
    public List<UserOtherDto> getOtherUserByName(@PathVariable String name) {
        log.info("GET /users/name/{}", name);
        return userService.getOtherUserByName(name);
    }

    // TODO Care with /auth
    @PostMapping
    public UserMeDto newUser(@RequestBody UserNewDto dto) {
        log.info("POST /users");
        return userService.newMe(dto);
    }

    @PostMapping(value = "/me/photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public UserMeDto uploadPhoto(
            Authentication authentication,
            @RequestPart("file") MultipartFile file) {

        log.info("POST /users/me/photo");

        Long userId = (Long) authentication.getPrincipal();

        FileStorageService.validatePhotoProfile(file);

        return userService.uploadPhoto(userId, file);
    }

    @PatchMapping("/me")
    public UserMeDto patchMe(Authentication authentication, @RequestBody UserPatchDto dto) {
        log.info("PATCH /users/me");
        Long userId = (Long) authentication.getPrincipal();
        return userService.patchMe(userId, dto);
    }

    // TODO Delete user

}