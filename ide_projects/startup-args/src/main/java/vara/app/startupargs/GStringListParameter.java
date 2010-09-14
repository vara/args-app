package vara.app.startupargs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vara.app.startupargs.exceptions.ValidationObjectException;

import java.util.List;

/**
 * User: Grzegorz (vara) Warywoda
 * Date: 2010-09-14
 * Time: 04:32:23
 */
public abstract class GStringListParameter extends StringListParameter implements GlobalParameter<List<String>>{

	private static final Logger log = LoggerFactory.getLogger(GStringListParameter.class);

	public GStringListParameter(String symbol, String shortSymbol) {
		super(symbol, shortSymbol);
	}

	private List<String> strings = null;


	@Override
	public void handleOption(List<String> optionValue) throws ValidationObjectException {

		if(strings!= null && log.isWarnEnabled()){ //Print warning about override global value
			log.warn("Detected overriding global value for {}",this);
			log.warn("Last value {} <= new value {}",strings,optionValue);
		}

		strings = optionValue;
	}

	@Override
	public List<String> getValue() {
		return strings;
	}

	@Override
	public boolean isSet() {
		return strings != null;
	}
}
