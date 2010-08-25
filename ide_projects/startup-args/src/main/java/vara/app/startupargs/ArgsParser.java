package vara.app.startupargs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vara.app.startupargs.base.DefaultParameter;
import vara.app.startupargs.base.Parameters;
import vara.app.startupargs.exceptions.CatchOnException;
import vara.app.startupargs.exceptions.OptionNotFoundException;
import vara.app.startupargs.exceptions.ValidationObjectException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
* User: Grzegorz (vara) Warywoda
* Date: 2010-05-12
* Time: 15:16:11
*/

/**
 * This class is a manager for all input command line arguments.
 * It is responsible for validation, delivered exception and parsing arguments.
 */
public class ArgsParser {
	private static final Logger log = LoggerFactory.getLogger(ArgsParser.class);

	private static List<CatchOnException> exceptionCatchers = new ArrayList<CatchOnException>();

	/**
	 * For special arguments.
	 * Add ability to concatenate symbols with value(s) separated this char.
	 * --symbol=value
	 */
	private static char separatorForCombinedArg = '=';

	/**
	 * This class tell to parser what to do when exception will be throw.
	 * Regardless of your choice, all exception will be redirect to registered
	 * listeners through <code>CatchOnException<code> object.
	 * 
	 * @see vara.app.startupargs.exceptions.CatchOnException
	 *
	 */
	public enum ExceptionBehaviour{
		/**
		 * Do nothing
		 */
		IGNORE,
		/**
		 * When exception will be caught by parser perform as
		 */
		THROW,
		/**
		 * When exception will be caught by parser exit from program
		 */
		EXIT;

		//TODO: For inner cases. "Throw" for only internal validation exceptions.  

	}

	static {
		//check if the user didn't specify a variable
		//NOTE:not checked/tested
		String charForSeparator = System.getProperty("cmdline.symbolseparator","");

		if(!charForSeparator.isEmpty() && charForSeparator.charAt(0) != separatorForCombinedArg){
			separatorForCombinedArg = charForSeparator.charAt(0);
			log.info("User define new separator '{}' for combined input arguments.",separatorForCombinedArg);
		}
	}

	/**
	 * Variable is responsible for action behavior when any runtime exception will be throw
	 * @see vara.app.startupargs.ArgsParser.ExceptionBehaviour 
	 */
	private static ExceptionBehaviour exceptionBehaviour = ExceptionBehaviour.EXIT;

	/**
	 * Get information of current set object  <code>ExceptionBehaviour<code>
	 * Tell to parser what to do when exception will be generated.
	 * Default behaviour is <code>ExceptionBehaviour.EXIT<code>
	 *
	 * @return type of <code>ExceptionBehaviour<code> object
	 * @see #exceptionBehaviour
	 * @see vara.app.startupargs.ArgsParser.ExceptionBehaviour
	 *
	 */
	public static ExceptionBehaviour getExceptionBehaviour() {
		return exceptionBehaviour;
	}

	/**
	 * Tell to parser what to do when exception will be generated.
	 * Default behaviour is <code>ExceptionBehaviour.EXIT<code>
	 *
	 * @param exceptionBehaviour
	 * @see #exceptionBehaviour
	 * @see vara.app.startupargs.ArgsParser.ExceptionBehaviour 
	 */
	public static void setExceptionBehaviour(ExceptionBehaviour exceptionBehaviour) {
		ArgsParser.exceptionBehaviour = exceptionBehaviour;
	}

	/**
	 * Set hook for all thrown exceptions by parser. In other words, allow
	 * to monitoring exceptions while parsing arguments.
	 *
	 * @param catcher object to correspond with parser
	 */
	public static void setCatchOnException(CatchOnException catcher){
		
		if(!exceptionCatchers.contains(catcher)){
			exceptionCatchers.add(catcher);
		}else{
			log.warn("Cant add CatchOnException object ! Object has been added before");
		}
	}

	public static void removeCatchOnException(CatchOnException catcher){
		//TODO : Add log inf
		exceptionCatchers.remove(catcher);
	}

	/**
	 * Function for handling eny runtime exception and it is responsible
	 * to deliver to registered <code>CatchOnException</code>. Action behaviour
	 * is mostly depend of variable <code> exceptionBehaviour </code>
	 *
	 *
	 * @param exc exception has been thrown
	 *
	 * @see #exceptionBehaviour
	 * @see vara.app.startupargs.exceptions.CatchOnException
	 */
	private static void deliverCaughtException(RuntimeException exc){
		if(!exceptionCatchers.isEmpty()){
			for (CatchOnException exceptionCatcher : exceptionCatchers) {
				exceptionCatcher.caughtException(exc);
			}
		}

		if(exceptionBehaviour == ExceptionBehaviour.THROW){
			throw  exc;
		}
		if(exceptionBehaviour == ExceptionBehaviour.EXIT){
			log.error("",exc);
			//TODO: Check for special error code
			System.exit(1);
		}
	}

