package org.skypro.exam_test;

import org.junit.jupiter.api.Test;
import org.skypro.exam_test.question.Question;
import org.skypro.exam_test.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class InMemoryQuestionRepositoryTest {
    @Autowired
    private QuestionRepository repository;

    @Test
    void addAndGetAll() {
        Question question = new Question("2 + 2?", "4");
        repository.add(question);
        Collection<Question> all = repository.getAll();
        assertTrue(all.contains(question));
    }

    @Test
    void remove() {
        Question question = new Question("2 + 2?", "4");
        repository.add(question);
        repository.remove(question);
        assertFalse(repository.getAll().contains(question));
    }
}