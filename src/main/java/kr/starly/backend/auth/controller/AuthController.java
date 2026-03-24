package kr.starly.backend.auth.controller;

import jakarta.validation.Valid;
import kr.starly.backend.auth.dto.LoginRequest;
import kr.starly.backend.auth.dto.LoginResponse;
import kr.starly.backend.auth.dto.RegisterRequest;
import kr.starly.backend.auth.dto.RegisterResponse;
import kr.starly.backend.auth.service.AuthService;
import kr.starly.backend.common.response.ApiResponse;
import kr.starly.backend.common.response.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpCookie;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private static final String REFRESH_TOKEN_COOKIE = "refreshToken";

    private final AuthService service;

    @Value("${jwt.refresh-token-cookie-max-age}")
    private long refreshTokenCookieMaxAge;

    @PostMapping("/register")
    public Mono<ResponseEntity<ApiResponse<RegisterResponse>>> register(@RequestBody @Valid RegisterRequest request) {
        return service.register(request)
                .map(account -> ResponseEntity.status(SuccessCode.CREATED.getStatus())
                        .body(ApiResponse.success(
                                SuccessCode.CREATED,
                                new RegisterResponse(account.id(), account.email(), account.createdAt())
                        )));
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<ApiResponse<LoginResponse>>> login(@RequestBody @Valid LoginRequest request, ServerWebExchange exchange) {
        return service.login(request)
                .map(result -> {
                    exchange.getResponse().addCookie(
                            ResponseCookie.from(REFRESH_TOKEN_COOKIE, result.refreshToken())
                                    .httpOnly(true)
                                    .secure(true)
                                    .path("/")
                                    .maxAge(refreshTokenCookieMaxAge)
                                    .sameSite("Strict")
                                    .build()
                    );
                    return ResponseEntity.ok(ApiResponse.success(SuccessCode.OK, result.response()));
                });
    }

    @PostMapping("/refresh")
    public Mono<ResponseEntity<ApiResponse<String>>> refresh(ServerWebExchange exchange) {
        return Mono.justOrEmpty(exchange.getRequest().getCookies().getFirst(REFRESH_TOKEN_COOKIE))
                .map(HttpCookie::getValue)
                .flatMap(service::refresh)
                .map(response -> ResponseEntity.ok(ApiResponse.success(SuccessCode.OK, response.accessToken())));
    }

    @PostMapping("/logout")
    public Mono<ResponseEntity<ApiResponse<Void>>> logout(ServerWebExchange exchange) {
        return Mono.justOrEmpty(exchange.getRequest().getCookies().getFirst(REFRESH_TOKEN_COOKIE))
                .map(HttpCookie::getValue)
                .flatMap(service::logout)
                .then(Mono.fromCallable(() -> {
                    exchange.getResponse().addCookie(
                            ResponseCookie.from(REFRESH_TOKEN_COOKIE, "")
                                    .httpOnly(true)
                                    .secure(true)
                                    .path("/")
                                    .maxAge(0)
                                    .build()
                    );
                    return ResponseEntity.ok(ApiResponse.success(SuccessCode.OK));
                }));
    }
}