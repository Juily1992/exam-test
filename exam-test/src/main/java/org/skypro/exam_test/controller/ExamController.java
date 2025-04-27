package org.skypro.exam_test.controller;

import org.skypro.exam_test.question.Question;
import org.skypro.exam_test.services.ExaminerService;
import org.skypro.exam_test.services.ExaminerServiceImpl;
import org.skypro.exam_test.services.JavaQuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;

@RestController
@RequestMapping("/exam")
public class ExamController {

    private final ExaminerService examinerService;

    public ExamController(ExaminerService examinerService) {
        this.examinerService = examinerService;
    }

    @GetMapping("/get/{amount}")
    public Collection<Question> getQuestions(@PathVariable int amount) {
        try {
            return examinerService.getQuestions(amount);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }


}