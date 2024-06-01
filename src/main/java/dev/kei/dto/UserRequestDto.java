package dev.kei.dto;

import dev.kei.entity.Department;
import dev.kei.entity.Role;
import dev.kei.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestDto {
    @NotBlank(message = "User name is required")
    private String name;
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    private String email;
    @NotBlank(message = "Password is required")
    private String password;
    private String phone;
    @NotBlank(message = "Department id is required")
    private String departmentId;
    @NotBlank(message = "Role id is required")
    private String roleId;
}
