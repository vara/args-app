package vara.app.startupargs.exceptions;

/**
 * User: Grzegorz (vara) Warywoda
 * Date: 2010-06-03
 * Time: 22:06:55
 */
public class BadDeclarationParameter extends RuntimeException{
	public BadDeclarationParameter() {
	}

	public BadDeclarationParameter(Throwable cause) {
		super(cause);
	}

	public BadDeclarationParameter(String message) {
		super(message);
	}

	public BadDeclarationParameter(String message, Throwable cause) {
		super(message, cause);
	}
}
