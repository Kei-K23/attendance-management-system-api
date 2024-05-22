package dev.kei.dto;

import dev.kei.entity.Department;
import dev.kei.entity.Role;
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
    private Department departmentId;
    private Role roleId;
}
