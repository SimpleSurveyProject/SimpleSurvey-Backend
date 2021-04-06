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
import de.dhbw.simplesurvey.payload.request.survey.AddQuestionRequest;
import de.dhbw.simplesurvey.payload.request.survey.GetQuestionsRequest;
import de.dhbw.simplesurvey.payload.response.MessageResponse;
import de.dhbw.simplesurvey.payload.response.QuestionListResponse;
import de.dhbw.simplesurvey.repositories.QuestionRepository;
import de.dhbw.simplesurvey.repositories.SurveyRepository;
import de.dhbw.simplesurvey.repositories.UserRepository;
import de.dhbw.simplesurvey.security.services.UserDetailsImpl;
import de.dhbw.simplesurvey.types.ResponseType;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/question")
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
					questionRepository.save(new Question(addQuestionRequest.getText(), surveyRepository.findById(addQuestionRequest.getSurveyId()).get()));
				}
			}
			return ResponseEntity.ok(new MessageResponse("question added successfully", ResponseType.SUCCESS));
		}
		return ResponseEntity.badRequest().body(MessageResponse.getLoginError());
	}

	@PostMapping("/get")
	public ResponseEntity<?> getQuestionsForSurvey(@Valid @RequestBody GetQuestionsRequest getQuestionsRequest) {
		if (surveyRepository.findById(getQuestionsRequest.getSurveyId()).isPresent()) {
			List<Question> questions = questionRepository.findBySurvey(surveyRepository.findById(getQuestionsRequest.getSurveyId()).get());
			return ResponseEntity.ok(new QuestionListResponse(questions));
		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("survey not found", ResponseType.ERROR));
		}
	}
}