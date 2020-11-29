package de.dhbw.simplesurvey.payload.request;

import javax.validation.constraints.NotBlank;

public class AddQuestionRequest {
    @NotBlank
    private String text;

    @NotBlank
    private int surveyId;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(int surveyId) {
        this.surveyId = surveyId;
    }

}