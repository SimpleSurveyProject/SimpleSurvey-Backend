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

import de.dhbw.simplesurvey.models.Answer;
import de.dhbw.simplesurvey.payload.request.AddAnswerRequest;
import de.dhbw.simplesurvey.payload.request.GetAnswersRequest;
import de.dhbw.simplesurvey.payload.response.AnswerListResponse;
import de.dhbw.simplesurvey.payload.response.MessageResponse;
import de.dhbw.simplesurvey.repositories.AnswerRepository;
import de.dhbw.simplesurvey.repositories.QuestionRepository;
import de.dhbw.simplesurvey.repositories.UserRepository;
import de.dhbw.simplesurvey.security.services.UserDetailsImpl;
import de.dhbw.simplesurvey.types.ResponseType;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/answer")
public class AnswerController {
	@Autowired
	AnswerRepository answerRepository;

	@Autowired
	QuestionRepository questionRepository;

	@Autowired
	UserRepository userRepository;

	@PostMapping("/add")
	public ResponseEntity<?> addQuestion(@Valid @RequestBody AddAnswerRequest[] addAnswerRequests) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
			String username = user.getUsername();

			for (AddAnswerRequest addAnswerRequest : addAnswerRequests) {
				answerRepository.save(new Answer(addAnswerRequest.getText(), questionRepository.findById(addAnswerRequest.getQuestionId()).get(), userRepository.findByName(username).get()));
			}
			return ResponseEntity.ok(MessageResponse.create("question added successfully", ResponseType.SUCCESS));
		}
		return ResponseEntity.badRequest().body(MessageResponse.getLoginError());
	}

	@PostMapping("/get")
	public ResponseEntity<?> getAnswersForQuestion(@Valid @RequestBody GetAnswersRequest getAnswersRequest) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
			String username = user.getUsername();
			if (questionRepository.findById(getAnswersRequest.getQuestionId()).isPresent()) {
				if (questionRepository.findById(getAnswersRequest.getQuestionId()).get().getSurvey().getOwner().getId() == userRepository.findByName(username).get().getId()) {
					List<Answer> answers = answerRepository.findByQuestion(questionRepository.findById(getAnswersRequest.getQuestionId()).get());
					return ResponseEntity.ok(new AnswerListResponse(answers));
				}
				return ResponseEntity.badRequest().body(MessageResponse.getLoginError());
			}
			return ResponseEntity.badRequest().body(new MessageResponse("question not found", ResponseType.ERROR));
		}
		return ResponseEntity.badRequest().body(MessageResponse.getLoginError());
	}
}