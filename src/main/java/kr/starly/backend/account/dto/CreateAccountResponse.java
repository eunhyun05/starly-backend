package kr.starly.backend.account.dto;

import java.util.Date;

public record CreateAccountResponse(
        String id,
        String email,
        Date createdAt
) {
}