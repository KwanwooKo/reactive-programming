package com.reactive.chapter03;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/v1/books")
public class SpringMvcHeadOfficeController {

    private final RestTemplate restTemplate;
    URI baseUri = UriComponentsBuilder.newInstance()
            .scheme("http")
            .host("localhost")
            .port(7070)
            .path("/v1/books")
            .build()
            .encode()
            .toUri();

    public SpringMvcHeadOfficeController(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public ResponseEntity<Book> getBook(@PathVariable("book-id") long bookId) {
        URI getBookUri = UriComponentsBuilder.fromUri(baseUri)
                .path("/{book-id}")
                .build()
                .expand(bookId)
                .encode()
                .toUri();

        ResponseEntity<Book> response = restTemplate.getForEntity(getBookUri, Book.class);
        Book book = response.getBody();
        return ResponseEntity.ok(book);
    }
}
