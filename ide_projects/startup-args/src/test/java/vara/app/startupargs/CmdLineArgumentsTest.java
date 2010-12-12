package vara.app.startupargs;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import vara.app.startupargs.exceptions.UnexpectedNumberOfArguments;

import java.util.Arrays;
import java.util.List;

/**
 * User: Grzegorz (vara) Warywoda
 * Date: 2010-05-12
 * Time: 15:28:20
 */
//@Ignore
public class CmdLineArgumentsTest extends FixtureUtil{
	private static final Logger log = Logger.getLogger(CmdLineArgumentsTest.class);

	@BeforeClass
	public static void beforeClass() throws Exception {
		FixtureUtil.beforeClass();
		Logger.getRootLogger().setLevel(Level.TRACE);
		ArgsParser.setExceptionBehaviour(ArgsParser.ExceptionBehaviour.THROW);
		GStringValueParameter.define("gstring","gs");
	}
	
	@Test
	public void cmdlWithEqualsOrWithout(){

		log.info("cmdLine With Equals Or Without");

		List<String> args = Arrays.asList("-f=1002.2 -boolean=true -gs=test".split(" "));

		ArgsParser.parseParameters(args);

		GlobalParameter gp  = GlobalParameters.getGlobalParameter("gs");
		System.out.println("gp class: "+gp.getClass());
		Assert.assertNotNull(gp);
		Assert.assertTrue(gp.isSet());

		args = Arrays.asList("-f 10.2 --boolean false -gs test".split(" "));

		ArgsParser.parseParameters(args);
	}

	//@Test
	public void cmdlWrongNumOfInputParams(){
		log.info("cmdlWrongNumOfInputParams");
		List<String> args = Arrays.asList("-f 1002.2 --boolean -h".split(" "));

		ArgsParser.parseParameters(args);
	}

	@Test (expected=UnexpectedNumberOfArguments.class )
	public void cmdlEmptyCombinedArg(){
		log.info("Empty combined argument. Should throw exception");
		List<String> args = Arrays.asList("-f=".split(" "));

		ArgsParser.parseParameters(args);
	}

	@Test
	public void sequentialOptions(){
		log.info("sequential Parameter test");

		String disableMarkChar = ArgsUtil.getDisableChar();

		List<String> args = Arrays.asList("-gs=one -gs=two".split(" "));

		ArgsParser.parseParameters(args);
		String expected  = "two";
		String gotValue = GlobalParameters.getStringValue("gs");

		Assert.assertEquals(expected,gotValue);

		String argsStr = "-gs one -gs two";
		ArgsParser.parseParameters(argsStr);
		expected  = "two";
		gotValue = GlobalParameters.getStringValue("gs");

		Assert.assertEquals(expected,gotValue);
	}

	@Test
	public void disableParameter(){
		log.info("Disabled option test");

		String disableMarkChar = ArgsUtil.getDisableChar();

		String args = "-gs=one -"+disableMarkChar+"gs=two";
		ArgsParser.parseParameters(args);
		String expected  = "one";
		String gotValue = GlobalParameters.getStringValue("gs");

		Assert.assertEquals(expected,gotValue);
	}
}
