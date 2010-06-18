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

	boolean val;

	public Boolean getValue() {
		return val;
	}

	@Override
	public void handleOption(boolean optionValue) throws ValidationObjectException {
		val = optionValue;
	}
}
