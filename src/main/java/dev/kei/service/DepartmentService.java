package dev.kei.service;

import dev.kei.dto.DepartmentRequestDto;
import dev.kei.dto.DepartmentResponseDto;
import dev.kei.entity.Department;
import dev.kei.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public DepartmentResponseDto save(DepartmentRequestDto departmentRequestDto) {
        Department department = departmentRequestDto.toDepartment(departmentRequestDto);

        departmentRepository.save(department);

        DepartmentResponseDto departmentResponseDto = new DepartmentResponseDto();
        return departmentResponseDto.fromDepartment(department);
    }

    public List<DepartmentResponseDto> findAll() {
        return departmentRepository.findAll().stream().map(this::mapTo).toList();
    }

    private DepartmentResponseDto mapTo(Department department) {
        return DepartmentResponseDto.builder()
               .id(department.getId())
               .name(department.getName())
               .description(department.getDescription())
               .build();
    }
}
