package com.example.hotel_back.common.exception;

import com.example.hotel_back.common.exception.user.DuplicationUserException;
import com.example.hotel_back.common.exception.user.FailVerifiedEmailException;
import com.example.hotel_back.common.exception.user.NoSuchMemberException;
import com.example.hotel_back.common.exception.user.NotMatchPasswordException;
import com.example.hotel_back.common.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler(DuplicationUserException.class)
    public ResponseEntity<ApiResponse<Object>> handlerDuplicationException(DuplicationUserException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.fail(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(NoSuchMemberException.class)
    public ResponseEntity<ApiResponse<Object>> handlerNoSuchMemberException(NoSuchMemberException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.fail(e.getMessage(), HttpStatus.UNAUTHORIZED.value()));
    }

    @ExceptionHandler(NotMatchPasswordException.class)
    public ResponseEntity<ApiResponse<Object>> handlerNoMatchPassword(NotMatchPasswordException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.fail(e.getMessage(), HttpStatus.UNAUTHORIZED.value()));

    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handlerNoResourceFoundException(Exception e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.fail("불편을 드려 죄송합니다. 해당 내용은 제공하지 않는 서비스입니다.", HttpStatus.NOT_FOUND.value()));
    }

    @ExceptionHandler(FailVerifiedEmailException.class)
    public ResponseEntity<ApiResponse<Object>> handlerFailVerifiedEmailException(Exception e) {
        return ResponseEntity
                .status(HttpStatus.NON_AUTHORITATIVE_INFORMATION)
                .body(ApiResponse.fail("이메일 인증에 실패하였습니다.", HttpStatus.NON_AUTHORITATIVE_INFORMATION.value()));
    }
}
