package rs.pscode.pomodorofire.service.shared;

public class RegisterUserInit {
	private final String userName;
	private final String email;

	public RegisterUserInit(String userName, String email) {
		super();
		this.userName = userName;
		this.email = email;
	}

	public String getUserName() {
		return userName;
	}

	public String getEmail() {
		return email;
	}
}
