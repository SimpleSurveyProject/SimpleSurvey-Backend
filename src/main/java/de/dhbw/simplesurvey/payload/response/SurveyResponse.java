package de.dhbw.simplesurvey.payload.response;

import de.dhbw.simplesurvey.models.Survey;

public class SurveyResponse {
	private Survey survey;

	public SurveyResponse(Survey survey) {
		this.survey = survey;
	}

	public Survey getSurvey() {
		return survey;
	}

	public void setSurvey(Survey survey) {
		this.survey = survey;
	}

}