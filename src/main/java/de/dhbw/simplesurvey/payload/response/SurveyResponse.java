package de.dhbw.simplesurvey.payload.response;

import de.dhbw.simplesurvey.models.Survey;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SurveyResponse {
	private Survey survey;
}