package com.nullnull.demo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Arrays;
import java.util.List;

/**
 * @author nullnull
 * @since 2024/12/17
 */
@Document(collation = "book")
@Data
@NoArgsConstructor
public class Book {
    @Id
    private Object id;


    @Indexed
    private String title;

    @Field("pubYear")
    private int publishingYear;

    @Indexed
    private List<String> authors;


    public Book(String title, int publishingYear, String... authors) {
        this.title = title;
        this.publishingYear = publishingYear;
        this.authors = Arrays.asList(authors);
    }
}
