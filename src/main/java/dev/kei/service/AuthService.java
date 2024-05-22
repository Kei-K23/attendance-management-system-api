package dev.kei.service;

import dev.kei.dto.LoginRequestDto;
import dev.kei.dto.LoginResponseDto;
import dev.kei.dto.UserRequestDto;
import dev.kei.dto.UserResponseDto;
import dev.kei.entity.User;
import dev.kei.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(JwtService jwtService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // create user
    public UserResponseDto register(UserRequestDto userRequestDto) {
        User user = User.builder()
                .name(userRequestDto.getName())
                .email(userRequestDto.getEmail())
                .phone(userRequestDto.getPhone())
                .departmentId(userRequestDto.getDepartmentId())
                .roleId(userRequestDto.getRoleId())
                .password(passwordEncoder.encode(userRequestDto.getPassword()))
                .build();
        userRepository.save(user);
        UserResponseDto userResponseDto = new UserResponseDto();
        return userResponseDto.fromUser(user);
    }

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        Optional<User> user = userRepository.findByName(loginRequestDto.getName());
        if (user.isEmpty()) {
            throw new RuntimeException("User not found with username " + loginRequestDto.getName());
        }

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.get().getPassword())) {
            throw new RuntimeException("Password is incorrect");
        }

        String token = jwtService.generateToken(loginRequestDto.getName());
        return LoginResponseDto.builder().accessToken(token).build();
    }
}
