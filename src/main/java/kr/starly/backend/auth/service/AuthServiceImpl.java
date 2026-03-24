package kr.starly.backend.auth.service;

import kr.starly.backend.account.dto.CreateAccountRequest;
import kr.starly.backend.account.service.AccountService;
import kr.starly.backend.auth.dto.*;
import kr.starly.backend.auth.exception.InvalidCredentialsException;
import kr.starly.backend.common.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AccountService accountService;
    private final TokenService tokenService;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Mono<RegisterResponse> register(RegisterRequest request) {
        return accountService.create(new CreateAccountRequest(request.email(), request.password()))
                .map(response -> new RegisterResponse(response.id(), response.email(), response.createdAt()));
    }

    @Override
    public Mono<LoginResult> login(LoginRequest request) {
        return accountService.findAccountByEmail(request.email())
                .flatMap(account -> {
                    if (!passwordEncoder.matches(request.password(), account.getPassword())) {
                        return Mono.error(new InvalidCredentialsException());
                    }

                    String accessToken = jwtProvider.generateAccessToken(account.getId());
                    String refreshToken = jwtProvider.generateRefreshToken(account.getId());

                    return tokenService.saveRefreshToken(account.getId(), refreshToken)
                            .thenReturn(new LoginResult(
                                    refreshToken,
                                    new LoginResponse(
                                            accessToken,
                                            new LoginResponse.AccountInfo(account.getId(), account.getEmail(), account.getCreatedAt())
                                    )
                            ));
                });
    }

    @Override
    public Mono<RefreshResponse> refresh(String refreshToken) {
        String accountId = jwtProvider.extractAccountId(refreshToken);
        return tokenService.getRefreshToken(accountId)
                .filter(saved -> saved.equals(refreshToken))
                .map(saved -> new RefreshResponse(jwtProvider.generateAccessToken(accountId)))
                .switchIfEmpty(Mono.error(new InvalidCredentialsException()));
    }

    @Override
    public Mono<Void> logout(String refreshToken) {
        String accountId = jwtProvider.extractAccountId(refreshToken);
        return tokenService.deleteRefreshToken(accountId);
    }
}