package vara.app.startupargs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vara.app.startupargs.exceptions.ValidationObjectException;

/**
 * User: Grzegorz (vara) Warywoda
 * Date: 2010-06-18
 * Time: 04:42:59
 */
public abstract class GStringValueParameter extends StringValueParameter implements GlobalParameter<String>{
	private static final Logger log = LoggerFactory.getLogger(GStringValueParameter.class);

	@Override
	public String getValue() {
		return val;
	}

	String val;

	public GStringValueParameter(String symbol, String shortSymbol) {
		super(symbol, shortSymbol);

	}

	@Override
	public void handleOption(String optionValue) throws ValidationObjectException {
		val = optionValue;
	}
}
