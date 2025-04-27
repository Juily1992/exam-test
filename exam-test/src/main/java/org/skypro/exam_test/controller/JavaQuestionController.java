package org.skypro.exam_test.controller;

import org.skypro.exam_test.question.Question;

import org.skypro.exam_test.services.ExaminerService;
import org.skypro.exam_test.services.ExaminerServiceImpl;
import org.skypro.exam_test.services.JavaQuestionService;
import org.skypro.exam_test.services.MathQuestionService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;


@RestController
@RequestMapping("/exam/java")
public class JavaQuestionController {

    private final JavaQuestionService javaQuestionService;
    private final MathQuestionService mathQuestionService;
    private final ExaminerService examinerService;


    public JavaQuestionController(JavaQuestionService javaQuestionService, ExaminerService examinerService, MathQuestionService mathQuestionService) {
        this.javaQuestionService = javaQuestionService;
        this.examinerService = examinerService;
        this.mathQuestionService = mathQuestionService;
    }

    @GetMapping("/add")
    public Collection<Question> addQuestion(
            @RequestParam String question,
            @RequestParam String correctAnswer,
            @RequestParam String wrongOption1,
            @RequestParam String wrongOption2,
            @RequestParam String wrongOption3) {

        Question newQuestion = javaQuestionService.add(question, correctAnswer, wrongOption1, wrongOption2, wrongOption3);

        return ((ExaminerServiceImpl) examinerService).addQuestionToHistory(newQuestion);
    }

    @GetMapping("/remove/{id}")
    public ResponseEntity<String> removeQuestion(@PathVariable UUID id) {
        Question removedQuestion = javaQuestionService.remove(id);
        if (removedQuestion != null) {
            return ResponseEntity.ok("Вопрос с ID " + id + " удален");
        }
        removedQuestion = mathQuestionService.remove(id);
        if (removedQuestion != null) {
            return ResponseEntity.ok("Вопрос с ID " + id + " удален");
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Вопрос с ID " + id + " не найден");
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchQuestions(@RequestParam String query) {
        Collection<Question> javaresults = javaQuestionService.searchByKeyword(query);
        Collection<Question> mathresults = mathQuestionService.searchByKeyword(query);

        List<Question> combinedResults = new ArrayList<>();
        combinedResults.addAll(javaresults);
        combinedResults.addAll(mathresults);

        if (combinedResults.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Нет совпадений по вашему запросу");
        }

        return ResponseEntity.ok(combinedResults);
    }

    @GetMapping
    public Collection<Question> getAllQuestions() {
        return javaQuestionService.getAll();
    }
}