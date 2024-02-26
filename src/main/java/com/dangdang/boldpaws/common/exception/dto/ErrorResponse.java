package com.dangdang.boldpaws.common.exception.dto;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponse {
    private int status;
    private Map<String, String> errors;
    public ErrorResponse(int status, Map<String, String> errors) {
        this.status = status;
        this.errors = errors;
    }

    public static void sendErrorResponse(HttpServletResponse response, int status, String contentType, String errorMessage) {
        try {
            log.debug("[sendErrorResponse] response : {} / status : {} / message : {}", response, status, errorMessage);
            Gson gson = new Gson();
            response.setCharacterEncoding("UTF-8");
            response.setContentType(contentType);
            response.setStatus(status);
            ErrorResponse resp = new ErrorResponse(status, Map.of("message", errorMessage));
            response.getWriter().println(gson.toJson(resp));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            log.error("[sendErrorResponse] IOException 발생 / response : {} / status : {} / message : {}", response, status, errorMessage);
        }
    }
}
