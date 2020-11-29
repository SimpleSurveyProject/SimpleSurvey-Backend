package de.dhbw.simplesurvey.payload.response;

public class SurveyCreatedResponse {
    private int id;

    public SurveyCreatedResponse(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