	/**
	 * Helper class for encapsulating information about
	 * layout of parameters in main container.
	 */
	private static class ParameterEntryHelper{
		final int index;
		final String symbol;
		final int nParameters;

		private ParameterEntryHelper(int fromIndex, int numOfParams,String symbol) {
			this.index = fromIndex;
			this.symbol = symbol;
			this.nParameters = numOfParams;
		}

		public String getSymbol() {
			return symbol;
		}

		public int getIndex() {
			return index;
		}

		public int getNumberOfParameters(){
			return nParameters;
		}

		@Override
		public String toString() {
			return "sym:"+symbol+" from:"+ index +" numOfParams:"+ nParameters;
		}
	}

	/**
	 * Create table with symbol indexes.
	 *
	 * @param args container with all raw input arguments
	 * @return table of indexes
	 */
	private static int [] getSymbolIndexes(List<String> args){

		int len = args.size();

		if(len == 0) return new int[0];

		int [] symbols = new int [len];
		int offset=0;
		for(int currentIndex = 0; currentIndex<len;currentIndex++){
			String pretenderToSymbolParameter = args.get(currentIndex);
			if(ArgsUtil.isSymbolParameter(pretenderToSymbolParameter)){
				symbols[offset++] = currentIndex;
			}
		}

		return Arrays.copyOf(symbols,offset);
	}

	/**
	 * Create list with
	 *
	 * @param args container with all raw input arguments
	 * @return container with <code>ParameterEntryHelper</code> objects
	 */
	private static List<ParameterEntryHelper> createParameterEntryHelpers(List<String> args){

		List<ParameterEntryHelper> parameterHelper = new ArrayList();

		int [] symbolIndexes = getSymbolIndexes(args);
		int iArguments = args.size();
		int length = symbolIndexes.length;

		for (int i = 0; length>i;i++) {

			int symbolIndex = symbolIndexes[i];
			String symbol = args.get(symbolIndex);
			//If you insert one prefix char '-' then stop parsing args
			//TODO:fix me
			if(symbol.length() == 1){
				if(log.isDebugEnabled()) log.debug("Detected break-char '-' on {} index.", symbolIndex);
				break;
			}

			int endIndex = i+1<length ? symbolIndexes[i+1] : iArguments;
			int numOfParameters= endIndex - (symbolIndex+1);

			parameterHelper.add(new ParameterEntryHelper(symbolIndex,numOfParameters, symbol));
		}

		return parameterHelper;
	}

	public static void parseParameters(List<String> args){

		//make 100% sure that indexes on this list can't be changed
		args = Collections.unmodifiableList(args);

		List<ParameterEntryHelper> helpers = createParameterEntryHelpers(args);

		for (ParameterEntryHelper entryHelper : helpers) {

			String symbol = entryHelper.symbol;

			//Special argument consists of symbol and value(s) separated by charSeparator
			int equalsPos = symbol.indexOf(separatorForCombinedArg);

			String specialArg = null;
			if ( equalsPos != -1 ) {
				specialArg = symbol.substring(equalsPos+1);
				symbol = symbol.substring(0,equalsPos);
			}

			DefaultParameter optionHandler = (DefaultParameter)Parameters.getParameter(symbol);
			if (optionHandler != null){

				if(log.isDebugEnabled())log.debug("Found parameter class {}. Expected number of params are :{}",
																optionHandler.getClass().getSuperclass().getName(),
																optionHandler.getOptionValuesLength().toString2());

				List<String> subList = null;
				if(specialArg == null){
					if(entryHelper.nParameters>0){
						int from = entryHelper.index+1;
						subList = args.subList(from,from + entryHelper.nParameters);
					}else{
						subList = Collections.emptyList();
					}
				}else{
					subList = Arrays.asList(specialArg);
				}

				try {
					optionHandler.handleOption(subList.toArray(new String[0]));

				} catch(ValidationObjectException exc){		deliverCaughtException(exc); }

				if(optionHandler.isExit()){
					System.exit(0);
				}

			}else
				deliverCaughtException(new OptionNotFoundException(symbol,"Unrecognized parameter "+symbol));
		}
	}

	public static void parseParameters(String[] args){
		parseParameters(Arrays.asList(args));
	}

//	private static String optionValuesToString(String[] v,String separator){
//
//		StringBuffer sb = new StringBuffer();
//		int n = v != null ? v.length:0;
//		for (int i=0; i<n; i++){
//			sb.append(v[i] );
//			sb.append( separator );
//		}
//		return sb.toString();
//	}
}
