package vara.app.startupargs;

import org.apache.log4j.Logger;

/**
 * User: Grzegorz (vara) Warywoda
 * Date: 2010-05-12
 * Time: 15:58:01
 */
public abstract class FloatValueParameter extends SingleValueParameter{
	public FloatValueParameter(String symbol, String shortSymbol) {
		super(symbol, shortSymbol);
	}

	 @Override
	public void handleOption(String optionValue) {
		try{
			handleOption(Float.valueOf(optionValue));
		} catch(NumberFormatException e){
			throw new IllegalArgumentException(e);
		}
	}

	public abstract void handleOption(float optionValue);
}
