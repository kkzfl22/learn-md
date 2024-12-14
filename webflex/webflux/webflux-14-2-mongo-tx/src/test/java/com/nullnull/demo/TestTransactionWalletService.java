package com.nullnull.demo;

import com.nullnull.demo.reporitory.WalletRepository;
import com.nullnull.demo.service.TransactionalWalletService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.util.function.Tuple2;

/**
 * @author nullnull
 * @since 2024/12/14
 */
@Slf4j
@DataMongoTest
public class TestTransactionWalletService extends TestBaseWalletService {


    @Test
    @DisplayName("Reactive transaction for data transfer")
    public void testReactiveTransactionApproach(@Autowired WalletRepository walletRepository,
                                                @Autowired ReactiveMongoTemplate mongoTemplate) {
        //1, 创建事务对象
        TransactionalWalletService walletService = new TransactionalWalletService(walletRepository, mongoTemplate);
        Tuple2<Long, Long> expectedActual = simulateOperations(walletService);

        //事务执行完成后，两个账户余额应该相同
        Assert.assertEquals(expectedActual.getT1(), expectedActual.getT2());

    }

}
