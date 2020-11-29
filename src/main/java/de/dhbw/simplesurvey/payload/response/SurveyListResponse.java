package de.dhbw.simplesurvey.payload.response;

import java.util.List;

import de.dhbw.simplesurvey.models.Survey;

public class SurveyListResponse {
	private List<Survey> surveys;

	public SurveyListResponse(List<Survey> surveys) {
		this.surveys = surveys;
	}

	public List<Survey> getSurveys() {
		return surveys;
	}

	public void setSurveys(List<Survey> surveys) {
		this.surveys = surveys;
	}
}