package vara.app.startupargs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vara.app.startupargs.exceptions.ValidationObjectException;

import java.net.InetAddress;

/**
 * User: Grzegorz (vara) Warywoda
 * Date: 2010-09-24
 * Time: 21:46:19
 */
public abstract class GNetworkParameter extends NetworkParameter implements GlobalParameter<InetAddress>{
	private static final Logger log = LoggerFactory.getLogger(GNetworkParameter.class);

	private InetAddress address = null;

	protected GNetworkParameter(String symbol, String shortSymbol) {
		super(symbol, shortSymbol);
	}

	@Override
	public InetAddress getValue() {
		return address;
	}

	@Override
	public boolean isSet() {
		return address != null;
	}

	@Override
	public void handleOption(InetAddress optionValue) throws ValidationObjectException {

		if(address!= null && log.isWarnEnabled()){ //Print warning about override global value
			log.warn("Detected overriding global value for {}",this);
			log.warn("Last value {} <= new value {}",address,optionValue);
		}

		address = optionValue;
	}

	/**
	 *
	 * @param ls
	 * @param ss
	 * @param description
	 *
	 * @return GNetworkParameter
	 */
	public static GNetworkParameter define(final String ls,final String ss,final String description){

		final GNetworkParameter val = new GNetworkParameter(ls,ss){

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
	 *
	 * @return GNetworkParameter
	 */
	public static GNetworkParameter define(final String ls,final String ss){
		return define(ls,ss,"");
	}
}
