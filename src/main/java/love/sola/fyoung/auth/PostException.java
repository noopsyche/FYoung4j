package love.sola.fyoung.auth;

/**
 * ***********************************************
 * Created by Sola on 2014/8/20.
 * Don't modify this source without my agreement
 * ***********************************************
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
