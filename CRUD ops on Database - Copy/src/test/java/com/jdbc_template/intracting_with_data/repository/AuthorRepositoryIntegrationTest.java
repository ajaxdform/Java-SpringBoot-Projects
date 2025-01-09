package com.jdbc_template.intracting_with_data.repository;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.jdbc_template.intracting_with_data.TestDataUtil;
import com.jdbc_template.intracting_with_data.domain.entities.AuthorEntity;

import static org.assertj.core.api.Assertions.assertThat;;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorRepositoryIntegrationTest {
    

    private AuthorRepository underTest;

    public AuthorRepositoryIntegrationTest(AuthorRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatAuthorCanBeCreatedAndRecalled() {
        AuthorEntity author = TestDataUtil.createTesAuthorA();

        underTest.save(author);
        Optional<AuthorEntity> result = underTest.findById(author.getId());
        assertThat(result.isPresent());
        assertThat(result.get()).isEqualTo(author);
    }

    @Test
    public void testThatMultipleAuthorCanBeCreatedAndReacalled() {
        AuthorEntity authorA = TestDataUtil.createTesAuthorA();
        underTest.save(authorA);
        AuthorEntity authorB = TestDataUtil.createTesAuthorB();
        underTest.save(authorB);
        AuthorEntity authorC = TestDataUtil.createTesAuthorC();
        underTest.save(authorC);

        Iterable<AuthorEntity> result = underTest.findAll();
        assertThat(result).hasSize(3);
        assertThat(result).containsExactly(authorA,authorB,authorC);
    }

    @Test
    public void testThatUpdateGeneratesCorrectSql() {
        AuthorEntity authorA = TestDataUtil.createTesAuthorA();
        underTest.save(authorA);

        authorA.setName("UPdated");
        underTest.save(authorA);

        Optional<AuthorEntity> result = underTest.findById(authorA.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(authorA);
    }

    @Test 
    public void testThatDeleteGeneratesCorrectSql() {
        AuthorEntity authorA = TestDataUtil.createTesAuthorA();
        underTest.save(authorA);

        underTest.deleteById(authorA.getId());

        Optional<AuthorEntity> res = underTest.findById(authorA.getId());

        assertThat(res).isEmpty();
    }

    @Test
    public void testThatAuthorAgeIsLessThan() {
        AuthorEntity testAuthorA = TestDataUtil.createTesAuthorA();
        underTest.save(testAuthorA);

        AuthorEntity testAuthorB = TestDataUtil.createTesAuthorB();
        underTest.save(testAuthorB);

        AuthorEntity testAuthorC = TestDataUtil.createTesAuthorC();
        underTest.save(testAuthorC);

        Iterable<AuthorEntity> res = underTest.ageLessThan(50);

        // assertThat(res).containsExactly(testAuthorA, testAuthorC);
        assertThat(res).containsExactly(testAuthorB, testAuthorC);
    }

    @Test
    public void testThatAuthorIsGreaterThan() {
        AuthorEntity testAuthorA = TestDataUtil.createTesAuthorA();
        underTest.save(testAuthorA);

        AuthorEntity testAuthorB = TestDataUtil.createTesAuthorB();
        underTest.save(testAuthorB);

        AuthorEntity testAuthorC = TestDataUtil.createTesAuthorC();
        underTest.save(testAuthorC);

        Iterable<AuthorEntity> res = underTest.ageGreaterThan(50);

        assertThat(res).containsExactly(testAuthorA);
    }
}
