package vara.app.startupargs.exceptions;

/**
 * User: Grzegorz (vara) Warywoda
 * Date: 2010-05-30
 * Time: 01:12:28
 */
public abstract class ParseOptionException extends Exception{

	public String getCorruptedOption() {
		return option;
	}

	private String option;

	public ParseOptionException(String option) {
		super();
		this.option = option;
	}

	public ParseOptionException(Throwable cause) {
		super(cause);
		this.option = option;
	}

	public ParseOptionException(String option,String message) {
		super(message);
		this.option = option;
	}

	public ParseOptionException(String option,String message, Throwable cause) {
		super(message, cause);
		this.option = option;
	}
}
