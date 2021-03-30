package de.dhbw.simplesurvey.payload.request;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class GetAnswersRequest {
	@NotBlank
	private int questionId;
}