package dev.kei.dto;

import dev.kei.entity.Department;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepartmentRequestDto {
    private String name;
    private String description;

    public Department toDepartment(DepartmentRequestDto departmentRequestDto) {
        return Department.builder()
                .name(departmentRequestDto.getName())
                .description(departmentRequestDto.getDescription())
                .build();
    }
}
