package kr.starly.backend.auth.service;

import kr.starly.backend.auth.dto.*;
import reactor.core.publisher.Mono;

public interface AuthService {

    Mono<RegisterResponse> register(RegisterRequest request);

    Mono<LoginResult> login(LoginRequest request);

    Mono<RefreshResponse> refresh(String refreshToken);

    Mono<Void> logout(String refreshToken);
}