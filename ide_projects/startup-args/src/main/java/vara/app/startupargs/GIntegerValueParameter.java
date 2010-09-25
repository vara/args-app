package vara.app.startupargs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vara.app.startupargs.exceptions.ValidationObjectException;

/**
 * User: Grzegorz (vara) Warywoda
 * Date: 2010-06-18
 * Time: 04:37:56
 */
public abstract class GIntegerValueParameter extends IntegerValueParameter implements GlobalParameter<Integer>{
	private static final Logger log = LoggerFactory.getLogger(GIntegerValueParameter.class);

	@Override
	public Integer getValue() {
		return val;
	}

	int val;
	boolean isSet = false;

	public GIntegerValueParameter(String symbol, String shortSymbol) {
		super(symbol, shortSymbol);

	}

	@Override
	public void handleOption(int optionValue) throws ValidationObjectException {

		if(log.isDebugEnabled()) log.debug("New option value '{}' has been set for GIntegerValueParameter object",optionValue);

		val = optionValue;
		this.isSet = true;
	}

	@Override
	public boolean isSet() {
		return isSet;
	}

	/**
	 *
	 * @param ls
	 * @param ss
	 * @param description
	 * @return GIntegerValueParameter
	 */
	public static GIntegerValueParameter define(final String ls,final String ss,final String description){

		final GIntegerValueParameter val = new GIntegerValueParameter(ls,ss){

			@Override
			public String getOptionDescription() {
				return description;
			}
		};

		GlobalParameters.putParameter(val);
		return val;
	}

	/**
	 *
	 * @param ls
	 * @param ss
	 * @return GIntegerValueParameter
	 */
	public static GIntegerValueParameter define(final String ls,final String ss){
		return define(ls,ss,"");
	}
}
