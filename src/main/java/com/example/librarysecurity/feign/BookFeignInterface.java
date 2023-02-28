package com.example.librarysecurity.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@FeignClient(name = "library-books",
        url = "http://localhost:8080/books",
        contextId = "bookFeignInterface")
@Component("bookFeignInterface")
public interface BookFeignInterface {

    @PostMapping()
    void saveBook(@Valid @RequestBody Object book);

    @DeleteMapping("{id}")
    void deleteBookById(@PathVariable Long id);

    @GetMapping()
    Object getAllBooks();

    @GetMapping("{id}")
    Object getBookById(@PathVariable Long id);

    @GetMapping("/title/{title}")
    Object getBooksByTitle(@PathVariable(name = "title") String title);

    @GetMapping("/students-read/{title}")
    Object getStudentsWhoReadBook(@PathVariable String title);
}
