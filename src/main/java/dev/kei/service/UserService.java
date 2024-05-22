//package dev.kei.service;
//
//import dev.kei.dto.UserRequestDto;
//import dev.kei.dto.UserResponseDto;
//import dev.kei.entity.User;
//import dev.kei.repository.UserRepository;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service
//public class UserService {
//    private final UserRepository userRepository;
//
//    public UserService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @Transactional
//    public UserResponseDto save(UserRequestDto userRequestDto) {
//        User user = User.builder()
//                .name(userRequestDto.getName())
//                .email(userRequestDto.getEmail())
//                .password()
//                .build();
//        return userRepository.save(userRequestDto);
//    }
//}
