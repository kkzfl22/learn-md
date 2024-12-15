package com.nullnull.demo.reposiroty;

import com.nullnull.demo.entity.Computer;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

/**
 * 事务的操作
 *
 * @author nullnull
 * @since 2024/12/14
 */
@Repository
public class TransferComputerRepository {

  private final DatabaseClient client;

  public TransferComputerRepository(DatabaseClient client) {
    this.client = client;
  }

  public Mono<Void> insertRows() {
    String uuid = UUID.randomUUID().toString();

    int i = 0;

    StringBuilder value = new StringBuilder();
    value.append("values(");
    value.append("'").append(uuid).append("'").append(",");
    value.append("'联想'").append(",");
    value.append("8500").append(",");
    value.append("2023");
    value.append(")");

    StringBuilder value2 = new StringBuilder();
    value2.append("values(");
    value2.append("'").append(uuid).append("'").append(",");
    value2.append("'HP'").append(",");
    value2.append("5600").append(",");
    value2.append("2022");
    value2.append(")");

    return client
        .sql("insert int computer (id,name,money,make_year) " + value.toString())
        .fetch()
        .rowsUpdated()
        .doOnNext(
            item -> {
              int a = 10 / i;
            })
        .then(
            client.sql("insert int computer (id,name,money,make_year) " + value2.toString()).then())
        .then();
  }
}
