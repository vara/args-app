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
    String getOption();

	/**
	 * Get short representation of option.
	 *
	 * @return optional symbol
	 */
	String getShortOption();

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
	 * @param input parameters
	 */
    void handleOption(String[] values);
}
