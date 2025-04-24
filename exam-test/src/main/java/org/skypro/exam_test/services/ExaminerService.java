package org.skypro.exam_test.services;

import org.skypro.exam_test.question.Question;

import java.util.List;

public interface ExaminerService {
    List<Question> getQuestions(int amount);
}
