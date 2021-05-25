package de.dhbw.simplesurvey.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.dhbw.simplesurvey.controllers.types.WebController;
import de.dhbw.simplesurvey.models.Answer;
import de.dhbw.simplesurvey.payload.request.survey.AddAnswerRequest;
import de.dhbw.simplesurvey.payload.request.survey.GetAnswersRequest;
import de.dhbw.simplesurvey.payload.response.AnswerListResponse;
import de.dhbw.simplesurvey.payload.response.MessageResponse;
import de.dhbw.simplesurvey.repositories.AnswerRepository;
import de.dhbw.simplesurvey.repositories.QuestionRepository;
import de.dhbw.simplesurvey.repositories.UserRepository;
import de.dhbw.simplesurvey.types.ResponseType;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/answer")
public class AnswerController extends WebController {
	@Autowired
	AnswerRepository answerRepository;

	@Autowired
	QuestionRepository questionRepository;

	@Autowired
	UserRepository userRepository;
	
	@PostMapping("/add")
	public ResponseEntity<?> addQuestion(@Valid @RequestBody AddAnswerRequest[] addAnswerRequests) {
		if(isLoggedIn()) {
			for (AddAnswerRequest addAnswerRequest : addAnswerRequests) {
				answerRepository.save(new Answer(addAnswerRequest.getText(),
						questionRepository.findById(addAnswerRequest.getQuestionId()).get(),
						userRepository.findByName(getUsername()).get()));
			}

			return ResponseEntity.ok(new MessageResponse.MessageResponseBuilder().message("question added successfully").type(ResponseType.SUCCESS).build());
		} else {
			return ResponseEntity.badRequest().body(MessageResponse.getLoginError());
		}
	}

	@PostMapping("/get")
	public ResponseEntity<?> getAnswersForQuestion(@Valid @RequestBody GetAnswersRequest getAnswersRequest) {
		if(isLoggedIn()) {
			if (questionRepository.findById(getAnswersRequest.getQuestionId()).isPresent()) {
				if (questionRepository.findById(getAnswersRequest.getQuestionId()).get().getSurvey().getOwner()
						.getId() == userRepository.findByName(getUsername()).get().getId()) {
					List<Answer> answers = answerRepository
							.findByQuestion(questionRepository.findById(getAnswersRequest.getQuestionId()).get());
					return ResponseEntity.ok(new AnswerListResponse(answers));
				}
				return ResponseEntity.badRequest().body(MessageResponse.getLoginError());
			}
			return ResponseEntity.badRequest().body(new MessageResponse.MessageResponseBuilder().message("question not found").type(ResponseType.ERROR).build());
		}
		return ResponseEntity.badRequest().body(MessageResponse.getLoginError());
	}
}