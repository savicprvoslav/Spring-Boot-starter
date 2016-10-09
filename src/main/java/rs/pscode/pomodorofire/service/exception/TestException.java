package rs.pscode.pomodorofire.service.exception;

public class TestException extends AbstractException {

	public TestException(String message, Throwable cause) {
		super(message, "TestException", cause);
	}

	private static final long serialVersionUID = -1451835022162714730L;

}
