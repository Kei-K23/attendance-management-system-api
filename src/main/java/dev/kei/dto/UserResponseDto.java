package dev.kei.dto;

import dev.kei.entity.Department;
import dev.kei.entity.Role;
import dev.kei.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDto {
    private String id;
    private String name;
    private String email;
    private String phone;
    private String departmentId;
    private String roleId;

    public UserResponseDto fromUser(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .departmentId(user.getDepartmentId())
                .roleId(user.getDepartmentId())
                .phone(user.getPhone())
                .build();
    }
}
