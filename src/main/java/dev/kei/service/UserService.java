package dev.kei.service;

import dev.kei.dto.UserRequestDto;
import dev.kei.dto.UserResponseDto;
import dev.kei.entity.User;
import dev.kei.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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
        User user = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("User not found with id " + id));
        UserResponseDto userResponseDto = new UserResponseDto();
        return userResponseDto.fromUser(user);
    }

    @Transactional
    public UserResponseDto update(String id, UserRequestDto userRequestDto) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()) {
            throw new NoSuchElementException("User id " + id + " does not exist to update");
        }

        User updatedUser = User.builder()
                .id(user.get().getId())
                .name(userRequestDto.getName())
                .email(userRequestDto.getEmail())
                .phone(userRequestDto.getPhone())
                .password(user.get().getPassword())
                .departmentId(userRequestDto.getDepartmentId())
                .roleId(userRequestDto.getRoleId())
                .build();
        userRepository.save(updatedUser);

        UserResponseDto userResponseDto = new UserResponseDto();
        return userResponseDto.fromUser(updatedUser);
    }

    @Transactional
    public void delete(String id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()) {
            throw new NoSuchElementException("User id " + id + " does not exist to delete");
        }
        userRepository.deleteById(id);
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
