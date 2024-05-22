package dev.kei.controller;

import dev.kei.dto.RoleRequestDto;
import dev.kei.dto.RoleResponseDto;
import dev.kei.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<RoleResponseDto> save(@RequestBody RoleRequestDto roleRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roleService.save(roleRequestDto));
    }

    @GetMapping
    public ResponseEntity<List<RoleResponseDto>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(roleService.findAll());
    }
}
