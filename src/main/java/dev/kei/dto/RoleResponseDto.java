package dev.kei.dto;

import dev.kei.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleResponseDto {
    private String id;
    private String name;
    private String permissions;

    public RoleResponseDto fromRole(Role role) {
        return RoleResponseDto.builder()
                .id(role.getId())
                .name(role.getName())
                .permissions(role.getPermissions())
                .build();
    }
}
