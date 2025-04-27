package org.skypro.exam_test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skypro.exam_test.question.Question;
import org.skypro.exam_test.repository.InMemoryQuestionRepository;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Collection;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class InMemoryQuestionRepositoryTest {
    private InMemoryQuestionRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryQuestionRepository();
    }

    @Test
    void testAddAndGetAll() {

        Question question = new Question("Сколько будет 2 + 2?", "4", "3", "5", "6");

        Question addedQuestion = repository.add(question);

        assertNotNull(addedQuestion);
        assertEquals("Сколько будет 2 + 2?", addedQuestion.getQuestion());

        Collection<Question> allQuestions = repository.getAll();

        assertEquals(1, allQuestions.size());
        assertTrue(allQuestions.contains(addedQuestion));
    }

    @Test
    void testRemove() {
        Question question = new Question("Сколько будет 2 + 2?", "4", "3", "5", "6");
        repository.add(question);
        Question removedQuestion = repository.remove(question);
        assertNotNull(removedQuestion);
        assertEquals("Сколько будет 2 + 2?", removedQuestion.getQuestion());
        Collection<Question> allQuestions = repository.getAll();
        assertTrue(allQuestions.isEmpty());
    }

    @Test
    void testRemoveNonExistingQuestion() {
        Question question = new Question("Сколько будет 2 + 2?", "4", "3", "5", "6");
        Question removedQuestion = repository.remove(question);
        assertNull(removedQuestion);
    }

    @Test
    void testUniqueIdForQuestions() {
        Question question1 = new Question("Сколько будет 2 + 2?", "4", "3", "5", "6");
        Question question2 = new Question("Чему равен sin(90°)?", "1", "0", "0.5", "√2/2");

        Question addedQuestion1 = repository.add(question1);
        Question addedQuestion2 = repository.add(question2);

        assertNotNull(addedQuestion1.getId());
        assertNotNull(addedQuestion2.getId());
        assertNotEquals(addedQuestion1.getId(), addedQuestion2.getId());
    }
}