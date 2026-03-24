package kr.starly.backend.auth.dto;

import java.util.Date;

public record RegisterResponse(
        String id,
        String email,
        Date createdAt
) {
}