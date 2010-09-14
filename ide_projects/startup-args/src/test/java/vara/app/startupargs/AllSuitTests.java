package vara.app.startupargs;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * User: Grzegorz (vara) Warywoda
 * Date: 2010-08-28
 * Time: 03:47:41
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	ArgsParserTest.class,
	ArgumentsContainerTest.class,
	CmdLineArgumentsTest.class,
	ExceptionsTest.class,
	/*FileValueParameterTest.class,*/
	ParameterCreationTest.class
})
public class AllSuitTests {
}
