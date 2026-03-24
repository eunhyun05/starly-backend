package kr.starly.backend.account.service;

import kr.starly.backend.account.dto.CreateAccountRequest;
import kr.starly.backend.account.dto.CreateAccountResponse;
import reactor.core.publisher.Mono;

public interface AccountService {

    Mono<CreateAccountResponse> create(CreateAccountRequest request);
}