package de.dhbw.simplesurvey.payload.response;

import java.util.List;

import de.dhbw.simplesurvey.models.Survey;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SurveyListResponse {
	private List<Survey> surveys;
}