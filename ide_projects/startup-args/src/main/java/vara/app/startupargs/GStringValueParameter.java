package vara.app.startupargs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vara.app.startupargs.exceptions.ValidationObjectException;

/**
 * User: Grzegorz (vara) Warywoda
 * Date: 2010-06-18
 * Time: 04:42:59
 */
public abstract class GStringValueParameter extends StringValueParameter implements GlobalParameter<String>{
	private static final Logger log = LoggerFactory.getLogger(GStringValueParameter.class);

	private String val = null;

	@Override
	public String getValue() {
		return val;
	}

	public GStringValueParameter(String symbol, String shortSymbol) {
		super(symbol, shortSymbol);

	}

	@Override
	public void handleOption(String optionValue) throws ValidationObjectException {

		if(val != null && log.isWarnEnabled()){ //Print warning about override global value 
			log.warn("Detected overriding global value for {}",this);
			log.warn("Last value {} <= new value {}",val,optionValue);
		}

		val = optionValue;

		if(log.isDebugEnabled()){
			log.debug("Global string parameter was set : "+val);
		}
	}

	@Override
	public boolean isSet() {
		return val != null;
	}

	/**
	 *
	 * @param ls
	 * @param ss
	 * @param description
	 */
	public static void define(final String ls,final String ss,final String description){

		final GlobalParameter val = new GStringValueParameter(ls,ss){

			@Override
			public String getOptionDescription() {
				return description;
			}
		};

		GlobalParameters.putParameter(val);
	}

	/**
	 *
	 * @param ls
	 * @param ss
	 */
	public static void define(final String ls,final String ss){
		define(ls,ss,"");
	}
}
