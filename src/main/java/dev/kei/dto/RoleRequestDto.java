package dev.kei.dto;

import dev.kei.entity.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleRequestDto {
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Permissions is required")
    private String permissions;

    public Role toRole(RoleRequestDto roleRequestDto) {
        return Role.builder()
                .name(roleRequestDto.getName())
                .permissions(roleRequestDto.getPermissions())
                .build();
    }
}
