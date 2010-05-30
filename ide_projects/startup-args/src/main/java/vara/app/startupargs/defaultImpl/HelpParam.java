package vara.app.startupargs.defaultImpl;

import org.apache.log4j.Logger;
import vara.app.startupargs.NoValueParameter;
import vara.app.startupargs.base.DefaultParameter;
import vara.app.startupargs.base.Parameters;

import java.util.List;

/**
 * User: Grzegorz (vara) Warywoda
 * Date: 2010-05-30
 * Time: 05:27:00
 */
public class HelpParam extends NoValueParameter{
	private static final Logger log = Logger.getLogger(HelpParam.class);

	public HelpParam(String symbol, String shortSymbol) {
		super(symbol, shortSymbol);
	}

	public HelpParam() {
		super("--help", "-h");
	}

	@Override
	public void handleOption() {
		List<DefaultParameter> params = Parameters.getAllParameters();
		System.out.println("Options:");
		final String format = "%s,%s\n\t";
		for (DefaultParameter ap : params) {
			System.out.printf(format,ap.getSymbol(),ap.getShortSymbol(),ap.getOptionDescription());
		}
	}

	@Override
	public String getOptionDescription() {
		return "Print this message";
	}

	@Override
	public boolean isExit() {
		return true;
	}
}
