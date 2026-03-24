package kr.starly.backend.auth.dto;

import java.util.Date;

public record LoginResponse(
        String accessToken,
        AccountInfo account
) {

    public record AccountInfo(
            String id,
            String email,
            Date createdAt
    ) {
    }
}