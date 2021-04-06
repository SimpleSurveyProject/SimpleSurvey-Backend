package de.dhbw.simplesurvey.payload.request.survey;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class CreateSurveyRequest {
	@NotBlank
	private String description;

	@NotBlank
	private String title;
}