package kr.starly.backend.auth.service;

import kr.starly.backend.account.dto.CreateAccountRequest;
import kr.starly.backend.account.service.AccountService;
import kr.starly.backend.auth.dto.RegisterRequest;
import kr.starly.backend.auth.dto.RegisterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AccountService accountService;

    public Mono<RegisterResponse> register(RegisterRequest request) {
        return accountService.create(new CreateAccountRequest(request.email(), request.password()))
                .map(response -> new RegisterResponse(response.id(), response.email(), response.createdAt()));
    }
}