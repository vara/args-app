package vara.app.startupargs.base;

/**
 *
 * @author Grzegorz (vara) Warywoda
 */
public interface AbstractParameter {
    /**
	 * Get full representation of option.
	 *
     * @Return symbol name
     */
    String getSymbol();

	/**
	 * Get short representation of option.
	 *
	 * @return optional symbol
	 */
	String getShortSymbol();

    /*
     * @Return description for parameter
     */
    String getOptionDescription();

    /**
     * @return number of input parameters
     */
    int getOptionValuesLength();

	/**
	 * Method for handling input parameters.
	 *
	 * @param values table with input parameters
	 */
    void handleOption(String[] values);
}
