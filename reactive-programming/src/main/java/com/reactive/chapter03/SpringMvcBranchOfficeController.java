package com.reactive.chapter03;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/books")
public class SpringMvcBranchOfficeController {

    private final Map<Long, Book> bookMap = new HashMap<>();

    @PostConstruct
    public void init() {
        bookMap.put(1L, new Book("Effective Java1"));
        bookMap.put(2L, new Book("Effective Java2"));
        bookMap.put(3L, new Book("Effective Java3"));
        bookMap.put(4L, new Book("Effective Java4"));
        bookMap.put(5L, new Book("Effective Java5"));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{book-id}")
    public ResponseEntity<Book> getBook(@PathVariable("book-id") long bookId) throws InterruptedException {
        // sleep 발생시켜서 I/O 차단 시키기
        Thread.sleep(5000);

        Book book = bookMap.get(bookId);

        return ResponseEntity.ok(book);
    }
}
