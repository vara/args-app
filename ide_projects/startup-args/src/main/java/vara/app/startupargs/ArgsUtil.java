package vara.app.startupargs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vara.app.startupargs.exceptions.BadDeclarationParameter;

/**
 * User: Grzegorz (vara) Warywoda
 * Date: 2010-06-18
 * Time: 05:35:43
 */
public final class ArgsUtil {
	private static final Logger log = LoggerFactory.getLogger(ArgsUtil.class);

	/**
	 * Prefix char for marking a symbols. One prefix char used like as short symbol.
	 * Two prefix chars as used like as long symbol<br>
	 */
	private static char prefixChar = '-';

	/**
	 * Variable used to detection long symbols. 
	 */
	private static String doublePrefixChar = ""+prefixChar+prefixChar;

	/**
	 * Variable for decide whether symbols should be store with prefix.
	 */
	private static boolean storeWithoutPrefix = false;

	/**
	 * For special arguments.
	 * Add ability to concatenate symbols with value(s) separated this char.
	 * --symbol=value
	 */
	private static String separatorForCombinedArg = "=";

	/**
	 * Global variable used for detection values concatenated with special characters.<br>
	 *  Default char is comma.
	 */
	private static String argumentValuesSeparator = ",";

	private static String disableChar = "~";

	/**
	 *  Get a string object used as marker for exclude option parameter.
	 * In other words this string object can disable parameter.
	 *
	 * @return
	 */
	public static String getDisableChar() {
		return disableChar;
	}

	static {

		//@Author Grzegorz (vara) Warywoda 2010-08-27 04:04:27 CEST
		//New feature for store date without prefix. Enable by default
		ArgsUtil.setStoreWithoutPrefix(true);
	}
	/**
	 * Method for proper initialization/declaration symbols. Method strip or add prefixes,
	 *  this is depend from type of mode described by <code>storeWithoutPrefix</code> variable.
	 *
	 * @see ArgsUtil#storeWithoutPrefix
	 *
	 * @param symbol
	 * @param isShort
	 * @return
	 * @throws BadDeclarationParameter
	 */
	public static String check(String symbol,boolean isShort) throws BadDeclarationParameter {

		if(log.isDebugEnabled())log.debug("Check symbol '{}' willBeShort: {}",symbol,isShort);

		if(symbol == null){
			if(isShort) return null;
			else throw new NullPointerException("Symbol described parameter must be non-null !");
		}

		if(symbol.isEmpty()){
			if(isShort) return null;
			else throw new BadDeclarationParameter("Symbol described parameter can't be empty ( length != 0 ) !");
		}
		symbol = symbol.trim();

		int prefixCount = countOfPrefixes(symbol,prefixChar);

		int neededPref = 0; //we not needed prefixes by default  

		if(!storeWithoutPrefix) neededPref = isShort ? 1 : 2;

		if(prefixCount != neededPref){

			if(log.isDebugEnabled())log.debug("Actual Prefixes : {} Need: {}",prefixCount,neededPref);

			if(prefixCount>neededPref){
				int begin = prefixCount-neededPref;
				symbol = symbol.substring(begin);

				if(log.isDebugEnabled())log.debug("Substring from : {} result {}",begin,symbol);

			}else{
				int diff = (neededPref-prefixCount);
				symbol =  (diff == 1 ? prefixChar : doublePrefixChar) +symbol;

				if(log.isDebugEnabled())log.debug("Is less needed pref : {} result {}",diff,symbol);
			}
		}

		return symbol;
	}

	/**
	 * Method for checking compatibility current mode with format of considered the symbol.
	 * Method should be use in runtime for command line input arguments.
	 *
	 * @param symbol command line input argument
	 * @return symbol in proper format
	 *
	 * @see #isStoreWithoutPrefix()
	 */
	public static String recheck(String symbol){

		if(storeWithoutPrefix){
			int prefixCount = countOfPrefixes(symbol,prefixChar);
			if(prefixCount != 0){
				symbol = symbol.substring(prefixCount);
				if(log.isTraceEnabled()){
					log.trace("On storeWithoutPrefix mode, detected {} prefixe(s) .",prefixCount);
					log.trace("New circumcised symbol is '{}'",symbol);
				}
			}
		}

		return symbol;
	}

	/**
	 *
	 * @return
	 */
	public static char getPrefixChar() {
		return prefixChar;
	}

	/**
	 * 
	 * @param prefixChar
	 */
	public static void setPrefixChar(char prefixChar) {
		ArgsUtil.prefixChar = prefixChar;
	}

