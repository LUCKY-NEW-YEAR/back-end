package com.lucky.newyear.utill;

import org.springframework.http.HttpStatus;

public class NyException extends RuntimeException {
    private final HttpStatus status;
    private final String message;

    // 생성자
    public NyException(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }

    // 상태 코드 반환
    public HttpStatus getStatus() {
        return status;
    }

    // 메시지 반환
    @Override
    public String getMessage() {
        return message;
    }
}
