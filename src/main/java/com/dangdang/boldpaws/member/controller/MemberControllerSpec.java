package com.dangdang.boldpaws.member.controller;

import com.dangdang.boldpaws.common.exception.dto.ApiResult;
import com.dangdang.boldpaws.member.dto.SignUpRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "member", description = "사용자 관련 API 입니다.")
public interface MemberControllerSpec {
    @Operation(
            description = "회원가입 요청을 위한 API 입니다.",
            parameters = {
                    @Parameter(name = "email", description = "이메일", example = "temp@boldpaws.com", required = true),
                    @Parameter(name = "password", description = "비밀번호", example = "asdf123!", required = true),
                    @Parameter(name = "hp", description = "전화번호", example = "김당당", required = true),
                    @Parameter(name = "name", description = "이름", example = "010-1234-5678", required = true),
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공",
                            content = {@Content(mediaType = "application/json", examples = {@ExampleObject(value = "{\"status\": \"200\"}")})}
                    ),
                    @ApiResponse(responseCode = "4xx", description = "실패",
                            content = {@Content(mediaType = "application/json", examples = {@ExampleObject(value = "" +
                                    "{\n" +
                                    "    \"status\": 400,\n" +
                                    "    \"errors\": {\n" +
                                    "        \"email\": \"유효한 이메일 주소 형식이 아닙니다.\",\n" +
                                    "        \"password\": \"비밀번호는 영문, 숫자, 특수문자를 모두 하나 이상 포함해야하며 8자 이상이어야 합니다.\",\n" +
                                    "        \"hp\": \"유효한 전화번호 형식이 아닙니다. (XXX-XXXX-XXXX 또는 XXX-XXX-XXXX)\",\n" +
                                    "        \"name\": \"사용자의 이름이 비어있습니다.\"\n" +
                                    "    }\n" +
                                    "}")})}
                    )
            }
    )
    @PostMapping
    ResponseEntity<ApiResult> signUp(@RequestBody @Valid SignUpRequest req);
}
