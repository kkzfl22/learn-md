package org.nullnull.demo.entity;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author nullnull
 * @since 2024/12/13
 */
@Getter
@Setter
@ToString
public class Wallet {

  private String id;

  private String owner;

  /** 余额 */
  private int balance;

  /** 入账笔数 */
  private int depositOperations;

  /** 转账笔数 */
  private int withdrawOperations;

  /** 币种 */
  private String currency;

  public Wallet() {}

  public Wallet(
      String id,
      String owner,
      int balance,
      int depositOperations,
      int withdrawOperations,
      String currency) {
    this.id = id;
    this.owner = owner;
    this.balance = balance;
    this.depositOperations = depositOperations;
    this.withdrawOperations = withdrawOperations;
    this.currency = currency;
  }

  public static Wallet wallet(String owner, int balance) {
    return new Wallet(UUID.randomUUID().toString(), owner, balance, 0, 0, "RMB");
  }

  public static Wallet wallet(String owner, int balance, String currency) {
    return new Wallet(UUID.randomUUID().toString(), owner, balance, 0, 0, currency);
  }

  public static Wallet wallet(String id, String owner, int balance, String currency) {
    return new Wallet(id, owner, balance, 0, 0, currency);
  }
}
