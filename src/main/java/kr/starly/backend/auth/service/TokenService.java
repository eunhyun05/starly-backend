package kr.starly.backend.auth.service;

import reactor.core.publisher.Mono;

public interface TokenService {

    Mono<Void> saveRefreshToken(String accountId, String refreshToken);

    Mono<String> getRefreshToken(String accountId);

    Mono<Void> deleteRefreshToken(String accountId);
}