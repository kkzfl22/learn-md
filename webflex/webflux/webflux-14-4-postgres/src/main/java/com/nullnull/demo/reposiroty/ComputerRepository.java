package com.nullnull.demo.reposiroty;

import com.nullnull.demo.entity.Computer;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

/**
 * 电脑的存储类
 *
 * @author nullnull
 * @since 2024/12/14
 */
public interface ComputerRepository extends ReactiveCrudRepository<Computer, Integer> {

  /**
   * 查找最新制造的电脑
   *
   * @return
   */
  @Query("select * from computer where make_year = (select max(make_year) from computer)")
  Flux<Computer> findNewComputer();
}
