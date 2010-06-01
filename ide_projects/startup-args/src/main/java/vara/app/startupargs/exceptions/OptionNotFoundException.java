package vara.app.startupargs.exceptions;

/**
 * User: Grzegorz (vara) Warywoda
 * Date: 2010-06-01
 * Time: 13:04:10
 */
public class OptionNotFoundException extends ParseOptionException{
	public OptionNotFoundException(Throwable cause) {
		super(cause);
	}

	public OptionNotFoundException(String option) {
		super(option);
	}

	public OptionNotFoundException(String option, String message) {
		super(option, message);
	}

	public OptionNotFoundException(String option, String message, Throwable cause) {
		super(option, message, cause);
	}
}
