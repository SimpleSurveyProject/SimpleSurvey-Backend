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

import de.dhbw.simplesurvey.models.Survey;
import de.dhbw.simplesurvey.payload.request.CreateSurveyRequest;
import de.dhbw.simplesurvey.payload.response.MessageResponse;
import de.dhbw.simplesurvey.payload.response.SurveyListResponse;
import de.dhbw.simplesurvey.repositories.SurveyRepository;
import de.dhbw.simplesurvey.repositories.UserRepository;
import de.dhbw.simplesurvey.security.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/survey")
public class SurveyController {
	@Autowired
	SurveyRepository surveyRepository;
	@Autowired
	UserRepository userRepository;

	@PostMapping("/create")
	public ResponseEntity<?> createSurvey(@Valid @RequestBody CreateSurveyRequest createSurveyRequest) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
			String username = user.getUsername();
			Survey survey = new Survey(createSurveyRequest.getTitle(), createSurveyRequest.getDescription(),
					userRepository.findByName(username).get());
			surveyRepository.save(survey);
			return ResponseEntity.ok(new MessageResponse("Survey created successfully"));
		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Please login"));
		}

	}

	@PostMapping("/getown")
	public ResponseEntity<?> getOwnSurveys() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
			List<Survey> surveys = surveyRepository.findByOwner(user.getId());
			return ResponseEntity.ok(new SurveyListResponse(surveys));
		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Please login"));
		}
	}
}