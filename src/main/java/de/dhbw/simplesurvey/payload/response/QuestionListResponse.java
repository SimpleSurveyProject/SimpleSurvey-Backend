package de.dhbw.simplesurvey.payload.response;

import java.util.List;

import de.dhbw.simplesurvey.models.Question;

public class QuestionListResponse {
	private List<Question> questions;

	public QuestionListResponse(List<Question> questions) {
		this.questions = questions;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

}