package dev.kei.service;

import dev.kei.dto.DepartmentRequestDto;
import dev.kei.dto.DepartmentResponseDto;
import dev.kei.entity.Department;
import dev.kei.repository.DepartmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Transactional
    public DepartmentResponseDto save(DepartmentRequestDto departmentRequestDto) {
        Department department = departmentRequestDto.toDepartment(departmentRequestDto);

        departmentRepository.save(department);

        DepartmentResponseDto departmentResponseDto = new DepartmentResponseDto();
        return departmentResponseDto.fromDepartment(department);
    }

    @Transactional
    public DepartmentResponseDto update(String id, DepartmentRequestDto departmentRequestDto) {
        Optional<Department> departmentOptional = departmentRepository.findById(id);
        if (departmentOptional.isEmpty()) {
            throw new NoSuchElementException("Department id " + id + " does not exist to update");
        }

        Department department = departmentRequestDto.toDepartment(departmentRequestDto);
        department.setId(departmentOptional.get().getId());
        departmentRepository.save(department);

        DepartmentResponseDto departmentResponseDto = new DepartmentResponseDto();
        return departmentResponseDto.fromDepartment(department);
    }

    @Transactional
    public void delete(String id) {
        Optional<Department> departmentOptional = departmentRepository.findById(id);
        if (departmentOptional.isEmpty()) {
            throw new NoSuchElementException("Department id " + id + " does not exist to delete");
        }
        departmentRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<DepartmentResponseDto> findAll() {
        return departmentRepository.findAll().stream().map(this::mapTo).toList();
    }

    @Transactional(readOnly = true)
    public DepartmentResponseDto findById(String id) {
        return mapTo(departmentRepository.findById(id).get());
    }

    private DepartmentResponseDto mapTo(Department department) {
        return DepartmentResponseDto.builder()
               .id(department.getId())
               .name(department.getName())
               .description(department.getDescription())
               .build();
    }
}
