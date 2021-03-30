package de.dhbw.simplesurvey.payload.request;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class AddQuestionRequest {
	@NotBlank
	private String text;

	@NotBlank
	private int surveyId;
}