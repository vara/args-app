package vara.app.startupargs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Test;
import vara.app.startupargs.base.Parameters;

import java.util.Arrays;
import java.util.List;

/**
 * User: Grzegorz (vara) Warywoda
 * Date: 2010-05-12
 * Time: 15:28:20
 */
//@Ignore
public class CmdLineArgumentsTest extends FixtureUtil{
	private static final Logger log = LoggerFactory.getLogger(CmdLineArgumentsTest.class);

//	public static void main(String[] args) throws Exception {
//
//		CmdLineArgumentsTest.beforeClass();
//
//		Parameters.putParameter(createParameters());
//
//		ArgsParser.parseParameters(args);
//	}

	@Override
	public void beforeTest() throws Exception {
		if(Parameters.numberOfParameters() <4)
			Parameters.putParameter(createParameters());
	}

	@Test
	public void cmdlWithEqualsOrWithout(){

		log.info("cmdLine With Equals Or Without");

		List<String> args = Arrays.asList("-f=1002.2 --boolean=true".split(" "));

		ArgsParser.parseParameters(args);

		args = Arrays.asList("-f 10.2 --boolean false".split(" "));

		ArgsParser.parseParameters(args);
	}

	@Test
	public void cmdlWrongNumOfInputParams(){
		log.info("cmdlWrongNumOfInputParams");
		List<String> args = Arrays.asList("-f 1002.2 --boolean -h".split(" "));

		ArgsParser.parseParameters(args);
	}
}
