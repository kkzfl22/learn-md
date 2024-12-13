package com.nullnull.demo.service;

import com.nullnull.demo.entity.Wallet;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 钱包服务操作接口
 *
 * @author nullnull
 * @since 2024/12/13
 */
public interface WalletService {

  /**
   * 生成指定的个数的账户信息
   *
   * @param number 账户个数
   * @param defaultBalance 默认的余额
   * @return 返回账户的名称信息
   */
  Flux<String> generateClients(Integer number, Integer defaultBalance);

  /**
   * 转账操作
   *
   * @return
   */
  Mono<TxResult> transferMoney(Mono<String> fromOwner, Mono<String> toOwner, Mono<Integer> amount);

  /**
   * @return
   */
  Mono<Statistics> reportAllWallets();

  /**
   * 移除所有数据
   *
   * @return
   */
  Mono<Void> removeAllClients();

  enum TxResult {
    SUCCESS,
    NOT_ENOUGH_FOUNDS,
    TX_CONFLICT
  }

  class Statistics {

    /** 总的转账笔数 */
    private long totalAccounts;

    /** 转账的总金额 */
    private long totalBalance;

    /** 总入帐 */
    private long totalDeposits;

    /** 总出账 */
    private long totalWithdraws;

    public Statistics() {}

    public Statistics(
        long totalAccounts, long totalBalance, long totalDeposits, long totalWithdraws) {
      this.totalAccounts = totalAccounts;
      this.totalBalance = totalBalance;
      this.totalDeposits = totalDeposits;
      this.totalWithdraws = totalWithdraws;
    }

    public Statistics withWallet(Wallet w) {
      return new Statistics(
          this.getTotalAccounts() + 1,
          this.getTotalBalance() + w.getBalance(),
          this.getTotalDeposits() + w.getDepositOperations(),
          this.totalWithdraws + w.getWithdrawOperations());
    }

    public long getTotalAccounts() {
      return totalAccounts;
    }

    public long getTotalBalance() {
      return totalBalance;
    }

    public long getTotalDeposits() {
      return totalDeposits;
    }

    public long getTotalWithdraws() {
      return totalWithdraws;
    }
  }
}
