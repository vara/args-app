package vara.app.startupargs;

import vara.app.startupargs.base.DefaultParameter;
import vara.app.startupargs.base.NumberOfParams;
import vara.app.startupargs.exceptions.ValidationObjectException;

/**
 *
 * @author Grzegorz (vara) Warywoda
 */
public abstract class StringValueParameter extends DefaultParameter {

	public StringValueParameter(String symbol,String shortSymbol) {
		super(symbol,shortSymbol);
	}

	@Override
	public void safeOption(String[] optionValues)  throws ValidationObjectException{

		handleOption(optionValues[0]);
	}

	@Override
	public final NumberOfParams getOptionValuesLength() {
		return NumberOfParams.ONE;
	}

	public abstract void handleOption(String optionValue) throws ValidationObjectException;
}
