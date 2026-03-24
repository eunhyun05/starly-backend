package kr.starly.backend.auth.dto;

import java.util.Date;

public record LoginResponse(
        String token,
        Account account
) {

    record Account(
            String id,
            String email,
            Date createdAt
    ) {
    }
}