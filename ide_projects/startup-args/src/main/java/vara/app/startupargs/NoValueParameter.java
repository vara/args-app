package vara.app.startupargs;

import vara.app.startupargs.base.DefaultParameter;
import vara.app.startupargs.base.RangeNumber;
import vara.app.startupargs.exceptions.ValidationObjectException;

/**
 *
 * @author Grzegorz (vara) Warywoda
 */
public abstract class NoValueParameter extends DefaultParameter {

	/* This class represents a parameter which performs
	 * the action defined and the program ends.
	 */
	public NoValueParameter(String symbol, String shortSymbol){
		super(symbol,shortSymbol);
	}

	@Override
	public void safeOption(String[] optionValues)  throws ValidationObjectException{
		handleOption();
	}

	@Override
	public RangeNumber getOptionValuesLength() {
		return RangeNumber.ZERO;
	}

	public abstract void handleOption()  throws ValidationObjectException;
}
