package vara.app.startupargs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vara.app.startupargs.exceptions.CatchOnException;
import vara.app.startupargs.base.DefaultParameter;
import vara.app.startupargs.base.Parameters;
import vara.app.startupargs.exceptions.OptionNotFoundException;
import vara.app.startupargs.exceptions.UnexpectedNumberOfArguments;
import vara.app.startupargs.exceptions.ValidationObjectException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
* User: Grzegorz (vara) Warywoda
* Date: 2010-05-12
* Time: 15:16:11
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
	 * listeners through 'CatchOnException' object.  
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
			log.info("User define new separator '{}' for input arguments.",separatorForCombinedArg);
		}
	}
	/**
	 * Get information of current set object  <code>ExceptionBehaviour<code>
	 *
	 * @return
	 */
	public static ExceptionBehaviour getExceptionBehaviour() {
		return exceptionBehaviour;
	}

	/**
	 * Tell to parser what to do when exception will be generated.
	 * Default behaviour is <code>ExceptionBehaviour.EXIT<code>
	 *
	 * @param exceptionBehaviour
	 */
	public static void setExceptionBehaviour(ExceptionBehaviour exceptionBehaviour) {
		ArgsParser.exceptionBehaviour = exceptionBehaviour;
	}

	private static ExceptionBehaviour exceptionBehaviour = ExceptionBehaviour.EXIT;

	public static void parseParameters(String[] args){
		parseParameters(Arrays.asList(args));
	}

	/**
	 *	Set hook for all thrown exceptions by parser.
	 *
	 * @param catcher
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

	public static void parseParameters(List<String> args){

		int iElements = args.size();

		if(log.isDebugEnabled() &&iElements!=0) log.debug("Prepare to parse input parameters");

		for (int i = 0; i < iElements; i++) {
			String pretenderToSymbolParam = args.get(i);

			if(!ArgsUtil.isSymbolParameter(pretenderToSymbolParam)){
				//If parameter flags not detected shift index
				if(log.isDebugEnabled())log.debug("Current parsing argument ({}) isn't a symbol, skip it.",pretenderToSymbolParam);
				continue;
			}

			//If you insert one prefix char '-' then stop parsing args 
			if(pretenderToSymbolParam.length() == 1){
				if(log.isDebugEnabled()) log.debug("Detected break char on {} index.",i);
				break;
			}

			//Special argument consists of symbol and value(s) separated by charSeparator
			int equalsPos = pretenderToSymbolParam.indexOf(separatorForCombinedArg);

			String specialArg = null;
			if ( equalsPos != -1 ) {
				specialArg = pretenderToSymbolParam.substring(equalsPos+1);
				pretenderToSymbolParam = pretenderToSymbolParam.substring(0,equalsPos);
			}

			if(log.isDebugEnabled())log.debug("Current parsing argument : '{}'",pretenderToSymbolParam);

			DefaultParameter optionHandler = (DefaultParameter)Parameters.getParameter(pretenderToSymbolParam);
			if (optionHandler == null){
				RuntimeException e = new OptionNotFoundException(pretenderToSymbolParam,"Unrecognized parameter "+pretenderToSymbolParam);
				deliverCaughtException(e);

			} else {

				if(log.isDebugEnabled())log.debug("Found parameter class {}. Expected number of params is :{}",optionHandler.getClass().getSuperclass().getName(),optionHandler.getOptionValuesLength());

				String[] optionValues;

				if(specialArg != null){
					optionValues = new String [] {specialArg};

				}else{
					int nOptionArgs = optionHandler.getOptionValuesLength();

					if (i + nOptionArgs >= iElements){

						String msg = new StringBuilder("Not enough option values for ").append(pretenderToSymbolParam).
											append(". Expected ").append(nOptionArgs).append(" but detected ").append(iElements-i-1).toString();

						RuntimeException e = new UnexpectedNumberOfArguments(optionHandler,msg);
						deliverCaughtException(e);
					}

					optionValues = new String[nOptionArgs];

					for (int j=0; j<nOptionArgs; j++){
						optionValues[j] = (String)args.get(1+i+j);
					}
					i += nOptionArgs;
				}

				try {

					optionHandler.handleOption(optionValues);

				} catch(ValidationObjectException exc){
//					String msg = new StringBuilder(100).append("Error: illegal argument for option ").append(optionHandler.getSymbol()).
//											append(" : ").append(optionValuesToString(optionValues," ")).append(".\nTry run application with option '-h'").toString();

					deliverCaughtException(exc);

				}
				if(optionHandler.isExit()){
					System.exit(0);
				}
			}
		}
	}

	private static String optionValuesToString(String[] v,String separator){

		StringBuffer sb = new StringBuffer();
		int n = v != null ? v.length:0;
		for (int i=0; i<n; i++){
			sb.append(v[i] );
			sb.append( separator );
		}
		return sb.toString();
	}

}
