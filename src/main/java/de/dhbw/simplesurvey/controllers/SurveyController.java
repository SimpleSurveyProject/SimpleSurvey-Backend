package de.dhbw.simplesurvey.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.dhbw.simplesurvey.models.Question;
import de.dhbw.simplesurvey.models.Survey;
import de.dhbw.simplesurvey.payload.request.survey.EditSurveyRequest;
import de.dhbw.simplesurvey.payload.request.survey.GetSurveyRequest;
import de.dhbw.simplesurvey.payload.response.MessageResponse;
import de.dhbw.simplesurvey.payload.response.SurveyCreatedResponse;
import de.dhbw.simplesurvey.payload.response.SurveyListResponse;
import de.dhbw.simplesurvey.payload.response.SurveyResponse;
import de.dhbw.simplesurvey.repositories.AnswerRepository;
import de.dhbw.simplesurvey.repositories.QuestionRepository;
import de.dhbw.simplesurvey.repositories.SurveyRepository;
import de.dhbw.simplesurvey.repositories.UserRepository;
import de.dhbw.simplesurvey.security.services.UserDetailsImpl;
import de.dhbw.simplesurvey.types.ResponseType;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/survey")
public class SurveyController {

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
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
			String username = user.getUsername();
			Survey survey = new Survey(createSurveyRequest.getTitle(), createSurveyRequest.getDescription(), userRepository.findByName(username).get());
			surveyRepository.save(survey);
			return ResponseEntity.ok(new SurveyCreatedResponse(survey.getId()));
		} else {
			return ResponseEntity.badRequest().body(MessageResponse.getLoginError());
		}

	}

	@GetMapping("/getown")
	public ResponseEntity<?> getOwnSurveys() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
			List<Survey> surveys = surveyRepository.findByOwner(userRepository.findById(user.getId()).get());
			return ResponseEntity.ok(new SurveyListResponse(surveys));
		} else {
			return ResponseEntity.badRequest().body(MessageResponse.getLoginError());
		}
	}

	@PostMapping("/getbyid")
	public ResponseEntity<?> getSurvey(@Valid @RequestBody GetSurveyRequest getSurveyRequest) {
		Survey survey = surveyRepository.findById(getSurveyRequest.getSurveyId()).get();
		return ResponseEntity.ok(new SurveyResponse(survey));

	}

	@PostMapping("/editsurvey")
	public ResponseEntity<?> editSurvey(@Valid @RequestBody EditSurveyRequest editSurveyRequest) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
			String username = user.getUsername();

			Survey survey = surveyRepository.findById(editSurveyRequest.getSurveyId()).get();
			if (!survey.isOwnedBy(username)) {
				return ResponseEntity.badRequest().body(MessageResponse.getSecurityError());
			}

			if(hasAnswers(survey)) {
				return ResponseEntity.badRequest().body(new MessageResponse.MessageResponseBuilder().message("Survey already has answers. It is therefore not possible to change it.").type(ResponseType.ERROR).build());
			}

			survey.setTitle(editSurveyRequest.getTitle());
			survey.setDescription(editSurveyRequest.getDescription());
			surveyRepository.save(survey);
			return ResponseEntity.ok(new SurveyCreatedResponse(survey.getId()));
		} else {
			return ResponseEntity.badRequest().body(MessageResponse.getLoginError());
		}
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

}
