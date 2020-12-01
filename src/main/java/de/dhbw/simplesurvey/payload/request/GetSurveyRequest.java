package de.dhbw.simplesurvey.payload.request;

import javax.validation.constraints.NotBlank;

public class GetSurveyRequest {
    @NotBlank
    private int surveyId;

    public int getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(int surveyId) {
        this.surveyId = surveyId;
    }

}