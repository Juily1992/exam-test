package org.skypro.exam_test.repository;

import org.skypro.exam_test.question.Question;

import java.util.Collection;

public interface QuestionRepository {
          Question add(Question question);
        Question remove(Question question);
        Collection<Question> getAll();

}
