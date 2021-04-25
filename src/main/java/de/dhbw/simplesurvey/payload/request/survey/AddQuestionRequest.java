package de.dhbw.simplesurvey.payload.request.survey;

import javax.validation.constraints.NotBlank;

import de.dhbw.simplesurvey.models.Question.Styles;
import lombok.Data;

@Data
public class AddQuestionRequest {
	@NotBlank
	private Integer position;

	@NotBlank
	private Styles style;

	@NotBlank
	private String text;

	@NotBlank
	private int surveyId;
}