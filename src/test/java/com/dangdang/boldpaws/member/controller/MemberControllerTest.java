package com.dangdang.boldpaws.member.controller;

import com.dangdang.boldpaws.common.RootTest;
import com.dangdang.boldpaws.common.exception.constants.ApiStatusCode;
import com.dangdang.boldpaws.common.util.JsonConverter;
import com.dangdang.boldpaws.member.dto.SignUpRequest;
import com.dangdang.boldpaws.member.exception.DuplicateMemberException;
import com.dangdang.boldpaws.member.fixture.SignUpFixture;
import com.dangdang.boldpaws.member.service.MemberService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(MemberController.class)
class MemberControllerTest extends RootTest {
    private MemberController memberController;
    @MockBean
    private MemberService memberService;
    private MockMvc mockMvc;
    private Validator validator;
    private static final String MEMBER_API = "/member";

    @BeforeEach
    void setUp(WebApplicationContext applicationContext, RestDocumentationContextProvider contextProvider) {
        mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext)
                .apply(documentationConfiguration(contextProvider))
                .build();
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
        memberController = new MemberController(memberService);
    }

    @Test
    @DisplayName("회원가입에 성공하면 200 코드를 반환한다.")
    void 회원가입_성공() throws Exception {
        // given & when
        mockMvc.perform(post(MEMBER_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonConverter.toJsonString(SignUpFixture.VALID_SIGN_UP_REQ)))
                .andDo(print())
                // then
                .andExpect(status().isOk())
                .andDo(document("/member/signUp/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("email").type(JsonFieldType.STRING)
                                        .description("사용자 이메일"),
                                fieldWithPath("password").type(JsonFieldType.STRING)
                                        .description("하나 이상의 영문, 숫자, 특수문자를 포함한 8자 이상의 비밀번호"),
                                fieldWithPath("hp").type(JsonFieldType.STRING)
                                        .description("사용자 전화번호"),
                                fieldWithPath("name").type(JsonFieldType.STRING)
                                        .description("사용자 이름")
                        )
                ));
    }

    @Test
    @DisplayName("이미 등록된 이메일로 가입 시에 DuplicateMemberException 예외가 발생한다.")
    void 회원가입_중복() throws Exception {
        // given
        Mockito.when(memberController.signUp(any(SignUpRequest.class))).thenThrow(DuplicateMemberException.class);

        // when
        mockMvc.perform(post(MEMBER_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonConverter.toJsonString(SignUpFixture.VALID_SIGN_UP_REQ)))
                .andDo(print())
                // then
                .andExpect(jsonPath("$.status").value(ApiStatusCode.FAIL.name()))
                .andExpect(jsonPath("$.message.error").value("이미 존재하는 유저 입니다."))
                .andDo(document("member/signUp/duplicateMember",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }

    @Test
    @DisplayName("회원가입 시 필수 인자가 누락된 경우 400 에러를 반환한다.")
    void 회원가입_유효성_실패() throws Exception {
        // given & when
        mockMvc.perform(post(MEMBER_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonConverter.toJsonString(SignUpFixture.INVALID_SIGN_UP_REQ)))
                .andDo(print())
                // then
                .andExpect(jsonPath("$.status").value(ApiStatusCode.FAIL.name()))
                .andExpect(jsonPath("$.message.email").value("유효한 이메일 주소 형식이 아닙니다."))
                .andExpect(jsonPath("$.message.password").value("비밀번호는 영문, 숫자, 특수문자를 모두 하나 이상 포함해야하며 8자 이상이어야 합니다."))
                .andExpect(jsonPath("$.message.hp").value("유효한 전화번호 형식이 아닙니다. (XXX-XXXX-XXXX 또는 XXX-XXX-XXXX)"))
                .andExpect(jsonPath("$.message.name").value("사용자의 이름이 비어있습니다."))
                .andDo(document("member/signUp/validationFail",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }

    @Test
    @DisplayName("회원가입 요청 객체의 모든 유효성을 통과한다.")
    void 유효성_통과() {
        validator.validate(SignUpFixture.VALID_SIGN_UP_REQ);
    }

    @Test
    @DisplayName("회원가입 요청 객체의 유효성을 통과하지 못한다.")
    void 유효성_통과_실패() {
        Set<ConstraintViolation<SignUpRequest>> violations = validator.validate(SignUpFixture.INVALID_SIGN_UP_REQ);
        assertTrue(violations.stream().anyMatch(violation -> violation.getPropertyPath().toString().equals("email")));
        assertTrue(violations.stream().anyMatch(violation -> violation.getPropertyPath().toString().equals("password")));
        assertTrue(violations.stream().anyMatch(violation -> violation.getPropertyPath().toString().equals("hp")));
        assertTrue(violations.stream().anyMatch(violation -> violation.getPropertyPath().toString().equals("name")));
    }

}