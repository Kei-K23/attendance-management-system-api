package dev.kei.service;

import dev.kei.dto.AttendanceRequestDto;
import dev.kei.entity.Attendance;
import dev.kei.exception.AlreadyMakeCheckInException;
import dev.kei.repository.AttendanceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;

    public AttendanceService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    @Transactional
    public void clockIn(AttendanceRequestDto attendanceRequestDto) {
        try {
            Optional<Attendance> attendance = findByUserId(attendanceRequestDto.getUserId());
            if (attendance.isPresent()) {
                throw new AlreadyMakeCheckInException("Already make check in");
            }

            LocalDateTime currentTime = LocalDateTime.now();
            Attendance newAttendance = Attendance.builder()
                    .userId(attendanceRequestDto.getUserId())
                    .clockInTime(currentTime)
                    .present(true)
                    .status("PENDING")
                    .build();

            attendanceRepository.save(newAttendance);
        } catch (AlreadyMakeCheckInException alreadyMakeCheckInException) {
            throw new AlreadyMakeCheckInException(alreadyMakeCheckInException.getMessage());
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Transactional
    public void clockOut(AttendanceRequestDto attendanceRequestDto) {
        try {
            Optional<Attendance> attendance = findByUserId(attendanceRequestDto.getUserId());
            if (attendance.isEmpty()) {
                throw new AlreadyMakeCheckInException("Already make check in");
            }

            LocalDateTime currentTime = LocalDateTime.now();
            Attendance newAttendance = Attendance.builder()
                    .userId(attendanceRequestDto.getUserId())
                    .clockInTime(currentTime)
                    .present(true)
                    .status("PENDING")
                    .build();

            attendanceRepository.save(newAttendance);
        } catch (AlreadyMakeCheckInException alreadyMakeCheckInException) {
            throw new AlreadyMakeCheckInException(alreadyMakeCheckInException.getMessage());
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Transactional(readOnly = true)
    private Optional<Attendance> findByUserId(String userId) {
        try {
            return attendanceRepository.findByUserId(userId);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
}
