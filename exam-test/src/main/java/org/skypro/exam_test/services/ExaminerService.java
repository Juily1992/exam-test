package org.skypro.exam_test.services;

import org.skypro.exam_test.question.Question;

import java.util.Collection;

public interface ExaminerService {
    Collection<Question> getQuestions(int amount);
}