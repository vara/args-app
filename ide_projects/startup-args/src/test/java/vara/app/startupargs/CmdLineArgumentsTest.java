package vara.app.startupargs;

import org.apache.log4j.Logger;
import vara.app.startupargs.base.Parameters;

/**
 * User: Grzegorz (vara) Warywoda
 * Date: 2010-05-12
 * Time: 15:28:20
 */
public class CmdLineArgumentsTest extends FixtureUtil{
	private static final Logger log = Logger.getLogger(CmdLineArgumentsTest.class);

	public static void main(String[] args) throws Exception {

		CmdLineArgumentsTest.beforeClass();

		Parameters.putParameter(createParameters());

		ArgsParser.parseParameters(args);
	}
}
