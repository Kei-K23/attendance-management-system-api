package dev.kei.advice;

import dev.kei.dto.CustomErrorResponseDto;
import dev.kei.exception.MissingAuthHeaderException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MissingAuthHeaderException.class)
    public ResponseEntity<CustomErrorResponseDto> handleMissingAuthHeaderException(MissingAuthHeaderException ex) {
        log.info("GlobalExceptionHandler::handleMissingAuthHeaderException exception caught: {} ", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(CustomErrorResponseDto.builder()
                .status("MISSING-AUTH-TOKEN")
                .code(401)
                .message(ex.getMessage())
                .build());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<CustomErrorResponseDto> handleRuntimeException(MissingAuthHeaderException ex) {
        log.info("GlobalExceptionHandler::handleRuntimeException exception caught: {} ", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(CustomErrorResponseDto.builder()
                .status("SERVER-ERROR")
                .code(500)
                .message(ex.getMessage())
                .build());
    }
}