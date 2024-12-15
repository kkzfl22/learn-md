package com.nullnull.demo.service;

import com.nullnull.demo.entity.Computer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 服务
 *
 * @author nullnull
 * @since 2024/12/15
 */
public interface ComputerService {

  Mono<Computer> saveComputer(Computer computer);

  Flux<Computer> findComputerAll();
}
