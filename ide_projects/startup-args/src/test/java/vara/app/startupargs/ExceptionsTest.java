package vara.app.startupargs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Test;
import vara.app.startupargs.exceptions.UnexpectedValueException;
import vara.app.startupargs.exceptions.ValidationObjectException;

import java.util.Arrays;
import java.util.List;

/**
 * User: Grzegorz (vara) Warywoda
 * Date: 2010-06-01
 * Time: 12:05:59
 */
public class ExceptionsTest extends FixtureUtil{

	private static final Logger log = LoggerFactory.getLogger(ExceptionsTest.class);

//	public static void main(String[] args) throws Exception {
//
//		CmdLineArgumentsTest.beforeClass();
//
//		Parameters.putParameter(createParameters());
//
//		Parameters.putParameter(new ArgsName());
//
//		ArgsParser.parseParameters(args);
//	}

	@Test (expected = Exception.class)
	public void illegalValueArguments(){

		ArgsParser.setExceptionBehaviour(ArgsParser.ExceptionBehaviour.THROW);

		log.info("Run illegalValueArguments test");

		List params = Arrays.asList("--throwException");

		ArgsParser.parseParameters(params);
	}

	static class ArgsName extends StringValueParameter {
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
