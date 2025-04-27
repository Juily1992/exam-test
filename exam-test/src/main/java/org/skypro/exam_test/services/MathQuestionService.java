package org.skypro.exam_test.services;

import org.skypro.exam_test.question.Question;
import org.skypro.exam_test.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MathQuestionService implements QuestionService {

    private final QuestionRepository repository;
    private final Map<UUID, Question> questions = new HashMap<>();

    public MathQuestionService(QuestionRepository repository) {
        this.repository = repository;
        initializeHistoryQuestions();
    }

    private void initializeHistoryQuestions() {
        add("Сколько будет 2 + 2?", "4", "3", "5", "6");
        add("Чему равно 5 × 5?", "25", "10", "20", "30");
        add("Какова площадь круга с радиусом 3?", "9π", "6π", "3π", "12π");
        add("Решите уравнение: 2x = 10", "5", "2", "10", "20");
        add("Чему равен sin(90°)?", "1", "0", "0.5", "√2/2");
        add("Сколько градусов в прямом угле?", "90°", "45°", "180°", "360°");
        add("10% от 100 равно...", "10", "5", "15", "20");
        add("Какая фигура имеет три стороны?", "Треугольник", "Квадрат", "Круг", "Пятиугольник");
        add("Чему равен квадратный корень из 64?", "8", "4", "6", "32");
        add("Если x = 3, то чему равно 2x + 5?", "11", "8", "10", "15");
    }

    @Override
    public Question add(String question, String correctAnswer, String wrongOption1, String wrongOption2, String wrongOption3) {
        if (questions.values().stream().anyMatch(q -> q.getQuestion().equalsIgnoreCase(question))) {
            throw new IllegalArgumentException("Вопрос уже существует");
        }
        Question newQuestion = new Question(question, correctAnswer, wrongOption1, wrongOption2, wrongOption3);
        questions.put(newQuestion.getId(), newQuestion);
        return newQuestion;
    }

    public Collection<Question> searchByKeyword(String keyword) {
        return questions.values().stream()
                .filter(q -> q.getQuestion().toLowerCase().contains(keyword.toLowerCase()) ||
                        q.getCorrectAnswer().toLowerCase().contains(keyword.toLowerCase()))
                .toList();
    }

    @Override
    public Collection<Question> getAll() {
        return questions.values();
    }

    @Override
    public Question remove(UUID id) {
        return questions.remove(id); //
    }


    @Override
    public Question getRandomQuestion() {
        if (questions.isEmpty()) {
            throw new RuntimeException("Список пуст!");
        }
        Random random = new Random();
        List<Question> questionList = new ArrayList<>(questions.values());
        return questionList.get(random.nextInt(questionList.size()));
    }
}