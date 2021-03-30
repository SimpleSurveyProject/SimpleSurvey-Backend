package de.dhbw.simplesurvey.payload.request;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class GetQuestionsRequest {
	@NotBlank
	private int surveyId;
}