package de.dhbw.simplesurvey.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.dhbw.simplesurvey.controllers.types.WebController;
import de.dhbw.simplesurvey.models.Answer;
import de.dhbw.simplesurvey.models.Question;
import de.dhbw.simplesurvey.models.Survey;
import de.dhbw.simplesurvey.payload.request.survey.EditSurveyRequest;
import de.dhbw.simplesurvey.payload.request.survey.SurveyRequest;
import de.dhbw.simplesurvey.payload.response.MessageResponse;
import de.dhbw.simplesurvey.payload.response.SurveyCreatedResponse;
import de.dhbw.simplesurvey.payload.response.SurveyListResponse;
import de.dhbw.simplesurvey.payload.response.SurveyResponse;
import de.dhbw.simplesurvey.repositories.AnswerRepository;
import de.dhbw.simplesurvey.repositories.QuestionRepository;
import de.dhbw.simplesurvey.repositories.SurveyRepository;
import de.dhbw.simplesurvey.repositories.UserRepository;
import de.dhbw.simplesurvey.types.ResponseType;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/survey")
public class SurveyController extends WebController {

	@Autowired
	SurveyRepository surveyRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	AnswerRepository answerRepository;

	@Autowired
	QuestionRepository questionRepository;

	@PostMapping("/create")
	public ResponseEntity<?> createSurvey(@Valid @RequestBody EditSurveyRequest createSurveyRequest) {
		if (isLoggedIn()) {
			Survey survey = new Survey(createSurveyRequest.getTitle(), createSurveyRequest.getDescription(), userRepository.findByName(getUsername()).get());
			surveyRepository.save(survey);
			return ResponseEntity.ok(new SurveyCreatedResponse(survey.getId()));
		} else {
			return ResponseEntity.badRequest().body(MessageResponse.getLoginError());
		}
	}

	@GetMapping("/getown")
	public ResponseEntity<?> getOwnSurveys() {
		if (isLoggedIn()) {
			List<Survey> surveys = surveyRepository.findByOwner(userRepository.findById(getUserId()).get());
			return ResponseEntity.ok(new SurveyListResponse(surveys));
		}
		return ResponseEntity.badRequest().body(MessageResponse.getLoginError());
	}

	@PostMapping("/getbyid")
	public ResponseEntity<?> getSurvey(@Valid @RequestBody SurveyRequest getSurveyRequest) {
		Survey survey = surveyRepository.findById(getSurveyRequest.getSurveyId()).get();
		return ResponseEntity.ok(new SurveyResponse(survey));
	}

	@PostMapping("/editsurvey")
	public ResponseEntity<?> editSurvey(@Valid @RequestBody EditSurveyRequest editSurveyRequest) {
		if (isLoggedIn()) {
			Survey survey = surveyRepository.findById(editSurveyRequest.getSurveyId()).get();
			if (!survey.isOwnedBy(getUsername())) {
				return ResponseEntity.badRequest().body(MessageResponse.getSecurityError());
			}

			if (hasAnswers(survey)) {
				return ResponseEntity.badRequest().body(new MessageResponse.MessageResponseBuilder().message("Survey already has answers. It is therefore not possible to change it.").type(ResponseType.ERROR).build());
			}

			survey.setTitle(editSurveyRequest.getTitle());
			survey.setDescription(editSurveyRequest.getDescription());
			surveyRepository.save(survey);
			return ResponseEntity.ok(new SurveyCreatedResponse(survey.getId()));
		}
		return ResponseEntity.badRequest().body(MessageResponse.getLoginError());
	}

	private boolean hasAnswers(Survey survey) {
		List<Question> questions = questionRepository.findBySurvey(survey);

		for (Question question : questions) {
			if (!answerRepository.findByQuestion(question).isEmpty()) {
				return true;
			}
		}

		return false;
	}

	@PostMapping("/deletesurvey")
	public ResponseEntity<?> deleteSurvey(@Valid @RequestBody SurveyRequest deleteSurveyRequest) {
		if (isLoggedIn()) {
			Survey survey = surveyRepository.findById(deleteSurveyRequest.getSurveyId()).get();
			if (!survey.isOwnedBy(getUsername())) {
				return ResponseEntity.badRequest().body(MessageResponse.getSecurityError());
			}

			List<Question> questions = questionRepository.findBySurvey(survey);
			for (Question question : questions) {
				List<Answer> answers = answerRepository.findByQuestion(question);
				for (Answer answer : answers) {
					answerRepository.delete(answer);
				}
				questionRepository.delete(question);
			}
			surveyRepository.delete(survey);

			return ResponseEntity.ok().body(new MessageResponse.MessageResponseBuilder().message("Survey has been deleted.").type(ResponseType.SUCCESS).build());
		}
		return ResponseEntity.badRequest().body(MessageResponse.getLoginError());
	}

}
