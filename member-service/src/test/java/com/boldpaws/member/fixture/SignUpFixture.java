package com.boldpaws.member.fixture;

import com.boldpaws.member.dto.SignUpRequest;

public class SignUpFixture {
    public static final SignUpRequest VALID_SIGN_UP_REQ = new SignUpRequest(
            "kim@dangdang.com", "asdf123!", "010-1234-5678", "김당당"
    );
    public static final SignUpRequest INVALID_SIGN_UP_REQ = new SignUpRequest(
            "", "", "", ""
    );
}
