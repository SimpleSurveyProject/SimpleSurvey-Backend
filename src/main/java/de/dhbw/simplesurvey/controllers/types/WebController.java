package de.dhbw.simplesurvey.controllers.types;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import de.dhbw.simplesurvey.security.services.UserDetailsImpl;

public class WebController {

	public String getUsername() {
		if (!isLoggedIn()) {
			throw new IllegalAccessError("user is not logged in");
		}

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
		return user.getUsername();

	}

	public boolean isLoggedIn() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if ((authentication != null) && (!(authentication instanceof AnonymousAuthenticationToken))) {
				return true;
		}
		return false;
	}

}
