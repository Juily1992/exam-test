package org.skypro.exam_test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.skypro.exam_test.question.Question;
import org.skypro.exam_test.services.ExaminerServiceImpl;
import org.skypro.exam_test.services.JavaQuestionService;
import org.skypro.exam_test.services.MathQuestionService;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class ExaminerServiceImplTest {

    @Mock
    private JavaQuestionService javaQuestionService;

    @Mock
    private MathQuestionService mathQuestionService;

    @InjectMocks
    private ExaminerServiceImpl examinerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetQuestions_ValidAmount() {
        Question q1 = new Question("Когда была Великая отечественная?", "1812", "2012", "1712", "1612");
        Question q2 = new Question("Сколько будет 2 + 2?", "4", "3", "5", "6");

        when(javaQuestionService.getAll()).thenReturn(List.of(q1));
        when(mathQuestionService.getAll()).thenReturn(List.of(q2));

        Collection<Question> questions = examinerService.getQuestions(2);

        assertEquals(2, questions.size());
        assertTrue(questions.contains(q1));
        assertTrue(questions.contains(q2));

        verify(javaQuestionService).getAll();
        verify(mathQuestionService).getAll();
    }

    @Test
    void testGetQuestions_TooManyQuestionsRequested() {

        Question q1 = new Question("Когда была Великая отечественная?", "1812", "2012", "1912", "1612");

        when(javaQuestionService.getAll()).thenReturn(List.of(q1));
        when(mathQuestionService.getAll()).thenReturn(Collections.emptyList());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> examinerService.getQuestions(2));

        assertEquals("Не достаточно вопросов. Запрошено: 2. Доступно : 1.", exception.getMessage());

        verify(javaQuestionService).getAll();
        verify(mathQuestionService).getAll();
    }

    @Test
    void testGetQuestions_NoQuestionsAvailable() {

        when(javaQuestionService.getAll()).thenReturn(Collections.emptyList());
        when(mathQuestionService.getAll()).thenReturn(Collections.emptyList());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> examinerService.getQuestions(1));

        assertEquals("Нет доступных вопросов. Запрошено: 1, Доступно: 0", exception.getMessage());

        verify(javaQuestionService).getAll();
        verify(mathQuestionService).getAll();
    }
}