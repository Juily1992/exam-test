package org.skypro.exam_test.services;

import org.skypro.exam_test.exceptions.DuplicateQuestionException;
import org.skypro.exam_test.question.Question;
import org.skypro.exam_test.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MathQuestionService implements QuestionService {
    private final Map<UUID, Question> questions = new HashMap<>();
    private final List<Question> lastUsedQuestions = new ArrayList<>(); // Кэш последних вопросов
    private static final int MAX_LAST_USED = 10; // Максимальное количеств

    public MathQuestionService() {
        initializeQuestions();
    }

    private void initializeQuestions() {
        addQuestion("Сколько будет 2 + 2?", "4", "30", "14", "12");
        addQuestion("Сколько будет 5 * 3?", "15", "25", "50", "10");
        addQuestion("Чему равен квадрат числа 7?", "49", "39", "59", "29");
        addQuestion("Какое число больше: 10 или 15?", "15", "12", "15");
        addQuestion("Сколько секунд в минуте?", "60", "30", "120", "90");
        addQuestion("Чему равен корень из 16?", "4", "8", "2", "6");
        addQuestion("Сколько градусов в прямом угле?", "90", "180", "45", "120");
        addQuestion("Какова площадь квадрата со стороной 3?", "9", "8", "2", "3");
        addQuestion("Сколько будет 100 / 10?", "10", "12", "10");
        addQuestion("Чему равна сумма углов треугольника?", "180", "25", "10");
    }

    @Override
    public Question addQuestion(String questionText, String answer, String... options) {
        Question newQuestion = new Question(questionText, answer, options);

        if (questions.values().stream()
                .anyMatch(q -> q.getQuestion().equals(questionText)
                        && q.getAnswer().equals(answer))) {
            throw new DuplicateQuestionException("Вопрос уже существует");
        }

        questions.put(newQuestion.getId(), newQuestion);
        return newQuestion;
    }

    public List<Question> getLastUsedQuestions() {
        return Collections.unmodifiableList(lastUsedQuestions); // Возвращаем неизменяемый список
    }

    public void updateLastUsedQuestions(List<Question> newQuestions) {
        lastUsedQuestions.clear();
        lastUsedQuestions.addAll(newQuestions);
        // Ограничиваем размер кэша
        if (lastUsedQuestions.size() > MAX_LAST_USED) {
            lastUsedQuestions.subList(0, lastUsedQuestions.size() - MAX_LAST_USED).clear();
        }
    }

        @Override
    public Question removeQuestionById(UUID id) {
        return questions.remove(id); // Удаляем вопрос из мапы по ключу
    }

    @Override
    public Question getRandomQuestion() {
        if (questions.isEmpty()) {
            throw new IllegalStateException("No questions available");
        }
        List<UUID> keys = new ArrayList<>(questions.keySet());
        Random random = new Random();
        UUID randomKey = keys.get(random.nextInt(keys.size()));
        return questions.get(randomKey);
    }

    @Override
    public List<Question> findQuestionsByKeyword(String keyword) {
        return questions.values().stream()
                .filter(q -> q.getQuestion().toLowerCase().contains(keyword.toLowerCase())
                        || q.getAnswer().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Question> getQuestions() {
        return Collections.unmodifiableCollection(questions.values()); // Возвращаем только значения
    }

}