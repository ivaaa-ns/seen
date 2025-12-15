package com.ivaaan.seen.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public UserMeDto getMe() {
        log.info("GET /users/me");
        return userService.getMe(Long.parseLong("2"));
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

    @PostMapping
    public UserMeDto newUser(@RequestBody UserNewDto dto) {
        log.info("POST /users");
        return userService.newMe(dto);
    }

    @PatchMapping("/me")
    public UserMeDto patchMe(@RequestBody UserPatchDto dto) {
        log.info("PATCH /users/me");
        return userService.patchMe(dto);
    }
}