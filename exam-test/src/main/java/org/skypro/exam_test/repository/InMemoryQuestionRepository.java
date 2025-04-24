package org.skypro.exam_test.repository;

import org.skypro.exam_test.question.Question;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InMemoryQuestionRepository implements QuestionRepository {
    private final Map<UUID, Question> questions = new HashMap<>();

    @Override
    public Question add(Question question) {
        questions.put(question.getId(), question);
        return question;
    }

    @Override
    public Question remove(Question question) {
        return questions.remove(question.getId());
    }

    @Override
    public Collection<Question> getAll() {
        return Collections.unmodifiableCollection(questions.values()); // Возвращаем только значения
    }

    public Question findById(UUID id) {
        return questions.get(id);
    }
}