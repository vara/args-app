package vara.app.startupargs.base;

import vara.app.startupargs.exceptions.ValidationObjectException;

/**
 *
 * @author Grzegorz (vara) Warywoda
 */
public interface AbstractParameter {
	/**
	 * Get full representation of this option.
	 *
	 * @return symbol name
	 */
	String getSymbol();

	/**
	 * Get short representation of this option.
	 *
	 * @return optional symbol
	 */
	String getShortSymbol();

	/**
	 * Get description for this parameter.
	 * Description should describing for what this is and what this have doing.
	 *
	 * @return description for for what it is
	 */
	String getOptionDescription();

	/**
	 * Get short descriptions how to use this option (etc. about type of parameters)
	 *
	 * @return description how to use this option
	 */
	String getOptionUsage();

	/**
	 * Get number of potential input parameters
	 *
	 * @return number of input parameters
	 */
	RangeNumber getOptionValuesLength();

	/**
	 * Method for handling input parameters.
	 *
	 * @param values table with input parameters
	 * @throws vara.app.startupargs.exceptions.ValidationObjectException
	 */
	void handleOption(String[] values) throws ValidationObjectException;

	/**
	 * Get separator for values specified for only current instance.<br>
	 * e.q. -parameter=val1:val2: ... <br>
	 * In this case separator should be ':' for proper separate values.
	 * <br>By default method return <code>null<code> value which means that,
	 * global separator will be used.
	 * @see vara.app.startupargs.ArgsUtil#getArgumentValuesSeparator()
	 *
	 * @return separator for values
	 */
	String getValueSeparator();
}
