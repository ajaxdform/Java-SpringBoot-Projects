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
import com.jdbc_template.intracting_with_data.domain.dto.AuthorDto;
import com.jdbc_template.intracting_with_data.domain.entities.AuthorEntity;
import com.jdbc_template.intracting_with_data.services.AuthorServices;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class AuthorControllerIntegrationTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private AuthorServices authorServices;

    public AuthorControllerIntegrationTest(MockMvc mockMvc, AuthorServices authorServices) {
        this.mockMvc = mockMvc;
        this.authorServices = authorServices;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testThatCreateAuthorReturnsHttp201() throws Exception {
        AuthorEntity testAuthorA = TestDataUtil.createTesAuthorA();
        testAuthorA.setId(null);
        String authorJson = objectMapper.writeValueAsString(testAuthorA);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson))
                .andExpect(
                        MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testThatCreateAuthorReturnsSavedAuthor() throws Exception {
        AuthorEntity testAuthorA = TestDataUtil.createTesAuthorA();
        testAuthorA.setId(null);
        String authorJson = objectMapper.writeValueAsString(testAuthorA);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.name").value("Shubham Salaskar"))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.age").value(80));
    }

    @Test
    public void testThatfindAllReturns200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(
                        MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatfindAllReturnsListOfAuthors() throws Exception {
        AuthorEntity tesAuthorEntityA = TestDataUtil.createTesAuthorA();
        authorServices.save(tesAuthorEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$[0].id").isNumber())
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$[0].name").value("Shubham Salaskar"))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$[0].age").value(80));
    }

    @Test
    public void testThatfindAllReturns200andAuthorExists() throws Exception {
        AuthorEntity testAuthorA = TestDataUtil.createTesAuthorA();
        authorServices.save(testAuthorA);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(
                        MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatfindAOneReturnsAuthorExist() throws Exception {
        AuthorEntity tesAuthorEntityA = TestDataUtil.createTesAuthorA();
        authorServices.save(tesAuthorEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.name").value("Shubham Salaskar"))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.age").value(80));
    }

    @Test
    public void testThatFullUpdateReturnStatus404() throws Exception {
        AuthorDto testAuthorDtoA = TestDataUtil.testAuthorDtoA();
        String authorDtoJson = objectMapper.writeValueAsString(testAuthorDtoA);

        AuthorEntity testAuthorA = TestDataUtil.createTesAuthorA();
        authorServices.save(testAuthorA);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorDtoJson))
                .andExpect(
                        MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatFullUpdateReturnStatus200() throws Exception {
        AuthorEntity tetsAuthorEntityA = TestDataUtil.createTesAuthorA();
        AuthorEntity savedAuthor = authorServices.save(tetsAuthorEntityA);

        AuthorDto testAuthorDtoA = TestDataUtil.testAuthorDtoA();
        String authorDtoJson = objectMapper.writeValueAsString(testAuthorDtoA);

        AuthorEntity testAuthorA = TestDataUtil.createTesAuthorA();
        authorServices.save(testAuthorA);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/" + savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorDtoJson))
                .andExpect(
                        MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testFullUpdateReturnAuthorExistS() throws Exception {
        AuthorEntity tetsAuthorEntityA = TestDataUtil.createTesAuthorA();
        AuthorEntity savedAuthor = authorServices.save(tetsAuthorEntityA);

        AuthorEntity authorDto = TestDataUtil.createTesAuthorB();
        authorDto.setId(savedAuthor.getId());

        String authorDtoUpdateJson = objectMapper.writeValueAsString(authorDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/" + savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorDtoUpdateJson))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.id").value(savedAuthor.getId()))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.name").value(authorDto.getName()))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.age").value(authorDto.getAge()));
    }

    @Test
    public void testThatDeleteauthorreturns204forNonexisting() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/authors/99")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(
                        MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testThatDeleteauthorreturns204forexisting() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createTesAuthorA();
        AuthorEntity savedAuthor = authorServices.save(authorEntity);
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/authors/" + savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(
                        MockMvcResultMatchers.status().isNoContent());
    }
}
