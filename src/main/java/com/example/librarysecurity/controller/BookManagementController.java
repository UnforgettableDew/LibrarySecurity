package com.example.librarysecurity.controller;

import com.example.librarysecurity.feign.BookFeignInterface;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequestMapping("/api/v1/library/books")
public class BookManagementController {

    private final BookFeignInterface bookFeignInterface;

    public BookManagementController(BookFeignInterface bookFeignInterface) {
        this.bookFeignInterface = bookFeignInterface;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR', 'ROLE_DEFAULT')")
    @GetMapping("/list")
    public Object getAllBooks() {
        return bookFeignInterface.getAllBooks();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR', 'ROLE_DEFAULT')")
    @GetMapping("/id/{id}")
    public Object getBookById(@PathVariable Long id) {
        return bookFeignInterface.getBookById(id);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR', 'ROLE_DEFAULT')")
    @GetMapping("/title/{title}")
    public Object getBooksByTitle(@PathVariable(name = "title") String title) {
        return bookFeignInterface.getBooksByTitle(title);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    @GetMapping("/students-read/{title}")
    public Object getStudentsWhoReadBook(@PathVariable String title) {
        return bookFeignInterface.getStudentsWhoReadBook(title);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/save")
    public void saveBook(@Valid @RequestBody Object book) {
        bookFeignInterface.saveBook(book);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public void deleteBookById(@PathVariable Long id) {
        bookFeignInterface.deleteBookById(id);
    }

}
