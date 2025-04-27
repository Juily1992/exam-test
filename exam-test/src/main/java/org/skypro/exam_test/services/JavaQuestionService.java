package org.skypro.exam_test.services;

import org.skypro.exam_test.question.Question;
import org.skypro.exam_test.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class JavaQuestionService implements QuestionService {

    private final QuestionRepository repository;
    private final Map<UUID, Question> questions = new HashMap<>();

    public JavaQuestionService(QuestionRepository repository) {
        this.repository = repository;
        initializeHistoryQuestions();
    }

    private void initializeHistoryQuestions() {
        add("Кто был первым президентом США?", "Джордж Вашингтон", "Томас Джефферсон", "Абрахам Линкольн", "Франклин Рузвельт");
        add("В каком году началась Вторая мировая война?", "1939", "1941", "1914", "1945");
        add("Какой город был столицей Российской империи?", "Санкт-Петербург", "Москва", "Киев", "Новгород");
        add("Кто написал 'Войну и мир'?", "Лев Толстой", "Федор Достоевский", "Александр Пушкин", "Николай Гоголь");
        add("Какое событие произошло в 1789 году во Франции?", "Великая французская революция", "Принятие Декларации прав человека", "Казнь Людовика XVI", "Реставрация монархии");
        add("Как назывался первый искусственный спутник Земли?", "Спутник-1", "Луноход-1", "Восток-1", "Аполлон-11");
        add("Какой договор завершил Столетнюю войну?", "Турнейский договор", "Версальский договор", "Вестфальский договор", "Парижский договор");
        add("Кто был последним русским царем?", "Николай II", "Александр III", "Петр I", "Екатерина II");
        add("Какой континент был открыт последним?", "Антарктида", "Австралия", "Африка", "Южная Америка");
        add("Кто возглавил Октябрьскую революцию 1917 года?", "Ленин", "Сталин", "Троцкий", "Керенский");
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
        return questions.remove(id);
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