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

    StringBuilder value = new StringBuilder();
    value.append("values(");
    value.append("'").append(uuid).append("'").append(",");
    value.append("'java编程思想'").append(",");
    value.append("156").append(",");
    value.append("2007");
    value.append(")");

    return client.sql("insert int computer (id,name,money,make_year) " + value)
            .fetch()
            .rowsUpdated()
            ;
  }
}
