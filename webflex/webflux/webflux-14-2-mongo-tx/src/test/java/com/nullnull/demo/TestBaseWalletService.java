package com.nullnull.demo;

import com.nullnull.demo.service.WalletService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Random;

/**
 * 测试基础
 *
 * @author nullnull
 * @since 2024/12/14
 */
@SuppressWarnings("Duplicates")
@Slf4j
@DataMongoTest
public class TestBaseWalletService {


    /**
     * 转账测试工具类
     *
     * @param walletService
     * @return
     */
    protected Tuple2<Long, Long> simulateOperations(WalletService walletService) {
        //账号数量
        int accounts = 500;
        //每个账号的金额
        int defaultBalance = 1000;
        //操作次数
        int iterations = 1000;
        //并行操作数量
        int parallelism = 200;

        //清除所有数据，在清除完成前，进行等待状态
        walletService.removeAllClients().block();

        //创建指定个数的账号，并返回账号的名称
        List<String> clients = walletService
                //创建账号信息
                .generateClients(accounts, defaultBalance)
                //执行副作用
                .doOnNext(name -> log.info("create wallet for : {}", name))
                //收到一个List集合
                .collectList()
                //阻塞直到最后完成
                .block();

        //并行操作的调度器
        Scheduler mongoScheduler = Schedulers.newParallel("MongoOperations", parallelism);

        //记录当前时间
        Instant startTime = Instant.now();

        OperationalSimulation simulation = OperationalSimulation.builder()
                //设置转账的钱包的服务
                .walletService(walletService)
                //设置全部用户名
                .clients(clients)
                //设置默认的余额
                .defaultBalance(defaultBalance)
                //操作转账次数
                .iterations(iterations)
                //并行的调度线程
                .simulationScheduler(mongoScheduler)
                .build();

        OperationStats operations = simulation
                //执行事务操作
                .runSimulation()
                //等待所有的事务完成
                .block();

        log.info("-------result--------------------------------------------");
        //收集所有账户的报告
        WalletService.Statistics statistsics = walletService.reportAllWallets().block();

        log.info("Expected/actual total balance : {} $/ {}$ | Took : {}",
                accounts * defaultBalance,
                statistsics.getTotalAccounts(),
                Duration.between(startTime, Instant.now())
        );
        log.info("{}", statistsics);
        log.info("{}", operations);


        log.info("Clean up database");

        //移除所有账户信息
        walletService.removeAllClients().block();

        return Tuples.of((long) accounts * defaultBalance, statistsics.getTotalBalance());
    }

    @Builder
    @RequiredArgsConstructor
    @ToString
    public static class OperationalSimulation {
        private final WalletService walletService;
        private final List<String> clients;
        private final int defaultBalance;
        private final int iterations;
        private final Scheduler simulationScheduler;
        private final Random random = new Random();

        public Mono<OperationStats> runSimulation() {
            return Flux
                    //指定的转账操作次数
                    .range(0, iterations)
                    //转换,以异步的方式，允许交错操作
                    .flatMap(i ->
                            Mono
                                    //操作延迟10毫秒
                                    .delay(Duration.ofMillis(random.nextInt(10)))
                                    //订阅原始响应流
                                    .publishOn(simulationScheduler)
                                    //数据被订阅，并且不允许交错
                                    .flatMap(item -> {
                                        //转账账户
                                        String fromOwner = randomOwner();
                                        //接收账户
                                        String toOwner = randomOwnerExcept(fromOwner);
                                        //转账的金额
                                        int amount = randomTransferAmount();

                                        //执行转账操作
                                        return walletService.transferMoney(Mono.just(fromOwner),
                                                Mono.just(toOwner),
                                                Mono.just(amount)
                                        );
                                    })
                    )
                    //将所有转账的数据进行归结操作
                    .reduce(OperationStats.start(), OperationStats::countTxResult);
        }

        /**
         * 转账的金额
         *
         * @return
         */
        private int randomTransferAmount() {
            return random.nextInt(defaultBalance);
        }

        /**
         * 转账的账户
         *
         * @return
         */
        private String randomOwner() {
            int from = random.nextInt(clients.size());
            return clients.get(from);
        }

        /**
         * 接收方账号,要求接收账号不能是自己
         *
         * @param fromOwner
         * @return
         */
        private String randomOwnerExcept(String fromOwner) {
            String toOwner;
            do {
                int to = random.nextInt(clients.size());
                toOwner = clients.get(to);
            } while (fromOwner.equals(toOwner));
            return toOwner;
        }
    }

    @ToString
    @RequiredArgsConstructor
    public static class OperationStats {
        private final int successFul;
        private final int notEnoughFounds;
        private final int conflict;

        /**
         * 针对转账结果进行统计
         *
         * @param result
         * @return
         */
        public OperationStats countTxResult(WalletService.TxResult result) {
            switch (result) {
                //如果成功，则成功的笔数加1
                case SUCCESS:
                    return new OperationStats(successFul + 1, notEnoughFounds, conflict);
                //余额不足，则不足的笔记加1
                case NOT_ENOUGH_FOUNDS:
                    return new OperationStats(successFul, notEnoughFounds + 1, conflict);
                //失败，则失败的笔记加1
                case TX_CONFLICT:
                    return new OperationStats(successFul, notEnoughFounds, conflict + 1);
                default:
                    throw new RuntimeException("unexpected status" + result);
            }
        }


        public static OperationStats start() {
            return new OperationStats(0, 0, 0);
        }

    }

}
