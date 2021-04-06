package de.dhbw.simplesurvey.payload.response;

import java.util.List;

import de.dhbw.simplesurvey.models.Answer;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AnswerListResponse {
	private List<Answer> answers;
}