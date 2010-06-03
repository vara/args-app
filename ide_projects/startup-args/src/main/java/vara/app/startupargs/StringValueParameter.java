package vara.app.startupargs;

import vara.app.startupargs.base.DefaultParameter;
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
		if(optionValues[0] != null){
			handleOption(optionValues[0]);
		}else{
			throw new ValidationObjectException(this,"Option value must be non-null");
		}
	}

	@Override
	public int getOptionValuesLength() {
		return 1;
	}

	@Override
	public boolean isExit() {
		return false;
	}

	public abstract void handleOption(String optionValue) throws ValidationObjectException;
}
