package com.dangdang.boldpaws.common.exception.constants;

import lombok.Getter;

@Getter
public enum ErrorCode {
    NOT_FOUND_MEMBER(404,"존재하지 않는 회원입니다."),
    DUPLICATE_MEMBER(400,"이미 존재하는 유저 입니다.")
    ;
    private final int status;
    private final String msg;

    ErrorCode(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }
}
