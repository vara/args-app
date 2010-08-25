package vara.app.startupargs.defaultImpl;

import vara.app.startupargs.NoValueParameter;
import vara.app.startupargs.base.AbstractParameter;
import vara.app.startupargs.base.Parameters;

import java.io.PrintStream;
import java.util.List;

/**
 * User: Grzegorz (vara) Warywoda
 * Date: 2010-05-30
 * Time: 05:27:00
 */
public class DefaultHelpParameter extends NoValueParameter{

	private String usage = null;

	private PrintStream localOutputStream = null;

	public DefaultHelpParameter(String symbol, String shortSymbol) {
		super(symbol, shortSymbol);
	}

	public DefaultHelpParameter() {
		super("--help", "-h");
	}

	@Override
	public void handleOption() {
		PrintStream out = localOutputStream;
		if(out == null) out = System.out;

		printInfo(out);
	}

	private void printInfo(PrintStream out){
		List<AbstractParameter> params = Parameters.getAllParameters();

		if(usage != null && !usage.isEmpty()){

			out.printf("Usage: %s\n",usage);
		}

		out.println("Options:");
		final String format = "\t%s\t\t%s\n";
		for (AbstractParameter ap : params) {
			out.printf(format,ap.getOptionUsage(),ap.getOptionDescription());
		}

		out.println();
	}

	@Override
	public String getOptionDescription() {
		return "Print this message";
	}

	@Override
	public boolean isExit() {
		return true;
	}

	public void setUsage(String usage) {
		this.usage = usage;
	}

	public String getUsage() {
		return usage;
	}
}
