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
        return Mono
                //订阅上游3个元素，等所有都到达后，送给下游
                .zip(fromOwner, toOwner, amount)
                //订阅上游流元素，进行数据转换
                .flatMap(
                        data -> {
                            //记录下开始时间
                            Instant start = Instant.now();
                            //执行转账操作
                            return doTransferMoney(data.getT1(), data.getT2(), data.getT3())
                                    //错误时返回,TX_CONFLICT
                                    .onErrorReturn(TxResult.TX_CONFLICT)
                                    //成功时记录下日志
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
                //开启事务
                .inTransaction()
                //执行操作,传入回调函数,回调函数的参数的会话对象
                .execute(
                        session ->
                                session
                                        //查找转帐的账号信息
                                        .findOne(queryForOwner(from), Wallet.class)
                                        //订阅并执行钱包操作
                                        .flatMap(
                                                fromWallet ->
                                                        session
                                                                //查找接收账号信息
                                                                .findOne(queryForOwner(to), Wallet.class)
                                                                //订阅并执行转换逻辑
                                                                .flatMap(
                                                                        toWallet -> {
                                                                            //检查钱包余额是否足够,如果足够执行转账
                                                                            if (fromWallet.hasEnoughFunds(amount)) {
                                                                                //扣除原始账户的余额
                                                                                fromWallet.withDraw(amount);
                                                                                //增加目标账号的余额
                                                                                toWallet.deposit(amount);

                                                                                return session
                                                                                        //保存原始钱包信息
                                                                                        .save(fromWallet)
                                                                                        //保存目标钱包信息
                                                                                        .then(session.save(toWallet))
                                                                                        //获取session对象，则返回成流
                                                                                        .then(ReactiveMongoContext.getSession())
                                                                                        //执行副作用，打印日志
                                                                                        .doOnNext(tx -> log.info(
                                                                                                "current session: {}"
                                                                                                , tx))
                                                                                        //返回转账成功
                                                                                        .then(Mono.just(TxResult.SUCCESS));
                                                                            }
                                                                            //不足则返回余额不足
                                                                            else {
                                                                                return Mono.just(TxResult.NOT_ENOUGH_FOUNDS);
                                                                            }
                                                                        })))
                //如果转账发生错误，则返回包装异常返回
                .onErrorResume(e -> {
                    //e.printStackTrace();
                    //log.error("error,", e);
                    return Mono.error(new RuntimeException("Conflict"));
                })
                .last();
    }

    /**
     * 查询方法
     *
     * @param owner
     * @return
     */
    private Query queryForOwner(String owner) {
        return Query.query(new Criteria("owner").is(owner));
    }
}
