package com.boldpaws.common.exception.advice;

import com.boldpaws.common.exception.constants.ErrorCode;
import com.boldpaws.common.exception.dto.ApiResult;
import com.boldpaws.member.exception.DuplicateMemberException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<ApiResult> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResult.fail(errors));
    }
    @ExceptionHandler(DuplicateMemberException.class)
    private ResponseEntity<ApiResult> duplicateUserIdException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResult.fail(Map.of("error", ErrorCode.DUPLICATE_MEMBER.getMsg())));
    }
}
