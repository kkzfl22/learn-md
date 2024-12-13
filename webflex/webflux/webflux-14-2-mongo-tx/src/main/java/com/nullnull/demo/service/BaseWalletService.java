package com.nullnull.demo.service;

import com.nullnull.demo.entity.Wallet;
import com.nullnull.demo.reporitory.WalletRepository;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;

/**
 * 基本钱包的实现
 *
 * @author nullnull
 * @since 2024/12/13
 */
@Slf4j
public abstract class BaseWalletService implements WalletService {

  protected final WalletRepository walletRepository;

  public BaseWalletService(WalletRepository walletRepository) {
    this.walletRepository = walletRepository;
  }

  @Override
  public Flux<String> generateClients(Integer number, Integer defaultBalance) {

    return walletRepository
        .saveAll(
            // 生成指定数量的值
            Flux.range(1, number)
                // 将id转换为字符串名称
                .map(id -> String.format("client-%05d", id))
                // 转换为钱包对象
                .map(owner -> Wallet.wallet(owner, defaultBalance)))
        // 返回包的用户
        .map(Wallet::getOwner);
  }



  @Override
  public Mono<Statistics> reportAllWallets() {
    return walletRepository
        .findAll()
        .sort(Comparator.comparing(Wallet::getOwner))
        .doOnNext(
            item ->
                log.info(
                    String.format(
                        "%10s: %7d$ (d:%5s | w: %5s)",
                        item.getOwner(),
                        item.getBalance(),
                        item.getDepositOperations(),
                        item.getWithdrawOperations())))
        .reduce(new Statistics(), Statistics::withWallet);
  }

  @Override
  public Mono<Void> removeAllClients() {
    return walletRepository.deleteAll();
  }
}
