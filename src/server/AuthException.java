package server;

@SuppressWarnings("serial")
public class AuthException extends Exception {

	public AuthException() {
		return;
	}

	public AuthException(String message) {
		super(message);
	}

	public AuthException(Throwable throwable) {
		super(throwable);
	}

	public AuthException(String message, Throwable throwable) {
		super(message, throwable);
	}

}