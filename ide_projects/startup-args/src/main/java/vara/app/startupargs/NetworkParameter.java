package vara.app.startupargs;

import sun.net.util.IPAddressUtil;
import vara.app.startupargs.exceptions.UnexpectedValueException;
import vara.app.startupargs.exceptions.ValidationObjectException;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * User: Grzegorz (vara) Warywoda
 * Date: 2010-09-24
 * Time: 20:27:45
 */

//TODO: Add validation for addresses numerical and textual
//	NOTE:	Does handleOption should get parameter type of InetAddress
//  NOTE: 	Method InetAddress.getAllByName it may be too slow for textual addresses.
abstract class NetworkParameter extends StringValueParameter{

	protected NetworkParameter(String symbol, String shortSymbol) {
		super(symbol, shortSymbol);
	}

	@Override
	public void handleOption(String optionValue) throws ValidationObjectException {
		try {
			handleOption(InetAddress.getByName(optionValue));
		} catch (UnknownHostException e) {
			throw new UnexpectedValueException(this,e);
		}
	}

	public final static boolean validateIPAddress(String  ipAddress ){
		boolean valid = IPAddressUtil.isIPv4LiteralAddress(ipAddress);

		return valid;
	}

	public abstract void handleOption(InetAddress optionValue) throws ValidationObjectException;
}
