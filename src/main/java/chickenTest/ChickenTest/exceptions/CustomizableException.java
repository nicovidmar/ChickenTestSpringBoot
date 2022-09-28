package chickenTest.ChickenTest.exceptions;

public class CustomizableException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private String message;

	public CustomizableException() {

	}

	public CustomizableException(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
