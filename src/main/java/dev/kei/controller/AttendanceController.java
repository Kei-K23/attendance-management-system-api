package dev.kei.controller;

import dev.kei.dto.AttendanceRequestDto;
import dev.kei.entity.Attendance;
import dev.kei.service.AttendanceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/attendance")
public class AttendanceController {
    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping("/check-in")
    public ResponseEntity<Void> clockIn(@Valid @RequestBody AttendanceRequestDto attendanceRequestDto) {
        attendanceService.clockIn(attendanceRequestDto);
        return  ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @PostMapping("/check-out")
    public ResponseEntity<Void> clockOut(@Valid @RequestBody AttendanceRequestDto attendanceRequestDto) {
        attendanceService.clockOut(attendanceRequestDto);
        return  ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Attendance>> findByUserId(@PathVariable String userId) {
        return  ResponseEntity.status(HttpStatus.OK).body(attendanceService.findByUserId(userId));
    }

    @GetMapping
    public ResponseEntity<List<Attendance>> findByUserId() {
        return  ResponseEntity.status(HttpStatus.OK).body(attendanceService.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        attendanceService.delete(id);
        return  ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
