package de.dhbw.simplesurvey.payload.response;

import de.dhbw.simplesurvey.types.ResponseType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageResponse {
	private String message;
	private ResponseType type;

	public static MessageResponse create(String message, ResponseType type) {
		return new MessageResponse(message, type);
	}

	public static MessageResponse getLoginError() {
		return new MessageResponse("please log in", ResponseType.ERROR);
	}
	
	public static MessageResponse getSecurityError() {
		return new MessageResponse("not authorized to perform this operation", ResponseType.ERROR);
	}
	
}