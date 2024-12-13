package com.nullnull.demo.reporitory;

import com.nullnull.demo.entity.Wallet;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * 钱包操作接口
 *
 * @author nullnull
 * @since 2024/12/13
 */
@Repository
public interface WalletRepository extends ReactiveMongoRepository<Wallet, ObjectId> {

  /**
   * 查询用户的钱包
   *
   * @param owner
   * @return
   */
  Mono<Wallet> findByOwner(Mono<String> owner);
}
