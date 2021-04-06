package de.dhbw.simplesurvey.payload.request.survey;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class AddAnswerRequest {
	@NotBlank
	private String text;

	@NotBlank
	private int questionId;
}