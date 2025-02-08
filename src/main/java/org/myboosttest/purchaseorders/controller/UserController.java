package org.myboosttest.purchaseorders.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.myboosttest.purchaseorders.apiresponse.ApiResponse;
import org.myboosttest.purchaseorders.dto.UserRequest;
import org.myboosttest.purchaseorders.dto.UserResponse;
import org.myboosttest.purchaseorders.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse> createUser(
            @RequestBody @Valid UserRequest userRequest
    ) {
        Integer userId = userService.createUser(userRequest);
        ApiResponse response = new ApiResponse("success", "User created successfully with ID: " + userId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> findAll() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @GetMapping("/{user-id}")
    public ResponseEntity<UserResponse> findById(
            @PathVariable("user-id") Integer userId
    ) {
        return ResponseEntity.ok(userService.findById(userId));
    }

    @PutMapping
    public ResponseEntity<Void> updateUser(
            @RequestBody @Valid UserRequest userRequest
    ) {
        userService.updateUser(userRequest);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("/{user-id}")
    public ResponseEntity<String> deleteUser(
            @PathVariable("user-id") Integer userId
    ) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().body("User with ID :: " + userId + " has been deleted");
    }
}
