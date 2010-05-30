package vara.app.startupargs.Exception;

/**
 * User: Grzegorz (vara) Warywoda
 * Date: 2010-05-30
 * Time: 01:12:28
 */
public class ParseOptionException extends RuntimeException{
	public ParseOptionException() {
	}

	public ParseOptionException(Throwable cause) {
		super(cause);
	}

	public ParseOptionException(String message) {
		super(message);
	}

	public ParseOptionException(String message, Throwable cause) {
		super(message, cause);
	}
}
