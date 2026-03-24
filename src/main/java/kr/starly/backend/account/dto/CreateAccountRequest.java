package kr.starly.backend.account.dto;

import kr.starly.backend.common.annotation.ValidEmail;
import kr.starly.backend.common.annotation.ValidPassword;

public record CreateAccountRequest(
        @ValidEmail
        String email,
        @ValidPassword
        String password
) {
}