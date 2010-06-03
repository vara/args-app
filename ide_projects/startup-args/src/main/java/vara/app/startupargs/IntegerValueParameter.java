package vara.app.startupargs;

import vara.app.startupargs.exceptions.ValidationObjectException;

/**
 *
 * @author Grzegorz (vara) Warywoda
 */
public abstract class IntegerValueParameter extends StringValueParameter {

	public IntegerValueParameter(String symbol,String shortSymbol) {
		super(symbol,shortSymbol);
	}

	@Override
	public void handleOption(String optionValue)  throws ValidationObjectException{
		try{
			handleOption(Integer.parseInt(optionValue));
		} catch(NumberFormatException e){
			throw new IllegalArgumentException(e);
		}
	}

	@Override
	public String getOptionUsage() {
		return super.getOptionUsage()+" value [ Integer value ]";
	}

	public abstract void handleOption(int optionValue) throws ValidationObjectException;
}
