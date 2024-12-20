package org.nullnull.demo.repository;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import org.junit.jupiter.api.Test;
import org.nullnull.demo.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

/**
 * 测试异步的MySQL
 *
 * @author nullnull
 * @since 2024/12/19
 */
@SpringBootTest
public class TestRxBookRepository {


    @Test
    void contextLoads() {
    }

    @Test
    public void testFindAll(@Autowired RxBookRepository repository) throws InterruptedException {
        final Flowable<Book> allBooks = repository.findAll();

        allBooks.subscribe(item -> System.out.println("next:" + item),
                ex -> {
                    System.out.println("error:" + ex);
                },
                () -> {
                    System.out.println("finish");
                }
        );
        Thread.sleep(2000);
    }

    @Test
    public void testFindById(@Autowired RxBookRepository repository) throws InterruptedException {

        repository.findById("11111111-1967-2343-7777-000000000000")
                .subscribe(item -> System.out.println("next:" + item),
                        ex -> {
                            System.out.println("error:" + ex);
                        },
                        () -> {
                            System.out.println("finish");
                        }
                );
        Thread.sleep(2000);
    }


    @Test
    public void testFindByTitle(@Autowired RxBookRepository repository) throws InterruptedException {
        Set<String> names = new HashSet<>();
        names.add("The Case for Mars");

        Maybe<Book> byTitle = repository.findByTitle(Flowable.fromIterable(names));

        byTitle.subscribe(item -> System.out.println("next:" + item),
                ex -> {
                    System.out.println("error:" + ex);
                },
                () -> {
                    System.out.println("finish");
                }
        );
        Thread.sleep(2000);
    }

    @Test
    public void testFindByYearBetween(@Autowired RxBookRepository repository) throws InterruptedException {
        Flowable<Book> byYearBetween = repository.findByYearBetween(Single.just(1990), Single.just(2020));

        byYearBetween.subscribe(item -> System.out.println("next:" + item),
                ex -> {
                    System.out.println("error:" + ex);
                },
                () -> {
                    System.out.println("finish");
                }
        );
        Thread.sleep(2000);
    }

    @Test
    public void testSave(@Autowired RxBookRepository repository) throws InterruptedException {
        repository.save(
                new Book() {
                    @Override
                    public String id() {
                        return "test-Book-1";
                    }

                    @Override
                    public String title() {
                        return "title-1";
                    }

                    @Override
                    public Integer publishing_year() {
                        return 2020;
                    }
                }
        ).subscribe();

        Thread.sleep(2000);
    }


    @Test
    public void testSave2(@Autowired RxBookRepository repository) throws InterruptedException {
        repository.save(Flowable.just(
                new Book() {
                    @Override
                    public String id() {
                        return "test-Book-2";
                    }

                    @Override
                    public String title() {
                        return "title-2";
                    }

                    @Override
                    public Integer publishing_year() {
                        return 2021;
                    }
                })
        ).blockingFirst();

        Thread.sleep(2000);
    }


}
