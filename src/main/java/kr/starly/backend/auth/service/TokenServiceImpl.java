package kr.starly.backend.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final ReactiveStringRedisTemplate redisTemplate;

    @Value("${jwt.refresh-token-expiration}")
    private long refreshTokenExpiration;

    @Override
    public Mono<Void> saveRefreshToken(String accountId, String refreshToken) {
        return redisTemplate.opsForValue()
                .set(accountId, refreshToken, Duration.ofMillis(refreshTokenExpiration))
                .then();
    }

    public Mono<String> getRefreshToken(String accountId) {
        return redisTemplate.opsForValue().get(accountId);
    }

    public Mono<Void> deleteRefreshToken(String accountId) {
        return redisTemplate.delete(accountId).then();
    }
}