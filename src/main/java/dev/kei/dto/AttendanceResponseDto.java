package dev.kei.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttendanceResponseDto {
    private String id;
    private String userId;
    private LocalDateTime clockInTime;
    private LocalDateTime clockOutTime;
    private Boolean present;
    private String status;
}