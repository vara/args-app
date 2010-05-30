package vara.app.startupargs;

import org.apache.log4j.Logger;
import vara.app.startupargs.Exception.ParseOptionException;

/**
 * Created by IntelliJ IDEA.
 * User: Grzegorz (vara) Warywoda
 * Date: 2009-11-14
 * Time: 18:07:31
 */

public abstract class BooleanValueParameter extends SingleValueParameter {

	private static Logger log = Logger.getLogger(BooleanValueParameter.class);

	public BooleanValueParameter(String symbol,String shortSymbol) {
		super(symbol,shortSymbol);
	}

	@Override
	public void handleOption(String optionValue) {
		try{

			handleOption(Boolean.parseBoolean(optionValue));

		} catch(Exception e){
			throw new ParseOptionException(e);
		}
	}

	public abstract void handleOption(boolean optionValue);
}
