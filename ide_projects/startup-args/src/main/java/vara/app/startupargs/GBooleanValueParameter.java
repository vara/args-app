package vara.app.startupargs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vara.app.startupargs.exceptions.ValidationObjectException;

/**
 * User: Grzegorz (vara) Warywoda
 * Date: 2010-06-18
 * Time: 04:46:17
 */
public abstract class GBooleanValueParameter extends BooleanValueParameter implements GlobalParameter<Boolean>{
	private static final Logger log = LoggerFactory.getLogger(GBooleanValueParameter.class);

	protected GBooleanValueParameter(String symbol, String shortSymbol) {
		super(symbol, shortSymbol);
	}

	boolean val = false;

	boolean isSet = false;

	public Boolean getValue() {
		return val;
	}

	@Override
	public void handleOption(boolean optionValue) throws ValidationObjectException {
		val = optionValue;

		isSet = true;
	}

	@Override
	public boolean isSet() {
		return isSet;
	}

	/**
	 * Create new global boolean parameter based on inputs arguments.
	 * Method automagically add this parameter to container.
	 * Format of symbols (long and short) not required prefixes
	 *
	 * @param longSymbolName full name for this parameter
	 * @param shortSymbolName short name for this parameter
	 * @param description
	 *
	 * @return GBooleanValueParameter
	 */
	public static GBooleanValueParameter define(final String longSymbolName,final String shortSymbolName,final String description){

		final GBooleanValueParameter val = new GBooleanValueParameter(longSymbolName,shortSymbolName){

			@Override
			public String getOptionDescription() {
				return description;
			}
		};

		GlobalParameters.putParameter(val);
		return val;
	}

	/**
	 * Create new global boolean parameter based on inputs arguments.
	 * Method automagically add this parameter to container.
	 * Format of symbols (long and short) not required prefixes
	 *
	 * @param longSymbolName full name for this parameter
	 * @param shortSymbolName short name for this parameter
	 *
	 * @return GBooleanValueParameter
	 */
	public static GBooleanValueParameter define(final String longSymbolName,final String shortSymbolName){
		return define(longSymbolName,shortSymbolName,"");
	}
}
