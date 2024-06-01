package dev.kei.advice;

import dev.kei.dto.CustomErrorResponseDto;
import dev.kei.exception.AlreadyMakeCheckInException;
import dev.kei.exception.MissingAuthHeaderException;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<CustomErrorResponseDto> handleRuntimeException(MissingAuthHeaderException ex) {
        log.info("GlobalExceptionHandler::handleRuntimeException exception caught: {} ", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(CustomErrorResponseDto.builder()
                .status("SERVER-ERROR")
                .code(500)
                .message(ex.getMessage())
                .build());
    }

    @ExceptionHandler(AlreadyMakeCheckInException.class)
    public ResponseEntity<CustomErrorResponseDto> handleAlreadyMakeCheckInException(AlreadyMakeCheckInException ex) {
        log.info("GlobalExceptionHandler::handleAlreadyMakeCheckInException exception caught: {} ", ex.getMessage());
        return ResponseEntity.status(400).body(CustomErrorResponseDto.builder()
                .status("ALREADY-CHECK-IN")
                .code(400)
                .message(ex.getMessage())
                .build());
    }

    @ExceptionHandler(MissingAuthHeaderException.class)
    public ResponseEntity<CustomErrorResponseDto> handleMissingAuthHeaderException(MissingAuthHeaderException ex) {
        log.info("GlobalExceptionHandler::handleMissingAuthHeaderException exception caught: {} ", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(CustomErrorResponseDto.builder()
                .status("MISSING-AUTH-TOKEN")
                .code(401)
                .message(ex.getMessage())
                .build());
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<CustomErrorResponseDto> handleExpiredJWTException(ExpiredJwtException ex) {
        log.info("GlobalExceptionHandler::handleExpiredJWTException exception caught: {} ", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(CustomErrorResponseDto.builder()
                .status("EXPIRED-JWT-TOKEN")
                .code(401)
                .message(ex.getMessage())
                .build());
    }
}