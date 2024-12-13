package com.nullnull.demo.service;

import com.nullnull.demo.entity.Statistics;
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
     * 初始化钱包金额
     *
     * @param number
     * @param defaultBalance
     * @return
     */
    Flux<String> generateClients(Integer number, Integer defaultBalance);


    /**
     * 转账操作
     *
     * @return
     */
    Mono<TxResult> transferMoney(
            Mono<String> fromOwner,
            Mono<String> toOwner,
            Mono<Integer> amount);

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

}
