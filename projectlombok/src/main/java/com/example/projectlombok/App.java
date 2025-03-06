package com.example.projectlombok;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

import com.example.projectlombok.domain.Book;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class App {

	public static void main(String[] args) throws StreamReadException, IOException {
		final ObjectMapper objectMapper = new ObjectMapper();

		final URL bookAuthorFile = App.class.getClassLoader().getResource("books-authors.json");

		final Book[] books = objectMapper.readValue(bookAuthorFile, Book[].class);

		Arrays.stream(books).forEach(book -> System.out.println(book.toString()));
	}

}
