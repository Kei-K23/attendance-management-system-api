package dev.kei.controller;

import dev.kei.dto.DepartmentRequestDto;
import dev.kei.dto.DepartmentResponseDto;
import dev.kei.service.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/departments")
public class DepartmentController {
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    public ResponseEntity<DepartmentResponseDto> save(@RequestBody DepartmentRequestDto departmentRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(departmentService.save(departmentRequestDto));
    }

    @GetMapping
    public ResponseEntity<List<DepartmentResponseDto>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(departmentService.findAll());
    }
}