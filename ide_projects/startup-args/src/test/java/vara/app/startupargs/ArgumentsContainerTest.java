package vara.app.startupargs;

import org.apache.log4j.Logger;
import org.junit.Test;
import vara.app.startupargs.base.DefaultParameter;
import vara.app.startupargs.base.Parameters;

import static org.junit.Assert.*;

/**
 * User: Grzegorz (vara) Warywoda
 * Date: 2010-03-18
 * Time: 23:09:04
 */
public class ArgumentsContainerTest extends FixtureUtil{
	private static final Logger log = Logger.getLogger(ArgumentsContainerTest.class);

	private static final DefaultParameter paramTest1Bool = new TestParamBoolean("--test1","-t1");
	private static final DefaultParameter paramTest2Bool = new TestParamBoolean("--test2","-t2");


	public void beforeTest(){
		log.info("Before Test");
		Parameters.putParameter(paramTest1Bool);

		assertFalse(Parameters.isEmpty());
	}


	@Test
	public void get(){
		log.info("Arguments Container Test - get");

		DefaultParameter paramGetShortSymbol = Parameters.getParameter("-t1");
		DefaultParameter paramGetSymbol = Parameters.getParameter("--test1");

		assertNotNull(paramGetShortSymbol);
		assertNotNull(paramGetSymbol);

		DefaultParameter paramGetShortNull = Parameters.getParameter("-n1");
		DefaultParameter paramGetNull = Parameters.getParameter("--null1");

		assertNull(paramGetShortNull);
		assertNull(paramGetNull);

	}

	@Test
	public void put(){
		log.info("Arguments Container Test - put");

		boolean wasAdded = Parameters.putParameter(paramTest1Bool);
		assertFalse(wasAdded);

		wasAdded = Parameters.putParameter(paramTest1Bool);
		assertFalse(wasAdded);

		wasAdded = Parameters.putParameter(new TestParamBoolean(paramTest1Bool.getOption(),paramTest1Bool.getShortOption()));
		assertFalse(wasAdded);

		wasAdded = Parameters.putParameter(new TestParamBoolean(paramTest1Bool.getOption(),"-notImportant"));
		assertFalse(wasAdded);

		wasAdded = Parameters.putParameter(new TestParamBoolean("-notImportant",paramTest1Bool.getShortOption()));
		assertFalse(wasAdded);

		wasAdded = Parameters.putParameter(new TestParamBoolean("-notImportant","-notImportant"));
		assertTrue(wasAdded);
	}


	@Test
	public void removeAll(){
		log.info("Arguments Container Test - removeAll");

		log.info(" *Fill container ... All parameters in container :"+fillContainerDefaultsParameters());

		Parameters.removeAll();

		assertTrue(Parameters.isEmpty());

		log.info("Now Container is empty.");
	}

	public int fillContainerDefaultsParameters(){

		int numOfParameters = CoreGui.getAllParameters().size();
		return numOfParameters;
	}

	static class TestParamBoolean extends BooleanValueParameter{
		public TestParamBoolean(String symbol, String shortSymbol) {
			super(symbol, shortSymbol);
		}

		@Override
		public void handleOption(boolean optionValue) {
		}

		@Override
		public String getOptionDescription() {
			return getClass().getSimpleName()+"@JUnit-Test";
		}

		@Override
		public String toString() {
			return getClass().getSimpleName()+"@'"+getOption()+"' '"+getShortOption()+"'";
		}
	}
}
