package de.dhbw.simplesurvey.api;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SurveyList {

	@RequestMapping("/survey")
	public List<String> getAll() {
		return Arrays.asList("1", "2", "3");
	}

}
