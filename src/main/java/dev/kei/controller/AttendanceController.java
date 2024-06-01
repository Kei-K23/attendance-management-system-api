package dev.kei.controller;

import dev.kei.dto.AttendanceRequestDto;
import dev.kei.service.AttendanceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/attendance")
public class AttendanceController {
    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping("/check-in")
    public ResponseEntity<Void> clockIn(@RequestBody AttendanceRequestDto attendanceRequestDto) {
        attendanceService.clockIn(attendanceRequestDto);
        return  ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @PostMapping("/check-out")
    public ResponseEntity<Void> clockOut(@RequestBody AttendanceRequestDto attendanceRequestDto) {
        attendanceService.clockIn(attendanceRequestDto);
        return  ResponseEntity.status(HttpStatus.CREATED).body(null);
    }
}
