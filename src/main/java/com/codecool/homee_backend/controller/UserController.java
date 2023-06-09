package com.codecool.homee_backend.controller;


import com.codecool.homee_backend.controller.dto.homeeuser.*;
import com.codecool.homee_backend.service.HomeeUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.mail.MessagingException;
import java.util.List;
import java.util.UUID;

import static com.codecool.homee_backend.config.auth.SpringSecurityConfig.USER;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final HomeeUserService homeeUserService;


    public UserController(HomeeUserService homeeUserService) {
        this.homeeUserService = homeeUserService;
    }

    @RolesAllowed(USER)
    @GetMapping
    public List<HomeeUserDto> getAllUsers() { return homeeUserService.getAllHomeeUsers(); }

    @RolesAllowed(USER)
    @GetMapping(params = "spaceId")
    public List<HomeeUserDto> getUsersForSpace(@RequestParam UUID spaceId) {
        return homeeUserService.getUsersForSpace(spaceId);
    }

    @RolesAllowed(USER)
    @GetMapping("/{id}")
    public HomeeUserDto getUser(@PathVariable UUID id) { return homeeUserService.getHomeeUser(id); }

    @PostMapping("/login")
    public AuthenticatedUserDto loginUser(@RequestBody LoginUserDto dto) { return homeeUserService.loginUser(dto); }

    @GetMapping(value = "/activate", params = {"userId", "code"})
    public ResponseEntity<Void> activateUser(@RequestParam UUID userId, @RequestParam Integer code) {
        return homeeUserService.activateUser(userId, code);
    }

    @RolesAllowed(USER)
    @PutMapping
    public HomeeUserDto updateUser(@RequestBody UpdatedHomeeUserDto dto) { return homeeUserService.updateUser(dto); }

    @RolesAllowed(USER)
    @PutMapping(params = "password")
    public HomeeUserDto changeUserPassword(@RequestBody ChangeHomeeUserPasswordDto dto) { return homeeUserService.changeUserPassword(dto); }

    @GetMapping(value = "/lost-password", params = {"email"})
    public void requestPasswordChange(@RequestParam String email) throws MessagingException {
        homeeUserService.requestPasswordChange(email);
    }

    @PostMapping("/lost-password")
    public void changeLostPassword(@RequestBody LostPasswordDto dto) {
        homeeUserService.changeLostPassword(dto);
    }

    @RolesAllowed(USER)
    @DeleteMapping("/{id}")
    public void softDeleteUser(@PathVariable UUID id) { homeeUserService.softDelete(id); }

    @PostMapping("/register")
    public HomeeUserDto addNewUser(@RequestBody NewHomeeUserDto newHomeeUser) throws MessagingException {
        return homeeUserService.registerUser(newHomeeUser);
    }
}
