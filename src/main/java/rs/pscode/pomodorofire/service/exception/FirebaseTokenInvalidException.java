package rs.pscode.pomodorofire.service.exception;

import org.springframework.security.authentication.BadCredentialsException;

public class FirebaseTokenInvalidException extends BadCredentialsException {

	public FirebaseTokenInvalidException(String msg) {
		super(msg);
	}

	private static final long serialVersionUID = 789949671713648425L;

}
