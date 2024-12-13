package com.nullnull.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author nullnull
 * @since 2024/12/13
 */
@With
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("wallet")
public class Wallet {

    @Id
    private ObjectId id;

    private String owner;

    /**
     * 余额
     */
    private int balance;

    /**
     * 入账笔数
     */
    private int depositOperations;
    /**
     * 转账笔数
     */
    private int withdrawOperations;

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
}
