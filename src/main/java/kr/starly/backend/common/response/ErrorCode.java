package kr.starly.backend.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    BAD_REQUEST(HttpStatus.BAD_REQUEST),
    EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT),
    INVALID_EMAIL(HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST);

    private final HttpStatus status;
}