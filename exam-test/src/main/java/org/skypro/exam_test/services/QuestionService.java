package org.skypro.exam_test.services;


import org.skypro.exam_test.question.Question;

import java.util.Collection;
import java.util.UUID;

public interface QuestionService {
    Question remove(UUID id);

    Question add(String question, String correctAnswer, String wrongOption1, String wrongOption2, String wrongOption3);

    Collection<Question> getAll();

    Question getRandomQuestion();
}
