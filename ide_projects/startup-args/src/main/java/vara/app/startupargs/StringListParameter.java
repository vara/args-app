package vara.app.startupargs;

import vara.app.startupargs.base.DefaultParameter;
import vara.app.startupargs.base.RangeNumber;
import vara.app.startupargs.exceptions.ValidationObjectException;

import java.util.Arrays;
import java.util.List;

/**
 * User: Grzegorz (vara) Warywoda
 * Date: 2010-09-14
 * Time: 04:28:01
 */
public abstract class StringListParameter extends DefaultParameter {

	public StringListParameter(String symbol,String shortSymbol) {
		super(symbol,shortSymbol);
	}

	@Override
	public void safeOption(String[] optionValues)  throws ValidationObjectException{

		handleOption(Arrays.asList(optionValues));
	}

	@Override
	public final RangeNumber getOptionValuesLength() {
		return RangeNumber.ONE_OR_MORE;
	}

	public abstract void handleOption(List<String> optionValue) throws ValidationObjectException;
}
