package kr.starly.backend.account.service;

import kr.starly.backend.account.dto.CreateAccountRequest;
import kr.starly.backend.account.dto.CreateAccountResponse;
import kr.starly.backend.account.model.Account;
import reactor.core.publisher.Mono;

public interface AccountService {

    Mono<CreateAccountResponse> create(CreateAccountRequest request);

    Mono<CreateAccountResponse> findByEmail(String email);

    Mono<Account> findAccountByEmail(String email);
}