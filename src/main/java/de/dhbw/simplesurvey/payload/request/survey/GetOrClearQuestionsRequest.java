package de.dhbw.simplesurvey.payload.request.survey;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class GetOrClearQuestionsRequest {
	@NotBlank
	private int surveyId;
}