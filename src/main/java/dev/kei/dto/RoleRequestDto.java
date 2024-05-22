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
public class RoleRequestDto {
    private String name;
    private String permissions;

    public Role toRole(RoleRequestDto roleRequestDto) {
        return Role.builder()
                .name(roleRequestDto.getName())
                .permissions(roleRequestDto.getPermissions())
                .build();
    }
}
