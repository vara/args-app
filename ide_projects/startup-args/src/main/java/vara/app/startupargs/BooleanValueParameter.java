package vara.app.startupargs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vara.app.startupargs.base.DefaultParameter;
import vara.app.startupargs.base.RangeNumber;
import vara.app.startupargs.exceptions.ValidationObjectException;

/**
 * Created by IntelliJ IDEA.
 * User: Grzegorz (vara) Warywoda
 * Date: 2009-11-14
 * Time: 18:07:31
 */

public abstract class BooleanValueParameter extends DefaultParameter {

	private static Logger log = LoggerFactory.getLogger(BooleanValueParameter.class);

	public BooleanValueParameter(String symbol,String shortSymbol) {
		super(symbol,shortSymbol);
	}

	@Override
	public void safeOption(String[] optionValues)  throws ValidationObjectException{

		handleOption(optionValues.length == 0 ? true : Boolean.parseBoolean(optionValues[0]));

	}

	@Override
	public final RangeNumber getOptionValuesLength() {
		return RangeNumber.ONE_OR_ZERO;
	}

	@Override
	public String getOptionUsage() {
		return super.getOptionUsage();
	}

	public abstract void handleOption(boolean optionValue) throws ValidationObjectException;
}
