package com.nullnull.demo.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author nullnull
 * @since 2024/12/13
 */
@Document("wallet")
public class Wallet {

  @Id private ObjectId id;

  private String owner;

  /** 余额 */
  private int balance;

  /** 入账笔数 */
  private int depositOperations;

  /** 转账笔数 */
  private int withdrawOperations;

  public Wallet(
      ObjectId id, String owner, int balance, int depositOperations, int withdrawOperations) {
    this.id = id;
    this.owner = owner;
    this.balance = balance;
    this.depositOperations = depositOperations;
    this.withdrawOperations = withdrawOperations;
  }

  /**
   * 余额检查
   *
   * @param amount
   * @return
   */
  public boolean hasEnoughFunds(int amount) {
    return balance >= amount;
  }

  /**
   * 扣除余额
   *
   * @param amount
   */
  public void withDraw(int amount) {
    if (!hasEnoughFunds(amount)) {
      throw new RuntimeException("Not enough founds!");
    }

    this.balance = this.balance - amount;
    this.withdrawOperations += 1;
  }

  /**
   * 增加余额
   *
   * @param amount
   */
  public void deposit(int amount) {
    this.balance = this.balance + amount;
    this.depositOperations += 1;
  }

  public static Wallet wallet(String owner, int balance) {
    return new Wallet(new ObjectId(), owner, balance, 0, 0);
  }

  public ObjectId getId() {
    return id;
  }

  public void setId(ObjectId id) {
    this.id = id;
  }

  public String getOwner() {
    return owner;
  }

  public void setOwner(String owner) {
    this.owner = owner;
  }

  public int getBalance() {
    return balance;
  }

  public void setBalance(int balance) {
    this.balance = balance;
  }

  public int getDepositOperations() {
    return depositOperations;
  }

  public void setDepositOperations(int depositOperations) {
    this.depositOperations = depositOperations;
  }

  public int getWithdrawOperations() {
    return withdrawOperations;
  }

  public void setWithdrawOperations(int withdrawOperations) {
    this.withdrawOperations = withdrawOperations;
  }
}
