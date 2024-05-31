package dev.kei.controller;

import dev.kei.dto.UserResponseDto;
import dev.kei.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> me(@RequestHeader(HttpHeaders.AUTHORIZATION) String authToken) {
        String token = authToken.substring(7);
        return ResponseEntity.status(HttpStatus.OK).body(userService.me(token));
    }
}
