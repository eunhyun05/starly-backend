package kr.starly.backend.auth.controller;

import jakarta.validation.Valid;
import kr.starly.backend.auth.dto.RegisterRequest;
import kr.starly.backend.auth.dto.RegisterResponse;
import kr.starly.backend.auth.service.AuthService;
import kr.starly.backend.common.response.ApiResponse;
import kr.starly.backend.common.response.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("/register")
    public Mono<ResponseEntity<ApiResponse<RegisterResponse>>> register(@RequestBody @Valid RegisterRequest request) {
        return service.register(request)
                .map(account -> ResponseEntity.status(SuccessCode.CREATED.getStatus())
                        .body(ApiResponse.success(
                                SuccessCode.CREATED,
                                new RegisterResponse(account.id(), account.email(), account.createdAt())
                        )));
    }
}