package kr.starly.backend.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessCode {

    OK(HttpStatus.OK),
    CREATED(HttpStatus.CREATED),
    ACCEPTED(HttpStatus.ACCEPTED),
    NO_CONTENT(HttpStatus.NO_CONTENT);

    private final HttpStatus status;
}