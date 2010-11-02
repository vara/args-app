package vara.app.startupargs.demo;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import vara.app.startupargs.*;
import vara.app.startupargs.base.AbstractParameter;
import vara.app.startupargs.base.ArgsVersion;
import vara.app.startupargs.base.Parameters;
import vara.app.startupargs.exceptions.CatchOnException;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Grzegorz (vara) Warywoda
 * Date: 2010-06-18
 * Time: 04:08:44
 */
public class Sample {
	private static final Logger log = Logger.getLogger(Sample.class);

	static {

		BasicConfigurator.configure();
		//Logger.getRootLogger().setLevel(Level.TRACE);
	}

	{
		init();
	}

	public void init(){
		List<AbstractParameter> list = createParameters();
		Parameters.putParameter(list);

		ArgsParser.setCatchOnException(new CatchOnException(){
			@Override
			public void caughtException(Exception e) {
//				SwingUtilities.invokeLater(new Runnable(){
//					@Override public void run(){
//
//					}
//				});
				JOptionPane.showMessageDialog(null,""+e.getLocalizedMessage());
				log.error("",e);
			}
		});

		//ArgsParser.setExceptionBehaviour(ArgsParser.ExceptionBehaviour.IGNORE);
	}

	public static void main(String[] args) {

		log.info(ArgsVersion.getStringFullVersion());

		new Sample();
		try{
			ArgsParser.parseParameters(args);
			
		}catch (Exception e){ //for this catch see ArgsParser.setExceptionBehaviour
			log.warn("",e);
		}

		GlobalParameter gp = null;
		if( (gp = GlobalParameters.getGlobalParameter("gp")).isSet()){
			System.out.println("Detected global parameter "+gp.getSymbol() +" and was set to "+gp.getValue());
		}
		//or access directly
		boolean val  = GlobalParameters.getBooleanValue("gp");

		int gInt = GlobalParameters.getIntegerValue("gInt",-1);
		if(gInt != -1)
			System.out.println("Detected global integer parameter and was set to "+gInt);
	}

	 protected List<AbstractParameter> createParameters(){

		List<AbstractParameter> params = new ArrayList();

		params.add(new NoValueParameter("--verbose","-v") {
			@Override public void handleOption() {
				System.out.println("Detected verbose option");
			}
			@Override
			public String getOptionDescription() {
				return "option set debug level on root logger";
			}
		});

		params.add(new FloatValueParameter("fraction","f"){
			@Override
			public void handleOption(float optionValue) {
				System.out.println("Detected fraction option, value was set to "+optionValue);
			}

			@Override
			public String getOptionDescription() {
				return "Set fraction value [float]";
			}
		});

		params.add(new BooleanValueParameter("boolean","b"){
			@Override
			public void handleOption(boolean optionValue) {
				System.out.println("Detected boolean parameter, value was set to "+optionValue);
			}

			@Override
			public String getOptionDescription() {
				return "Set flag like a boolean value (true or false)";
			}
		});

		GBooleanValueParameter.define("globalParam","gp");

		GIntegerValueParameter.define("globalInt","gInt");

		return params;
	}
}
