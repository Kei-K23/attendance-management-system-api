package dev.kei.service;

import dev.kei.dto.AttendanceRequestDto;
import dev.kei.entity.Attendance;
import dev.kei.exception.AlreadyMakeCheckInException;
import dev.kei.exception.NotFoundException;
import dev.kei.repository.AttendanceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;

    public AttendanceService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    @Transactional
    public void clockIn(AttendanceRequestDto attendanceRequestDto) {
        try {
            List<Attendance> attendances = findByUserId(attendanceRequestDto.getUserId());
            LocalDateTime currentTime = LocalDateTime.now();

            if(!attendances.isEmpty()) {
                Attendance lastAttendance = attendances.get(attendances.size() - 1);
                if(lastAttendance.getClockInTime().toLocalDate().equals(currentTime.toLocalDate())) {
                    throw new AlreadyMakeCheckInException("Already make check in");
                }
            }

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
            List<Attendance> attendances = findByUserId(attendanceRequestDto.getUserId());

            if (attendances.isEmpty()) {
                throw new NotFoundException("Attendance not found to make check out");
            }

            Attendance lastAttendance = attendances.get(attendances.size() - 1);
            LocalDateTime currentTime = LocalDateTime.now();

            // Check if the user has already clocked out
            if (lastAttendance.getClockOutTime() != null) {
                throw new IllegalStateException("User has already clocked out for the day");
            }

            // check attendance check out is in the same day at check in last time
            if (!lastAttendance.getClockInTime().toLocalDate().equals(currentTime.toLocalDate())) {
                throw new NotFoundException("Attendance not found to make check out");
            }

            lastAttendance.setClockOutTime(currentTime);
            lastAttendance.setPresent(false);
            attendanceRepository.save(lastAttendance);

            Duration duration = Duration.between(lastAttendance.getClockInTime(), currentTime);
            String status = duration.toHours() >= 7 ? "COMPLETED" : "UNCOMPLETED";

            Attendance newAttendance = Attendance.builder()
                    .id(lastAttendance.getId())
                    .userId(lastAttendance.getUserId())
                    .clockInTime(lastAttendance.getClockInTime())
                    .clockOutTime(currentTime)
                    .present(true)
                    .status(status)
                    .build();

            attendanceRepository.save(newAttendance);
        } catch (NotFoundException notFoundException) {
            throw new NotFoundException(notFoundException.getMessage());
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Transactional(readOnly = true)
    private List<Attendance> findByUserId(String userId) {
        return attendanceRepository.findByUserId(userId);
    }
}
