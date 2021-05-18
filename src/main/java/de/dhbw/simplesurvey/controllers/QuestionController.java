package de.dhbw.simplesurvey.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.dhbw.simplesurvey.models.Question;
import de.dhbw.simplesurvey.models.Survey;
import de.dhbw.simplesurvey.payload.request.survey.AddQuestionRequest;
import de.dhbw.simplesurvey.payload.request.survey.GetOrClearQuestionsRequest;
import de.dhbw.simplesurvey.payload.response.MessageResponse;
import de.dhbw.simplesurvey.payload.response.QuestionListResponse;
import de.dhbw.simplesurvey.repositories.QuestionRepository;
import de.dhbw.simplesurvey.repositories.SurveyRepository;
import de.dhbw.simplesurvey.repositories.UserRepository;
import de.dhbw.simplesurvey.security.services.UserDetailsImpl;
import de.dhbw.simplesurvey.types.ResponseType;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/question")
public class QuestionController {
	
	@Autowired
	QuestionRepository questionRepository;
	
	@Autowired
	SurveyRepository surveyRepository;
	
	@Autowired
	UserRepository userRepository;

	@PostMapping("/add")
	public ResponseEntity<?> addQuestion(@Valid @RequestBody AddQuestionRequest[] addQuestionRequests) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
			String username = user.getUsername();

			for (AddQuestionRequest addQuestionRequest : addQuestionRequests) {
				if (surveyRepository.findById(addQuestionRequest.getSurveyId()).get().getOwner().getId() == userRepository.findByName(username).get().getId()) {
					questionRepository.save(new Question(addQuestionRequest.getPosition(), addQuestionRequest.getStyle(), addQuestionRequest.getText(), surveyRepository.findById(addQuestionRequest.getSurveyId()).get()));
				}
			}

			return ResponseEntity.ok(new MessageResponse.MessageResponseBuilder().message("question added successfully").type(ResponseType.SUCCESS).build());
		}
		return ResponseEntity.badRequest().body(MessageResponse.getLoginError());
	}

	@PostMapping("/get")
	public ResponseEntity<?> getQuestionsForSurvey(@Valid @RequestBody GetOrClearQuestionsRequest questionsRequest) {
		if (surveyRepository.findById(questionsRequest.getSurveyId()).isPresent()) {
			List<Question> questions = questionRepository.findBySurvey(surveyRepository.findById(questionsRequest.getSurveyId()).get());
			return ResponseEntity.ok(new QuestionListResponse(questions));
		} else {
			return ResponseEntity.badRequest().body(new MessageResponse.MessageResponseBuilder().message("survey not found").type(ResponseType.ERROR).build());
		}
	}

	@PostMapping("/clear")
	public ResponseEntity<?> clearQuestionsForSurvey(@Valid @RequestBody GetOrClearQuestionsRequest questionsRequest) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
		String username = user.getUsername();

		if (surveyRepository.findById(questionsRequest.getSurveyId()).isPresent()) {
			Survey survey = surveyRepository.findById(questionsRequest.getSurveyId()).get();
			if (!survey.isOwnedBy(username)) {
				return ResponseEntity.badRequest().body(MessageResponse.getSecurityError());
			}
			List<Question> questions = questionRepository.findBySurvey(survey);
			questionRepository.deleteAll(questions);

			return ResponseEntity.ok(new MessageResponse.MessageResponseBuilder().type(ResponseType.SUCCESS).build());
		} else {
			return ResponseEntity.badRequest().body(new MessageResponse.MessageResponseBuilder().message("survey not found for clear").type(ResponseType.ERROR).build());
		}
	}
}