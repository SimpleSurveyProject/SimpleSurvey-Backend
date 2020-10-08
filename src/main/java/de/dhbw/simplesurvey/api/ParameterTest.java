package de.dhbw.simplesurvey.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.dhbw.simplesurvey.types.Survey;

@RestController
public class ParameterTest {

	@RequestMapping("/test")
	public Survey returnParameter(@RequestParam String parameter) {
		return new Survey(parameter);
	}
	
}
