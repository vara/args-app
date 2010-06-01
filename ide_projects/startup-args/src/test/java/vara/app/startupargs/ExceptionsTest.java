package vara.app.startupargs;

import org.apache.log4j.Logger;
import org.junit.Test;
import vara.app.startupargs.base.Parameters;
import vara.app.startupargs.exceptions.UnexpectedValueException;
import vara.app.startupargs.exceptions.ValidationObjectException;

/**
 * User: Grzegorz (vara) Warywoda
 * Date: 2010-06-01
 * Time: 12:05:59
 */
public class ExceptionsTest extends FixtureUtil{

	private static final Logger log = Logger.getLogger(ExceptionsTest.class);

	public static void main(String[] args) throws Exception {

		CmdLineArgumentsTest.beforeClass();

		Parameters.putParameter(createParameters());

		Parameters.putParameter(new ArgsName());

		ArgsParser.parseParameters(args);
	}

	//@Test
	public void illegalValueArguments(){

	}

	static class ArgsName extends SingleValueParameter{
		ArgsName(){
			super("--name","-n");
		}

		@Override
		public void handleOption(String optionValue) throws ValidationObjectException {
			if(!optionValue.equals("Greg")){
				throw new UnexpectedValueException(this,"Wrong value \""+optionValue+"\", only \"Greg\" is correct");
			}
			System.out.println("Success !");
		}

		@Override
		public String getOptionDescription() {
			return "Set name argument. [String]";
		}
	}
}
