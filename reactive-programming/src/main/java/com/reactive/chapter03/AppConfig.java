package com.reactive.chapter03;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class AppConfig {

    private static final Logger log = LoggerFactory.getLogger(AppConfig.class);

    private final URI baseUri = UriComponentsBuilder.newInstance().scheme("http")
            .host("localhost")
            .port(8080)
            .path("/v1/books")
            .build()
            .toUri();


    @Bean
    public RestTemplateBuilder restTemplate() {
        return new RestTemplateBuilder();
    }

    @Bean
    public CommandLineRunner run() {
        return (String... args) -> {
            log.info("# 요청 시작 시간: {}", LocalTime.now());

            for (int i = 1; i <= 5; i++) {
                Book book = this.getBook(i);
                log.info("{}: book name: {}", LocalTime.now(), book.getName());
            }
        };
    }

    @Bean
    public Map<Long, Book> bookMap() {
        return new HashMap<>();
    }

    private Book getBook(long bookId) {
        RestTemplate restTemplate = new RestTemplate();

        URI getBooksUrl = UriComponentsBuilder.fromUri(baseUri)
                .path("/{book-id}")
                .build()
                .expand(bookId)
                .encode()
                .toUri();

        ResponseEntity<Book> response = restTemplate.getForEntity(getBooksUrl, Book.class);
        Book book = response.getBody();

        return book;
    }
}
