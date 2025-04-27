package org.skypro.exam_test.question;

import java.util.UUID;

public class Question {
    private UUID id;
    private String question;
    private String correctAnswer;
    private String wrongOption1;
    private String wrongOption2;
    private String wrongOption3;

    public Question(String question, String correctAnswer, String wrongOption1, String wrongOption2, String wrongOption3) {
        this.id = UUID.randomUUID();
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.wrongOption1 = wrongOption1;
        this.wrongOption2 = wrongOption2;
        this.wrongOption3 = wrongOption3;
    }

    public UUID getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String getWrongOption1() {
        return wrongOption1;
    }

    public String getWrongOption2() {
        return wrongOption2;
    }

    public String getWrongOption3() {
        return wrongOption3;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question1 = (Question) o;
        return id.equals(question1.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}