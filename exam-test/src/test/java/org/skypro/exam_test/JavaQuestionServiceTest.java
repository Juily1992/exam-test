package org.skypro.exam_test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.skypro.exam_test.question.Question;
import org.skypro.exam_test.repository.QuestionRepository;
import org.skypro.exam_test.services.JavaQuestionService;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class JavaQuestionServiceTest {

    @Mock
    private QuestionRepository repository;

    @InjectMocks
    private JavaQuestionService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddAndGetAll() {
        Question question = new Question("Когда была Великая отечественная?", "1812", "2012", "1912", "1612");
        when(repository.add(question)).thenReturn(question);
        service.add(question.getQuestion(), question.getCorrectAnswer(), question.getWrongOption1(), question.getWrongOption2(), question.getWrongOption3());
        verify(repository).add(question);

        when(repository.getAll()).thenReturn(List.of(question));
        List<Question> allQuestions = (List<Question>) service.getAll();

        assertEquals(1, allQuestions.size());
        assertEquals("Что такое JVM?", allQuestions.get(0).getQuestion());
    }

    @Test
    void testRemove() {
        Question question = new Question("Когда была Великая отечественная?", "1812", "2012", "1912", "1612");
        when(repository.remove(question)).thenReturn(question);

        Question removedQuestion = service.remove(question.getId());

        verify(repository).remove(question);
        assertNotNull(removedQuestion);
        assertEquals("Когда была Великая отечественная?", removedQuestion.getQuestion());
    }

    @Test
    void testRemoveNonExistingQuestion() {
        Question question = new Question("Когда была Великая отечественная?", "1812", "2012", "1912", "1612");
        when(repository.remove(question)).thenReturn(null);

        Question removedQuestion = service.remove(question.getId());

        assertNull(removedQuestion);
        verify(repository).remove(question);
    }

    @Test
    void testGetAll() {
        Question question1 = new Question("Когда была Великая отечественная?", "1812", "2012", "1912", "1612");
        Question question2 = new Question("Кто был последним русским царем?", "Николай II", "Александр III", "Петр I", "Екатерина II");

        when(repository.getAll()).thenReturn(List.of(question1, question2));

        List<Question> allQuestions = (List<Question>) service.getAll();

        assertEquals(2, allQuestions.size());
        assertEquals("Когда была Великая отечественная?", allQuestions.get(0).getQuestion());
        assertEquals("Кто был последним русским царем?", allQuestions.get(1).getQuestion());
    }
}