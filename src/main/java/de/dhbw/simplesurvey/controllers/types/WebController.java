package de.dhbw.simplesurvey.controllers.types;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import de.dhbw.simplesurvey.security.services.UserDetailsImpl;
import lombok.Getter;

@Getter
public class WebController {

	private boolean isLoggedIn = false;
	private String username = null;

	public WebController() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			isLoggedIn = true;

			UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
			username = user.getUsername();
		}
	}
}
