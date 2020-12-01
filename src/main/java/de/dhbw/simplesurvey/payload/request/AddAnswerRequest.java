package de.dhbw.simplesurvey.payload.request;

import javax.validation.constraints.NotBlank;

public class AddAnswerRequest {
    @NotBlank
    private String text;

    @NotBlank
    private int questionId;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

}