package vara.app.startupargs;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vara.app.startupargs.base.Parameters;
import vara.app.startupargs.exceptions.OptionNotFoundException;
import vara.app.startupargs.exceptions.UnexpectedNumberOfArguments;
import vara.app.startupargs.exceptions.UnexpectedValueException;
import vara.app.startupargs.exceptions.ValidationObjectException;

import java.util.Arrays;
import java.util.List;

import static vara.app.startupargs.ArgsParser.ExceptionBehaviour;

/**
 * User: Grzegorz (vara) Warywoda
 * Date: 2010-06-01
 * Time: 12:05:59
 */
public class ExceptionsTest extends FixtureUtil{

	private static final Logger log = LoggerFactory.getLogger(ExceptionsTest.class);

	@BeforeClass
	public static void beforeClass() throws Exception {
		FixtureUtil.beforeClass();
		Parameters.putParameter(new ArgsName("hotkey"));
	}

	@Before
	public void initTest(){
		ArgsParser.setExceptionBehaviour(ExceptionBehaviour.THROW);
	}

	@Test (expected = OptionNotFoundException.class)
	public void optionNotFoundTest(){
		log.info(" * Run option not found exception test");

		List params = Arrays.asList("--throwException");

		ArgsParser.parseParameters(params);
	}

	@Test (expected = UnexpectedNumberOfArguments.class)
	public void wrongNumberOfArgumentsTest(){

		log.info(" * Run wrong number of arguments test");

		List params = Arrays.asList("-b 1 2 3 -f 12 12 -b -f".split(" "));

		ArgsParser.parseParameters(params);

	}

	@Test (expected = ValidationObjectException.class)
	public void validationExceptionTest(){
		log.info("* Run validation exception test");

		ArgsParser.parseParameters("--name=hotkey");
	}

	static class ArgsName extends StringValueParameter {
		private String pattern;
		ArgsName(String pattern){
			super("--name","-n");
			this.pattern = pattern;
		}

		@Override
		public void handleOption(String optionValue) throws ValidationObjectException {
			if(optionValue.equals(pattern)){
				throw new UnexpectedValueException(this,"Success !");
			}
			log.warn(getSymbol()+" : Wrong value \""+optionValue+"\", only \""+pattern+"\" is correct");
		}

		@Override
		public String getOptionDescription() {
			return "Set name argument. [String]";
		}
	}
}
