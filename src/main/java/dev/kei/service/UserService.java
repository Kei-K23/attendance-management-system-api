package dev.kei.service;

import dev.kei.dto.UserRequestDto;
import dev.kei.dto.UserResponseDto;
import dev.kei.entity.User;
import dev.kei.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserResponseDto save(UserRequestDto userRequestDto) {
        User user = User.builder()
                .name(userRequestDto.getName())
                .email(userRequestDto.getEmail())
                .password(passwordEncoder.encode(userRequestDto.getPassword()))
                .build();
        userRepository.save(user);
        UserResponseDto userResponseDto = new UserResponseDto();
        return userResponseDto.fromUser(user);
    }

    @Transactional(readOnly = true)
    public UserResponseDto findById(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id " + id));
        UserResponseDto userResponseDto = new UserResponseDto();
        return userResponseDto.fromUser(user);
    }
}
