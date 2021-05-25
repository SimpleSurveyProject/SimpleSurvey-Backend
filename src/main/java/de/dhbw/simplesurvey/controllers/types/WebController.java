package de.dhbw.simplesurvey.controllers.types;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import de.dhbw.simplesurvey.security.services.UserDetailsImpl;

public class WebController {

	public boolean isLoggedIn() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if ((authentication != null) && (!(authentication instanceof AnonymousAuthenticationToken))) {
				return true;
		}
		return false;
	}

	public int getUserId() {
		if (!isLoggedIn()) {
			throw new IllegalAccessError("user is not logged in");
		}
		return getUser().getId();
	}
	
	public String getUsername() {
		if (!isLoggedIn()) {
			throw new IllegalAccessError("user is not logged in");
		}
		return getUser().getUsername();
	}
	
	private UserDetailsImpl getUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return (UserDetailsImpl) authentication.getPrincipal();
	}


}
