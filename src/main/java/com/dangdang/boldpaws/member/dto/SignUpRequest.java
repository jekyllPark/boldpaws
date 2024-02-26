package com.dangdang.boldpaws.member.dto;

import com.dangdang.boldpaws.member.domain.entity.Member;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class SignUpRequest {
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "유효한 이메일 주소 형식이 아닙니다.")
    private String email;
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "비밀번호는 영문, 숫자, 특수문자를 모두 하나 이상 포함해야 합니다.")
    private String password;
    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$", message = "유효한 전화번호 형식이 아닙니다. (XXX-XXXX-XXXX 또는 XXX-XXX-XXXX)")
    private String hp;
    @NotEmpty(message = "사용자의 이름이 비어있습니다.")
    private String name;

    public static Member toEntity(SignUpRequest req, PasswordEncoder passwordEncoder) {
        return Member.builder()
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .hp(req.getHp())
                .name(req.getName())
                .build();
    }
}
