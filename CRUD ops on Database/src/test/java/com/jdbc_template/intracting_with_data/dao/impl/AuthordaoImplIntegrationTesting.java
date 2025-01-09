package com.jdbc_template.intracting_with_data.dao.impl;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.jdbc_template.intracting_with_data.TestDataUtil;
import com.jdbc_template.intracting_with_data.domain.Author;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthordaoImplIntegrationTesting {
    

    private AuthorDaoImpl underTest;

    @Autowired

    public AuthordaoImplIntegrationTesting(AuthorDaoImpl underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatAuthorCanBeCreatedAndRecalled() {
        Author author = TestDataUtil.createTesAuthorA();

        underTest.create(author);
        Optional<Author> result = underTest.findOne(author.getId());
        assertThat(result.isPresent());
        assertThat(result.get()).isEqualTo(author);
    }

    @Test
    public void testThatMultipleAuthorCanBeCreatedAndReacalled() {
        Author authorA = TestDataUtil.createTesAuthorA();
        underTest.create(authorA);
        Author authorB = TestDataUtil.createTesAuthorB();
        underTest.create(authorB);
        Author authorC = TestDataUtil.createTesAuthorC();
        underTest.create(authorC);

        List<Author> result = underTest.find();
        assertThat(result).hasSize(3);
        assertThat(result).containsExactly(authorA,authorB,authorC);
    }

    @Test
    public void testThatUpdateGeneratesCorrectSql() {
        Author authorA = TestDataUtil.createTesAuthorA();
        underTest.create(authorA);

        authorA.setName("UPdated");
        underTest.update(authorA.getId(), authorA);

        Optional<Author> result = underTest.findOne(authorA.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(authorA);
    }

    @Test 
    public void testThatDeleteGeneratesCorrectSql() {
        Author author = TestDataUtil.createTesAuthorA();
        underTest.create(author);

        underTest.delete(author.getId());

        Optional<Author> res = underTest.findOne(author.getId());

        assertThat(res).isEmpty();
    }
}
