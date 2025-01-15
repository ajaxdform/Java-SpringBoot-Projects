package com.jdbc_template.intracting_with_data.controller;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jdbc_template.intracting_with_data.domain.dto.BookDto;
import com.jdbc_template.intracting_with_data.domain.entities.BookEntity;
import com.jdbc_template.intracting_with_data.mappers.Mapper;
import com.jdbc_template.intracting_with_data.services.BookServices;

@RestController
public class BookController {
    private BookServices bookServices;

    private Mapper<BookEntity, BookDto> bookMapper;

    public BookController(Mapper<BookEntity, BookDto> bookMapper, BookServices bookServices) {
        this.bookMapper = bookMapper;
        this.bookServices = bookServices;
    }

    @PutMapping("/books/{isbn}")
    public ResponseEntity<BookDto> fullUpdateBook(@PathVariable String isbn, @RequestBody BookDto bookDto) {
        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        boolean bookExists = bookServices.isExists(isbn);
        BookEntity savedBookEntity = bookServices.createBook(isbn, bookEntity);
        BookDto savedBookDto = bookMapper.mapTo(savedBookEntity);

        if (bookExists) {
            return new ResponseEntity<>(savedBookDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(savedBookDto, HttpStatus.CREATED);
        }
    }

    @GetMapping(path = "/books")
    public Page<BookDto> listAllBooks(Pageable pageable) {
        Page<BookEntity> books = bookServices.findAll(pageable);
        return books.map(bookMapper::mapTo);
    }

    @GetMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> getBook(@PathVariable String isbn) {
        Optional<BookEntity> foundBook = bookServices.findOne(isbn);
        return foundBook.map(bookEntity -> {
            BookDto bookDto = bookMapper.mapTo(bookEntity);
            return new ResponseEntity<>(bookDto, HttpStatus.OK);
        }).orElse(
                new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> partialUpdateBook(@PathVariable String isbn, @RequestBody BookDto bookDto) {
        if(!bookServices.isExists(isbn)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        BookEntity updateBook = bookServices.partialUpdate(isbn, bookEntity);

        return new ResponseEntity<>(
            bookMapper.mapTo(updateBook),
            HttpStatus.OK
        );
    }

    @SuppressWarnings("rawtypes")
    @DeleteMapping(path = "/books/{isbn}")
    public ResponseEntity deleteBookEntity(@PathVariable String isbn) {
        bookServices.deleteBook(isbn);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
