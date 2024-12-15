package com.nullnull.demo.service;

import com.nullnull.demo.entity.Computer;
import com.nullnull.demo.reposiroty.ComputerRepository;
import com.nullnull.demo.reposiroty.TransferComputerRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

/**
 * 测试服务
 *
 * @author nullnull
 * @since 2024/12/15
 */
@SpringBootTest
public class TestComputerServiceImpl {

  @Test
  public void test(@Autowired ComputerRepository repository) throws InterruptedException {
    Assert.assertNotNull(repository);
    CountDownLatch latch = new CountDownLatch(1);

    Mono<Computer> thinkStation = repository.save(new Computer("thinkstation", 18000, 1994));
    thinkStation.subscribe(
        item -> System.out.println("onnext:" + item),
        ex -> {
          System.err.println("error:" + ex);
          latch.countDown();
        },
        () -> {
          System.out.println("finish");
          latch.countDown();
        });

    latch.await();
  }

  @Test
  public void testFindLastComputer(@Autowired ComputerRepository repository)
      throws InterruptedException {
    CountDownLatch latch = new CountDownLatch(1);

    Flux<Computer> newComputer = repository.findNewComputer();
    newComputer.subscribe(
        item -> {
          System.out.println("newComputer" + item);
          latch.countDown();
        });
    latch.await();
  }

  @Test
  public void testInsertComputer(@Autowired ComputerRepository repository)
      throws InterruptedException {
    CountDownLatch latch = new CountDownLatch(1);

    Mono<Computer> thinkStation = repository.save(new Computer("联想", 5000, 2014));
    thinkStation.subscribe(
        item -> System.out.println("onnext:" + item),
        ex -> {
          System.err.println("error:" + ex);
          latch.countDown();
        },
        () -> {
          System.out.println("finish");
          latch.countDown();
        });

    latch.await();
  }

  @Test
  public void testFindAll(@Autowired ComputerRepository repository) throws InterruptedException {
    CountDownLatch latch = new CountDownLatch(1);

    Flux<Computer> newComputer = repository.findAll();
    newComputer.subscribe(
        item -> {
          System.out.println("findAll" + item);
          latch.countDown();
        });
    latch.await();
  }

  @Test
  public void testServerSave(@Autowired ComputerService computerService)
      throws InterruptedException {
    computerService.saveComputer(new Computer("戴尔", 15000, 2013)).block(Duration.ofSeconds(5));
  }

  @Test
  public void testServiceFindAll(@Autowired ComputerService computerService)
      throws InterruptedException {
    computerService.findComputerAll().subscribe(System.out::println);
    Thread.sleep(10000);
  }

  @Test
  public void testTransferInsert(@Autowired TransferComputerRepository computerRepository)
      throws InterruptedException {
    computerRepository.insertRows().subscribe(System.out::println);
    Thread.sleep(5000);
  }
}
