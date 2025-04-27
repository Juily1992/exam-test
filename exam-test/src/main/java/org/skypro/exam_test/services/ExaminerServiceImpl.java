package org.skypro.exam_test.services;

import org.skypro.exam_test.question.Question;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ExaminerServiceImpl implements ExaminerService {
    private Set<Question> lastQuestions = new HashSet<>();
    private final JavaQuestionService javaQuestionService;
    private final MathQuestionService mathQuestionService;

    public ExaminerServiceImpl(JavaQuestionService javaQuestionService, MathQuestionService mathQuestionService) {
        this.javaQuestionService = javaQuestionService;
        this.mathQuestionService = mathQuestionService;
    }

    @Override
    public Collection<Question> getQuestions(int amount) {

        List<Question> allQuestions = new ArrayList<>();
        allQuestions.addAll(javaQuestionService.getAll());
        allQuestions.addAll(mathQuestionService.getAll());

        Set<Question> uniqueQuestions = new HashSet<>(allQuestions);

        if (amount > uniqueQuestions.size()) {
            throw new IllegalArgumentException("Недостаточно вопросов. Запрошено: " + amount + ", Доступно: " + uniqueQuestions.size());
        }

        List<Question> shuffled = new ArrayList<>(uniqueQuestions);
        Collections.shuffle(shuffled);

        Set<Question> selected = new HashSet<>();
        Random random = new Random();
        while (selected.size() < amount) {
            int index = random.nextInt(shuffled.size());
            selected.add(shuffled.get(index));
        }

        lastQuestions = new HashSet<>(selected);
        return selected;
    }

    public Collection<Question> addQuestionToHistory(Question newQuestion) {
        lastQuestions.add(newQuestion);
        return lastQuestions;
    }

}