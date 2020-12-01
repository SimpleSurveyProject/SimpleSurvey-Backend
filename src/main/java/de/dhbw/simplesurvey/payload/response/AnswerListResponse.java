package de.dhbw.simplesurvey.payload.response;

import java.util.List;

import de.dhbw.simplesurvey.models.Answer;

public class AnswerListResponse {
	private List<Answer> answers;

	public AnswerListResponse(List<Answer> answers) {
		this.answers = answers;
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

}