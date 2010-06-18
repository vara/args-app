package vara.app.startupargs.demo;

import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vara.app.startupargs.ArgsParser;
import vara.app.startupargs.BooleanValueParameter;
import vara.app.startupargs.FloatValueParameter;
import vara.app.startupargs.NoValueParameter;
import vara.app.startupargs.base.AbstractParameter;
import vara.app.startupargs.base.Parameters;
import vara.app.startupargs.exceptions.CatchOnException;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Grzegorz (vara) Warywoda
 * Date: 2010-06-18
 * Time: 04:08:44
 */
public class Sample {
	private static final Logger log = LoggerFactory.getLogger(Sample.class);

	static {

		BasicConfigurator.configure();
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
				log.error("",e);
			}
		});

		//ArgsParser.setExceptionBehaviour(ArgsParser.ExceptionBehaviour.IGNORE);
	}

	public static void main(String[] args) {
		new Sample();
		try{
			ArgsParser.parseParameters(args);
			
		}catch (Exception e){ //for this catch see ArgsParser.setExceptionBehaviour
			log.warn("",e);
		}
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

		params.add(new FloatValueParameter("--fraction","-f"){
			@Override
			public void handleOption(float optionValue) {
				System.out.println("Detected fraction option, value was set to "+optionValue);
			}

			@Override
			public String getOptionDescription() {
				return "Set fraction value [float]";
			}
		});

		params.add(new BooleanValueParameter("--boolean","-b"){
			@Override
			public void handleOption(boolean optionValue) {
				System.out.println("Detected boolean parameter, value was set to "+optionValue);
			}

			@Override
			public String getOptionDescription() {
				return "Set flag like a boolean value (true or false)";
			}
		});

		return params;
	}
}
