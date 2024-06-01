package dev.kei.service;

import dev.kei.dto.RoleRequestDto;
import dev.kei.dto.RoleResponseDto;
import dev.kei.entity.Role;
import dev.kei.repository.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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

    @Transactional
    public RoleResponseDto update(RoleRequestDto roleRequestDto, String id) {
        Optional<Role> existingRole = roleRepository.findById(id);
        if(existingRole.isEmpty()) {
            throw new NoSuchElementException("Role id " + id + " does not exist to update");
        }

        Role role = roleRequestDto.toRole(roleRequestDto);
        role.setId(existingRole.get().getId());
        roleRepository.save(role);

        RoleResponseDto roleResponseDto = new RoleResponseDto();
        return roleResponseDto.fromRole(role);
    }

    @Transactional
    public void delete(String id) {
        Optional<Role> existingRole = roleRepository.findById(id);
        if(existingRole.isEmpty()) {
            throw new NoSuchElementException("Role id " + id + " does not exist to delete");
        }
        roleRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<RoleResponseDto> findAll() {
        return roleRepository.findAll().stream().map(this::mapTo).toList();
    }

    @Transactional(readOnly = true)
    public RoleResponseDto findById(String id) {
        return mapTo(roleRepository.findById(id).get());
    }

    private RoleResponseDto mapTo(Role role) {
        return RoleResponseDto.builder()
               .id(role.getId())
               .name(role.getName())
               .permissions(role.getPermissions())
               .build();
    }
}
