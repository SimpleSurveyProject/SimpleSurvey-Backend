package de.dhbw.simplesurvey.payload.request;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class GetSurveyRequest {
	@NotBlank
	private int surveyId;
}