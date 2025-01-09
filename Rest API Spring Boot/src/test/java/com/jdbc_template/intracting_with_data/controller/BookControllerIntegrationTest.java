package com.jdbc_template.intracting_with_data.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jdbc_template.intracting_with_data.TestDataUtil;
import com.jdbc_template.intracting_with_data.domain.dto.BookDto;
import com.jdbc_template.intracting_with_data.domain.entities.BookEntity;
import com.jdbc_template.intracting_with_data.services.BookServices;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class BookControllerIntegrationTest {
    
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private BookServices bookServices;

    public BookControllerIntegrationTest(MockMvc mockMvc, ObjectMapper objectMapper, BookServices bookServices) {
        this.mockMvc = mockMvc;
        this.bookServices = bookServices;
        this.objectMapper = objectMapper;
    }

    @Test
    public void testThatCreateBookReturn201() throws Exception {
        BookDto bookDto = TestDataUtil.createTestbookDtoA(null);
        String createBookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + bookDto.getIsbn())
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(createBookJson)
        ).andExpect(
            MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateBookReturnCreateBooks() throws Exception {
        BookDto bookDto = TestDataUtil.createTestbookDtoA(null);
        String createBookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + bookDto.getIsbn())
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(createBookJson)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.isbn").value(bookDto.getIsbn())
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.title").value(bookDto.getTitle())
        );
    }

    @Test
    public void testThatListOfAllBooksReturns200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books")
                                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
            MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatListOfBooksreturns200() throws Exception {
        BookEntity testBookEntityA = TestDataUtil.createTestbookEntityA(null);
        bookServices.createBook(testBookEntityA.getIsbn(), testBookEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books")
                                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$[0].isbn").value("3445-332-5566-4433")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$[0].title").value("Negotitor")
        );
    }

    @Test
    public void testThatListOfAllBooksReturns200AndBookExists() throws Exception {
        BookEntity bookEntityAA = TestDataUtil.createTestbookEntityA(null);
        bookServices.createBook(bookEntityAA.getIsbn(), bookEntityAA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/" + bookEntityAA.getIsbn())
                                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
            MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatPartialUpdateBookreturns200Status() throws Exception {
        BookEntity testBookEntity = TestDataUtil.createTestbookEntityA(null);
        bookServices.createBook(testBookEntity.getIsbn(), testBookEntity);

        BookDto testBookDto = TestDataUtil.createTestbookDtoA(null);
        testBookDto.setIsbn(testBookEntity.getIsbn());
        testBookDto.setTitle("UPDATED");

        String bookson = objectMapper.writeValueAsString(testBookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/books/" + testBookEntity.getIsbn())
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(bookson)
        ).andExpect(
            MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatPartialUpdateBookreturnsUpdatedSatus() throws Exception {
        BookEntity testBookEntity = TestDataUtil.createTestbookEntityA(null);
        bookServices.createBook(testBookEntity.getIsbn(), testBookEntity);

        BookDto testBookDto = TestDataUtil.createTestbookDtoA(null);
        testBookDto.setIsbn(testBookEntity.getIsbn());
        testBookDto.setTitle("UPDATED");

        String bookson = objectMapper.writeValueAsString(testBookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/books/" + testBookEntity.getIsbn())
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(bookson)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.isbn").value(testBookEntity.getIsbn())
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.title").value("UPDATED")
        );
    }

    @Test
    public void testThatDeleteBookReturn204forNonExstingBook() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/books/99")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(
                        MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testThatDeleteBookReturn204forExstingBook() throws Exception {
        BookEntity testBookEntity = TestDataUtil.createTestbookEntityA(null);
        BookEntity savedBookc = bookServices.createBook(testBookEntity.getIsbn(), testBookEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/books/" + savedBookc.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(
                        MockMvcResultMatchers.status().isNoContent());
    }
}
