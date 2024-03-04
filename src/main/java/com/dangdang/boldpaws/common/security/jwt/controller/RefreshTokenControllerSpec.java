package com.dangdang.boldpaws.common.security.jwt.controller;

import com.dangdang.boldpaws.common.security.jwt.dto.AccessTokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
@Tag(name = "refreshToken", description = "토큰 발급 API 입니다.")
public interface RefreshTokenControllerSpec {
    @Operation(
            description = "토큰 발급 API",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공",
                            content = {@Content(mediaType = "application/json", examples = {@ExampleObject(value = "{\n" +
                                    "    \"status\": \"SUCCESS\",\n" +
                                    "    \"message\": {\n" +
                                    "        \"accessToken\": \"토큰 값\",\n" +
                                    "    }\n" +
                                    "}")})})
            }
    )
    @PostMapping
    ResponseEntity<AccessTokenResponse> createAccessToken(HttpServletRequest request);
}