	/**
	 * Check whether the parameter is short symbol.
	 *
	 * @param symbol
	 * @return true if parameter is short symbol otherwise return false
	 */
	public static boolean isShortSymbol(String symbol){
		return countOfPrefixes(symbol,prefixChar) == 1;
	}

	/**
	 * Check whether the parameter is long symbol.
	 *
	 * @param symbol
	 * @return true if parameter is long symbol otherwise return false
	 */
	public static boolean isLongSymbol(String symbol){
		return countOfPrefixes(symbol,prefixChar) == 2;
	}


	/**
	 * Check whether the parameter is the symbol.
	 * If test parameter start with one {@link #prefixChar}
	 * then it is recognize as symbol.<br>
	 *
	 * @param symbol object to test
	 * @return true if this string is symbol
	 */
	public static boolean isSymbolParameter(String symbol){
		//@Author Grzegorz (vara) Warywoda 2010-06-21 09:02:35 CEST
		//TODO: protect against negative numbers
		if(symbol != null && !symbol.isEmpty())
			return  symbol.charAt(0) == prefixChar;
		return false;
	}

	/**
	 * Detect number of pattern characters placed on the first place in the string.
	 *
	 * @param str  tested string
	 * @param prefix pattern character
	 * @return number of prefixes in string
	 */
	private static int countOfPrefixes(String str,char prefix){
		int counter = 0;
		for(int i=0 ;i<str.length();i++){
			if(str.charAt(i)==prefix) counter++;
			else break;
		}
		return counter;
	}

	/**
	 *
	 * @return
	 */
	public static boolean isStoreWithoutPrefix() {
		return storeWithoutPrefix;
	}

	/**
	 *
	 * @param storeWithoutPrefix
	 */
	public static void setStoreWithoutPrefix(boolean storeWithoutPrefix) {
		if(ArgsUtil.storeWithoutPrefix != storeWithoutPrefix){
			ArgsUtil.storeWithoutPrefix = storeWithoutPrefix;
		}
	}

	/**
	 * Get global value used for detection values concatenated with special characters.<br>
	 * Default character is comma.
	 * @return String values separator
	 */
	public static String getArgumentValuesSeparator() {
		return argumentValuesSeparator;
	}

	/**
	 *
	 * @param separatorForCombinedArg
	 */
	public static void setArgumentValuesSeparator(String separatorForCombinedArg) {
		if(ArgsUtil.argumentValuesSeparator != argumentValuesSeparator){
			ArgsUtil.argumentValuesSeparator = argumentValuesSeparator;
		}
	}

	/**
	 *
	 * @return
	 */
	public static String getSeparatorForCombinedArg() {
		return separatorForCombinedArg;
	}

	/**
	 *
	 * @param separatorForCombinedArg
	 */
	public static void setSeparatorForCombinedArg(String separatorForCombinedArg) {
		if(ArgsUtil.separatorForCombinedArg != separatorForCombinedArg)
			ArgsUtil.separatorForCombinedArg = separatorForCombinedArg;
	}

	/**
	 *
	 * @param argument
	 * @return
	 */
	public static boolean isCombinedArgument(String argument){
		if(argument != null && !argument.isEmpty() ){
			return argument.indexOf(separatorForCombinedArg) != -1;
		}
		return false;
	}

	/**
	 *
	 * @param rawCombinedArgument
	 * @return
	 */
	public static CombinedArgument toCombinedArgObject(String rawCombinedArgument){
		CombinedArgument ca = null;
		if(rawCombinedArgument != null && !rawCombinedArgument.isEmpty()){
			int index = rawCombinedArgument.indexOf(separatorForCombinedArg);
			if(index != -1){
				String args = rawCombinedArgument.substring(index+1);
				String rawSymbol = rawCombinedArgument.substring(0,index);

				ca = new CombinedArgument(rawSymbol,args);
			}

		}
		return ca;
	}

	/**
	 * For encapsulate data. Useful for quick access to divided "combined-arguments"
	 * (symbol and his arguments).
	 *
	 */
	public static class CombinedArgument{
		private String rawSymbol;
		private String arguments;

		public CombinedArgument(String rawSymbol,String arguments) {
			this.arguments = arguments;
			this.rawSymbol = rawSymbol;
		}

		public String getArguments() {
			return arguments;
		}

		public String getRawSymbol() {
			return rawSymbol;
		}
	}
}
