package com.boldpaws.common.exception.dto;

import com.boldpaws.common.exception.constants.ApiStatusCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResult {
    private String status;
    private Object message;
    public ApiResult(String status, Object message) {
        this.status = status;
        this.message = message;
    }
    public static ApiResult success(Object message) {
        return new ApiResult(ApiStatusCode.SUCCESS.name(), message);
    }
    public static ApiResult fail(Object message) {
        return new ApiResult(ApiStatusCode.FAIL.name(), message);
    }
}
