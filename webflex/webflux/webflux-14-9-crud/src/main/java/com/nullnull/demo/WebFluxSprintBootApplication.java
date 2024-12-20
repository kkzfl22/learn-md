package com.nullnull.demo;

import com.nullnull.demo.config.RxPersistenceConfiguration;
import com.nullnull.demo.entity.Book;
import com.nullnull.demo.repository.RxBookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * @author nullnull
 * @since 2024/12/10
 */
@Slf4j
@EnableJpaRepositories
@RequiredArgsConstructor
@SpringBootApplication
@Import({
        RxPersistenceConfiguration.class
})
public class WebFluxSprintBootApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(WebFluxSprintBootApplication.class, args);
    }

    private final RxBookRepository bookRepository;

    @Override
    public void run(String... args) throws Exception {
        Flux<Book> books = Flux.just(
                new Book("The Martian", 2011),
                new Book("Blue Mars", 1996),
                new Book("The War of the worlds", 1898),
                new Book("Artemis", 2016),
                new Book("The Expanse: Leviathan Wakes", 2011),
                new Book("The Expanse: Caliban's War", 2012)
        );

        bookRepository.saveAll(books)
                .count()
                .doOnNext(amount -> log.info("{} books saved in DB", amount))
                .block();

        Flux<Book> allBooks = bookRepository.findAll();
        reportResults("all books in Db", allBooks);

        Flux<Book> andWeirBooks = bookRepository.findByIdBetween(Mono.just(17), Mono.just(22));
        reportResults("Books with ids (17..22)", andWeirBooks);


        Flux<Book> booksWithFewAuthors = bookRepository.findShortesTitle();
        reportResults("Books with the shortest title:", booksWithFewAuthors);

        Mono.delay(Duration.ofSeconds(5))
                .subscribe(e -> log.info("Application finished successfully!"));

    }

    private void reportResults(String message, Flux<Book> books) {
        books.map(Book::toString)
                .reduce(new StringBuilder(),
                        (sb, b) -> sb.append(" - ")
                                .append(b)
                                .append("\n"))
                .doOnNext(sb -> log.info(message + "\n {}", sb))
                .subscribe();
    }
}
