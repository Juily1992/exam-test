package org.skypro.exam_test.controller;


import org.skypro.exam_test.question.Question;
import org.skypro.exam_test.services.JavaQuestionService;
import org.skypro.exam_test.services.MathQuestionService;
import org.skypro.exam_test.services.QuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/exam/java")
public class JavaQuestionController {
    private final JavaQuestionService service;
    private final MathQuestionService questionService;

    public JavaQuestionController(JavaQuestionService service, MathQuestionService questionService) {
        this.questionService = questionService;
        this.service = service;
    }

    @GetMapping("/add")
    @PostMapping("/add")
    public ResponseEntity<List<Question>> addQuestion(
            @RequestParam String question,
            @RequestParam String answer,
            @RequestParam String option1,
            @RequestParam String option2,
            @RequestParam String option3) {

        // Добавляем новый вопрос
        Question newQuestion = service.addQuestion(question, answer, option1, option2, option3);

        // Получаем последние использованные вопросы
        List<Question> lastUsedQuestions = ((JavaQuestionService) service).getLastUsedQuestions();

        // Добавляем новый вопрос к последним использованным
        List<Question> result = new ArrayList<>(lastUsedQuestions);
        result.add(newQuestion);

        return ResponseEntity.ok(result); // Возвращаем обновленный список

    }

    @GetMapping
    public Collection<Question> getAll() {
        return service.getQuestions();
    }

    @GetMapping("/remove/{id}")
      public ResponseEntity<String> removeQuestion(@PathVariable UUID id) {
        // Удаление вопроса из JavaQuestionService
        Question removedJavaQuestion = service.removeQuestionById(id);

        // Удаление вопроса из MathQuestionService
        Question removedMathQuestion = questionService.removeQuestionById(id);

        // Проверяем, был ли удален хотя бы один вопрос
        if (removedJavaQuestion != null || removedMathQuestion != null) {
            return ResponseEntity.ok("Вопрос с ID " + id + " найден и удален.");
        }

        // Если вопрос не найден ни в одном из сервисов
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Вопрос с ID " + id + " не найден!");
    }
    @GetMapping("/find")
    public ResponseEntity<?> findQuestionsByKeyword(@RequestParam String keyword) {
        List<Question> foundQuestions = service.findQuestionsByKeyword(keyword); // Java-вопросы
        List<Question> foundMathQuestions = questionService.findQuestionsByKeyword(keyword); // Математические вопросы

        // Объединяем списки
        List<Question> allFoundQuestions = new ArrayList<>();
        allFoundQuestions.addAll(foundQuestions);
        allFoundQuestions.addAll(foundMathQuestions);

        if (allFoundQuestions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Не найдено вопросов с искомым словом: " + keyword);
        }

        return ResponseEntity.ok(allFoundQuestions);
    }
}
