package com.boldpaws.common.exception.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    NOT_FOUND_MEMBER("존재하지 않는 회원입니다."),
    DUPLICATE_MEMBER("이미 존재하는 유저 입니다.")
    ;
    private final String msg;
}
