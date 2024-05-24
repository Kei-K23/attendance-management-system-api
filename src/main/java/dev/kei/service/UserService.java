package dev.kei.service;

import dev.kei.dto.UserResponseDto;
import dev.kei.entity.User;
import dev.kei.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository,
                       JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @Transactional(readOnly = true)
    public UserResponseDto findById(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id " + id));
        UserResponseDto userResponseDto = new UserResponseDto();
        return userResponseDto.fromUser(user);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> findAll() {
        return userRepository.findAll().stream().map(user -> {
            UserResponseDto userResponseDto = new UserResponseDto();
            return userResponseDto.fromUser(user);
        }).toList();
    }

    @Transactional(readOnly = true)
    public UserResponseDto me(String token) {
        String name = jwtService.extractUsername(token);

        User user = userRepository.findByName(name).orElseThrow(() -> new RuntimeException("User not found with name " + name));
        UserResponseDto userResponseDto = new UserResponseDto();
        return userResponseDto.fromUser(user);
    }
}
