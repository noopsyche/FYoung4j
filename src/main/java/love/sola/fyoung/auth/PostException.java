package love.sola.fyoung.auth;

/**
 * @author Sola {@literal <dev@sola.love>}
 */
public class PostException extends RuntimeException {

	public PostException(Throwable cause) {
		super(cause);
	}

	public PostException(String message, Throwable cause) {
		super(message, cause);
	}

	public PostException(String message) {
		super(message);
	}

	public PostException() {
		super();
	}

}
