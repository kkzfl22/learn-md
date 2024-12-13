package com.nullnull.demo.service;

import com.nullnull.demo.entity.Wallet;
import com.nullnull.demo.reporitory.WalletRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.ReactiveMongoContext;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;

/**
 * @author nullnull
 * @since 2024/12/13
 */
@Slf4j
@Component
public class TransactionalWalletService extends BaseWalletService {

  private final ReactiveMongoTemplate mongoTemplate;

  public TransactionalWalletService(
      WalletRepository walletRepository, ReactiveMongoTemplate mongoTemplate) {
    super(walletRepository);
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public Mono<TxResult> transferMoney(
      Mono<String> fromOwner, Mono<String> toOwner, Mono<Integer> amount) {
    return Mono.zip(fromOwner, toOwner, amount)
        .flatMap(
            data -> {
              Instant start = Instant.now();
              return doTransferMoney(data.getT1(), data.getT2(), data.getT3())
                  .onErrorReturn(TxResult.TX_CONFLICT)
                  .doOnSuccess(
                      result ->
                          log.info(
                              "transaction result: {} took: {}",
                              result,
                              Duration.between(start, Instant.now())));
            });
  }

  private Mono<TxResult> doTransferMoney(String from, String to, Integer amount) {
    return mongoTemplate
        .inTransaction()
        .execute(
            session ->
                session
                    .findOne(queryForOwner(from), Wallet.class)
                    .flatMap(
                        fromWallet ->
                            session
                                .findOne(queryForOwner(to), Wallet.class)
                                .flatMap(
                                    toWallet -> {
                                      if (fromWallet.hasEnoughFunds(amount)) {
                                        fromWallet.withDraw(amount);
                                        toWallet.deposit(amount);
                                        return session
                                            .save(fromWallet)
                                            .then(session.save(toWallet))
                                            .then(ReactiveMongoContext.getSession())
                                            .doOnNext(tx -> log.info("current session: {}", tx))
                                            .then(Mono.just(TxResult.SUCCESS));
                                      } else {
                                        return Mono.just(TxResult.NOT_ENOUGH_FOUNDS);
                                      }
                                    })))
        .onErrorResume(e -> Mono.error(new RuntimeException("Conflict")))
        .last();
  }

  private Query queryForOwner(String owner) {
    return Query.query(new Criteria("owner").is(owner));
  }
}
