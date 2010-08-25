package vara.app.startupargs.exceptions;

/**
 * User: Grzegorz (vara) Warywoda
 * Date: 2010-05-30
 * Time: 00:07:31
 */

/**
 * Interface usable for implementation a intercept
 * exceptions from arguments parser.
 *
 * @see vara.app.startupargs.ArgsParser
 * @see vara.app.startupargs.ArgsParser#setCatchOnException(CatchOnException)
 */
public interface CatchOnException {

	/**
	 * Every exception encountered while parsing arguments will be redirect trough this method.
	 *
	 * @param e type of exception that has been thrown
	 */
	void caughtException(Exception e);
}
