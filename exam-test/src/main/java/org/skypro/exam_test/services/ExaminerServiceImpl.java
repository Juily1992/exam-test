package org.skypro.exam_test.services;

import org.skypro.exam_test.question.Question;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ExaminerServiceImpl implements ExaminerService {
    private final List<QuestionService> questionServices;

    public ExaminerServiceImpl(List<QuestionService> questionServices) {
        this.questionServices = questionServices;
    }

    @Override
    public List<Question> getQuestions(int amount) {
        List<Question> allQuestions = questionServices.stream()
                .flatMap(service -> service.getQuestions().stream())
                .collect(Collectors.toList());

        if (amount > allQuestions.size()) {
            throw new IllegalArgumentException("Not enough questions available");
        }

        Set<Question> uniqueQuestions = new HashSet<>();
        Random random = new Random();
        while (uniqueQuestions.size() < amount) {
            int index = random.nextInt(allQuestions.size());
            uniqueQuestions.add(allQuestions.get(index));
        }
        return List.copyOf(uniqueQuestions);
    }
}