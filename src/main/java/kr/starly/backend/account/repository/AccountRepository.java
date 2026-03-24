package kr.starly.backend.account.repository;

import kr.starly.backend.account.model.Account;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface AccountRepository extends ReactiveMongoRepository<Account, String> {

    Mono<Boolean> existsByEmail(String email);

    Mono<Account> findByEmail(String email);
}