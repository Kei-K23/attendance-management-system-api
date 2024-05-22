package dev.kei.service;

import dev.kei.dto.RoleRequestDto;
import dev.kei.dto.RoleResponseDto;
import dev.kei.entity.Role;
import dev.kei.repository.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Transactional
    public RoleResponseDto save(RoleRequestDto roleRequestDto) {
        try {
            Role role = roleRequestDto.toRole(roleRequestDto);
            roleRepository.save(role);
            RoleResponseDto roleResponseDto = new RoleResponseDto();
            return roleResponseDto.fromRole(role);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<RoleResponseDto> findAll() {
        try {
            return roleRepository.findAll().stream().map(this::mapTo).toList();
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    private RoleResponseDto mapTo(Role role) {
        return RoleResponseDto.builder()
               .id(role.getId())
               .name(role.getName())
               .permissions(role.getPermissions())
               .build();
    }
}
