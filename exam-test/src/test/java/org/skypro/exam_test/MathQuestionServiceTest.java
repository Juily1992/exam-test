package org.skypro.exam_test;

import org.junit.jupiter.api.Test;
import org.skypro.exam_test.question.Question;
import org.skypro.exam_test.services.MathQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MathQuestionServiceTest {
    @Autowired
    private MathQuestionService mathQuestionService;

    @Test
    void addQuestion() {
        Question question = mathQuestionService.addQuestion("2 + 2?", "4", "15", "14");
        assertNotNull(question.getId());
        assertTrue(mathQuestionService.getQuestions().contains(question));
    }

    @Test
    void getRandomQuestion() {
        Question randomQuestion = mathQuestionService.getRandomQuestion();
        assertNotNull(randomQuestion);
        assertTrue(mathQuestionService.getQuestions().contains(randomQuestion));
    }

    @Test
    void removeQuestion() {
        Question question = mathQuestionService.addQuestion("5 + 5?", "10");
        Question removed = mathQuestionService.removeQuestionById(UUID.randomUUID());
        assertNotNull(removed);
        assertFalse(mathQuestionService.getQuestions().contains(removed));
    }
}