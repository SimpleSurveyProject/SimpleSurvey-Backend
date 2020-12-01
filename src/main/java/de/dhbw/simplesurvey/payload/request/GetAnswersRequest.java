package de.dhbw.simplesurvey.payload.request;

import javax.validation.constraints.NotBlank;

public class GetAnswersRequest {
    @NotBlank
    private int questionId;

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

}