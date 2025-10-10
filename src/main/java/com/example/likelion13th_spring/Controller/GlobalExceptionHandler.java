package com.example.likelion13th_spring.Controller;

import com.example.likelion13th_spring.exception.NameAlreadyExistsException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NameAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleNameAlreadyExistsException(NameAlreadyExistsException e, HttpServletRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.CONFLICT.value(), // int 타입 상태 코드
                e.getMessage(),              // String 타입 메시지
                request.getRequestURI()      // String 타입 요청 경로
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }
}

class ErrorResponse {
    private final int status;
    private final String message;
    private final String path;

    public ErrorResponse(int status, String message, String path) {
        this.status = status;
        this.message = message;
        this.path = path;
    }
    //? 하 어렵다
    public int getStatus() { return status; }
    public String getMessage() { return message; }
    public String getPath() { return path; }
}