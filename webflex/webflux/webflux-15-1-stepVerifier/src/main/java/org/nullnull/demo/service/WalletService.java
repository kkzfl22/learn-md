package org.nullnull.demo.service;

import java.util.HashMap;
import java.util.Map;
import org.nullnull.demo.entity.Wallet;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author nullnull
 * @since 2024/12/21
 */
@Service
public class WalletService {

  private static final Map<String, Wallet> DATA_MAP = new HashMap<>();

  public Flux<Wallet> list() {
    Wallet nullItem = Wallet.wallet("nullnull", 1000);
    Wallet admin = Wallet.wallet("admin", 500);
    return Flux.just(nullItem, admin);
  }

  public Mono<String> send(Mono<Wallet> data) {
    return data.map(
        item -> {
          DATA_MAP.put(item.getId(), item);
          return item.getOwner();
        });
  }
}
