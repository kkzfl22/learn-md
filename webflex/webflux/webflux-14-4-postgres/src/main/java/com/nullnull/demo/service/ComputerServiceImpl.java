package com.nullnull.demo.service;

import com.nullnull.demo.entity.Computer;
import com.nullnull.demo.reposiroty.ComputerRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author nullnull
 * @since 2024/12/15
 */
@Service
public class ComputerServiceImpl  implements ComputerService {



    private final ComputerRepository repository;

    public ComputerServiceImpl(ComputerRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<Computer> saveComputer(Computer computer) {


        Mono<Computer> save = repository.save(computer);

        int i = 1 / 0;

        return save;
    }

    @Override
    public Flux<Computer> findComputerAll() {
        return repository.findAll();
    }
}
