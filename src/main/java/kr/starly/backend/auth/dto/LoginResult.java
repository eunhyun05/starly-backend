package kr.starly.backend.auth.dto;

public record LoginResult(
        String refreshToken,
        LoginResponse response
) {
}