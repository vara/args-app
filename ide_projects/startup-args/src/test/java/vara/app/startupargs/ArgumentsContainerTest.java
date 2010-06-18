package vara.app.startupargs;

import org.apache.log4j.Logger;
import org.junit.Test;
import vara.app.startupargs.base.AbstractParameter;
import vara.app.startupargs.base.DefaultParameter;
import vara.app.startupargs.base.Parameters;

import static org.junit.Assert.*;
import static vara.app.startupargs.base.Parameters.InsertBehavior;

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

		AbstractParameter paramGetShortSymbol = Parameters.getParameter("-t1");
		AbstractParameter paramGetSymbol = Parameters.getParameter("--test1");

		assertNotNull(paramGetShortSymbol);
		assertNotNull(paramGetSymbol);

		AbstractParameter paramGetShortNull = Parameters.getParameter("-n1");
		AbstractParameter paramGetNull = Parameters.getParameter("--null1");

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

		wasAdded = Parameters.putParameter(new TestParamBoolean(paramTest1Bool.getSymbol(),paramTest1Bool.getShortSymbol()));
		assertFalse(wasAdded);

		wasAdded = Parameters.putParameter(new TestParamBoolean(paramTest1Bool.getSymbol(),"-notImportant"));
		assertFalse(wasAdded);

		wasAdded = Parameters.putParameter(new TestParamBoolean("-notImportant",paramTest1Bool.getShortSymbol()));
		assertFalse(wasAdded);

		wasAdded = Parameters.putParameter(new TestParamBoolean("-notImportant","-notImportant"));
		assertTrue(wasAdded);
	}


	@Test
	public void removeAll(){
		log.info("Arguments Container Test - removeAll");

		Parameters.putParameter(createParameters());
		
		log.info(" *Fill container ... All parameters in container :"+Parameters.numberOfParameters());

		Parameters.removeAll();

		assertTrue(Parameters.isEmpty());

		log.info("Now Container is empty.");
	}

	@Test
	public void putBehavior(){
		log.info("Put behavior test");

		final  AbstractParameter behParam1 = new TestParamBoolean("--behavior","-beh");
		final  AbstractParameter behParam2 = new TestParamBoolean("--behavior","-beh");

		//we should sure that the param not exists.
		boolean wasAdded = Parameters.putParameter(behParam1, InsertBehavior.DontReplaceExisting);
		assertTrue(wasAdded);

		//Will not add because the same instance of this param exists in container.
		wasAdded = Parameters.putParameter(behParam1,InsertBehavior.ReplaceExisting);
		assertFalse(wasAdded);

		//Try add the same param but inner instance
		//Will not add because the behavior protect older param
		wasAdded = Parameters.putParameter(behParam2,InsertBehavior.DontReplaceExisting);
		assertFalse(wasAdded);

		//Will add because we have different instances of params
		wasAdded = Parameters.putParameter(behParam2,InsertBehavior.ReplaceExisting);
		assertTrue(wasAdded);

		//Try add the same param but inner instance
		//Will add because we have different instances of params
		//remove behParam2
		wasAdded = Parameters.putParameter(behParam1,InsertBehavior.ReplaceExisting);
		assertTrue(wasAdded);

		
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
			return getClass().getSimpleName()+"@'"+ getSymbol()+"' '"+ getShortSymbol()+"'";
		}
	}
}
