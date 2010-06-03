package vara.app.startupargs;

import org.apache.log4j.Logger;
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
	private static final Logger log = Logger.getLogger(ArgsParser.class);

	private static List<CatchOnException> exceptionCatchers = new ArrayList<CatchOnException>();

	public enum ExceptionBehaviour{
		IGNORE,
		THROW,
		EXIT
	}

	public static ExceptionBehaviour getExceptionBehaviour() {
		return exceptionBehaviour;
	}

	public static void setExceptionBehaviour(ExceptionBehaviour exceptionBehaviour) {
		ArgsParser.exceptionBehaviour = exceptionBehaviour;
	}

	private static ExceptionBehaviour exceptionBehaviour = ExceptionBehaviour.EXIT;

	public static void parseParameters(String[] args){
		parseParameters(Arrays.asList(args));
	}

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

		if(exceptionBehaviour == ExceptionBehaviour.THROW)	throw  exc;
		if(exceptionBehaviour == ExceptionBehaviour.EXIT){
			log.warn(exc);
			//TODO: Check for special error code
			System.exit(1);
		}
	}

	public static void parseParameters(List<String> args){

		int iElements = args.size();

		if(log.isDebugEnabled() &&iElements!=0) log.debug("Prepare to parse input parameters");

		for (int i = 0; i < iElements; i++) {
			String pretenderToSymbolParam = args.get(i);

			if(!isSymbolParameter(pretenderToSymbolParam)){
				//If parameter flags not detected shift index
				if(log.isDebugEnabled())log.debug("Current parsing argument ("+pretenderToSymbolParam+") isn't a symbol, skip it.");
				continue;
			}

			if(log.isDebugEnabled())log.debug("Current parsing argument : '"+pretenderToSymbolParam+"'");

			DefaultParameter optionHandler = (DefaultParameter)Parameters.getParameter(pretenderToSymbolParam);
			if (optionHandler == null){
				RuntimeException e = new OptionNotFoundException(pretenderToSymbolParam,"Unrecognized parameter "+pretenderToSymbolParam);
				deliverCaughtException(e);

			} else {
				int nOptionArgs = optionHandler.getOptionValuesLength();

				if (i + nOptionArgs >= iElements){
					RuntimeException e = new UnexpectedNumberOfArguments(optionHandler,"Not enough option values");
					deliverCaughtException(e);
				}

				String[] optionValues = new String[nOptionArgs];
				for (int j=0; j<nOptionArgs; j++){
					optionValues[j] = (String)args.get(1+i+j);
				}
				i += nOptionArgs;

				try {

					optionHandler.handleOption(optionValues);

				} catch(ValidationObjectException exc){

//					Exception exc = new Exception("Error: illegal argument for option "+optionHandler.getSymbol() +" : "+
//										 optionValuesToString(optionValues)+".\nTry run application with option '-h'");
					deliverCaughtException(exc);

				}
				if(optionHandler.isExit()){
					System.exit(0);
				}
			}
		}
	}

	private static boolean isSymbolParameter(String pretender){
		if(pretender.startsWith("-") || pretender.startsWith("--")){
			return true;
		}
		return false;
	}

	private static String optionValuesToString(String[] v){

		StringBuffer sb = new StringBuffer();
		int n = v != null ? v.length:0;
		for (int i=0; i<n; i++){
			sb.append(v[i] );
			sb.append( ' ' );
		}
		return sb.toString();
	}

}
