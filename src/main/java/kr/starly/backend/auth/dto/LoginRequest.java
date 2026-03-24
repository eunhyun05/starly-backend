package kr.starly.backend.auth.dto;

import kr.starly.backend.common.annotation.ValidEmail;
import kr.starly.backend.common.annotation.ValidPassword;

public record LoginRequest(
        @ValidEmail
        String email,
        @ValidPassword
        String password
) {
}