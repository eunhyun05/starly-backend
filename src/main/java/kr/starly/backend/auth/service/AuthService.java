package kr.starly.backend.auth.service;

import kr.starly.backend.auth.dto.RegisterRequest;
import kr.starly.backend.auth.dto.RegisterResponse;
import reactor.core.publisher.Mono;

public interface AuthService {

    Mono<RegisterResponse> register(RegisterRequest request);
}