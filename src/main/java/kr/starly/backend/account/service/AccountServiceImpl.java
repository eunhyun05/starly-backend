package kr.starly.backend.account.service;

import kr.starly.backend.account.dto.CreateAccountRequest;
import kr.starly.backend.account.dto.CreateAccountResponse;
import kr.starly.backend.account.exception.AccountNotFoundException;
import kr.starly.backend.account.exception.EmailAlreadyExistsException;
import kr.starly.backend.account.model.Account;
import kr.starly.backend.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Mono<CreateAccountResponse> create(CreateAccountRequest request) {
        return repository.existsByEmail(request.email())
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new EmailAlreadyExistsException());
                    }

                    return repository.save(
                            Account.builder()
                                    .email(request.email())
                                    .password(passwordEncoder.encode(request.password()))
                                    .build()
                    ).map(account -> new CreateAccountResponse(account.getId(), account.getEmail(), account.getCreatedAt()));
                });
    }

    @Override
    public Mono<CreateAccountResponse> findByEmail(String email) {
        return repository.findByEmail(email)
                .map(account -> new CreateAccountResponse(account.getId(), account.getEmail(), account.getCreatedAt()));
    }

    @Override
    public Mono<Account> findAccountByEmail(String email) {
        return repository.findByEmail(email)
                .switchIfEmpty(Mono.error(new AccountNotFoundException()));
    }
}