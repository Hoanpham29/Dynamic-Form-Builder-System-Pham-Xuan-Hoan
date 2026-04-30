package com.project.DynamicFormBuilderSystem.controller;

import com.project.DynamicFormBuilderSystem.request.PasswordUpdateRequest;
import com.project.DynamicFormBuilderSystem.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User REST API Endpoints", description = "Operations related to info about current user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Password update", description = "Change user password after verification")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/password")
    public void passwordUpdate(@Valid @RequestBody PasswordUpdateRequest passwordUpdateRequest) throws Exception {
        userService.updatePassword(passwordUpdateRequest);
    }

    @Operation(summary = "Delete user", description = "Delete user account")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping
    public void deleteUser(){
        userService.deleteUser();
    }
}