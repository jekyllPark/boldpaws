package com.dangdang.boldpaws.common.exception.advice;

import com.dangdang.boldpaws.common.exception.dto.ApiResult;
import com.dangdang.boldpaws.member.exception.DuplicateMemberException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

import static com.dangdang.boldpaws.common.exception.constants.ErrorCode.DUPLICATE_MEMBER;
import static com.dangdang.boldpaws.common.exception.dto.ApiResult.fail;

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
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(fail(errors));
    }
    @ExceptionHandler(DuplicateMemberException.class)
    private ResponseEntity<ApiResult> duplicateUserIdException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(fail(Map.of("error", DUPLICATE_MEMBER.getMsg())));
    }
}
