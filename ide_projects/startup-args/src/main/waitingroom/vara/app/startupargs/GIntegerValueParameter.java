package vara.app.startupargs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vara.app.startupargs.exceptions.ValidationObjectException;

/**
 * User: Grzegorz (vara) Warywoda
 * Date: 2010-06-18
 * Time: 04:37:56
 */
public abstract class GIntegerValueParameter extends IntegerValueParameter implements GlobalParameter<Integer>{
	private static final Logger log = LoggerFactory.getLogger(GIntegerValueParameter.class);

	public Integer getValue() {
		return val;
	}

	int val;

	public GIntegerValueParameter(String symbol, String shortSymbol) {
		super(symbol, shortSymbol);

	}

	@Override
	public void handleOption(int optionValue) throws ValidationObjectException {
		val = optionValue;
	}
}
