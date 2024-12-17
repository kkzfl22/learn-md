package com.nullnull.demo;


import com.nullnull.demo.entity.Book;
import com.nullnull.demo.repository.BookSpringDataMongoRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.util.concurrent.CountDownLatch;


/**
 * @author nullnull
 * @since 2024/12/17
 */
@SpringBootTest
public class WebFluxApplicationTests {

    private static final Logger log = LoggerFactory.getLogger(WebFluxApplicationTests.class);

    @Test
    public void contextLoader(@Autowired BookSpringDataMongoRepository bookRepo) {
        bookRepo.saveAll(Flux.just(
                new Book("java编程思想", 2011, "不记得了"),
                new Book("java并发编程", 2012, "也不记得"),
                new Book("代码整洁之道", 2012, "bob"),
                new Book("代码整洁之道：程序员的职业素养", 2011, "bob"),
                new Book("UML：Java程序员指南", 2013, "bob")
        )).blockLast();

        log.info("books save in db");

        //打印保存的停下
        bookRepo.findAll()
                .doOnNext(book -> System.out.println(book))
                .blockLast();

        System.out.println("----------------------------------");
        System.out.println();

        bookRepo.findByAuthorsOrderByPublishingYearDesc("bob")
                .doOnNext(book -> System.out.println(book))
                .blockLast();
        System.out.println("----------------------------------");
        System.out.println();

        bookRepo.booksWithFewAuthors()
                .doOnNext(System.out::println)
                .blockLast();
        System.out.println("application finish successfully!");
    }

    public String toString(Iterable<Book> books) {
        StringBuilder sb = new StringBuilder();

        books.iterator().forEachRemaining(b ->
                sb.append(" - ").append(b.toString()).append("\n")
        );

        return sb.toString();
    }

    @Test
    public void testInsert(@Autowired ReactiveMongoRepository template) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Book book = new Book("敏捷软件开发：原则、模式和实践", 2011, "bob");
        template.insert(book)
                .doOnNext(book1 -> System.out.println(book1))
                .flatMap(book1 -> template.findById(book.getId()))
                .doOnNext(book1 -> System.out.println("找到:" + book1))
                .doOnTerminate(() -> latch.countDown())
                .subscribe();

        latch.await();
    }

    @Test
    public void testDelete(@Autowired ReactiveMongoRepository repository) throws InterruptedException {
        Book back = (Book) repository.findAll().blockLast();

        repository.findById(back.getId())
                .doOnNext(book -> System.out.println("删除之前:" + book))
                .then(repository.deleteById(back.getId()))
                .map(book -> repository.findById(back.getId()))
                .doOnNext(book -> System.out.println("删除之后:" + book))
                .doOnTerminate(() -> System.out.println("运行结束"))
                .block();
    }
}
