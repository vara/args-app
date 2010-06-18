package vara.app.startupargs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vara.app.startupargs.exceptions.UnexpectedValueException;
import vara.app.startupargs.exceptions.ValidationObjectException;

/**
 * Created by IntelliJ IDEA.
 * User: Grzegorz (vara) Warywoda
 * Date: 2009-11-14
 * Time: 18:07:31
 */

public abstract class BooleanValueParameter extends StringValueParameter {

	private static Logger log = LoggerFactory.getLogger(BooleanValueParameter.class);

	public BooleanValueParameter(String symbol,String shortSymbol) {
		super(symbol,shortSymbol);
	}

	@Override
	public void handleOption(String optionValue)  throws ValidationObjectException{
		try{

			handleOption(Boolean.parseBoolean(optionValue));

		} catch(Exception e){
			throw new UnexpectedValueException(this,e);
		}
	}

	@Override
	public String getOptionUsage() {
		return super.getOptionUsage();
	}

	public abstract void handleOption(boolean optionValue) throws ValidationObjectException;
}
