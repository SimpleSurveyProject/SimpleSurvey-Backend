package de.dhbw.simplesurvey.payload.request;

import javax.validation.constraints.NotBlank;

public class CreateSurveyRequest {
    @NotBlank
    private String description;

    @NotBlank
    private String title;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}