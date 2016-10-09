package rs.pscode.pomodorofire.service.exception;

public class AbstractException extends RuntimeException {

	private static final long serialVersionUID = 9004674664241996502L;

	private String key;

	public AbstractException(String message, String key, Throwable cause) {
		super(message, cause);

		this.key = key;
	}

	public String getKey() {
		return key;
	}

}
