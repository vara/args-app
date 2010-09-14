package vara.app.startupargs;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;
/**
 * ArgsUtil Tester.
 *
 * @author <Authors name>
 * @since <pre>08/27/2010</pre>
 * @version 1.0
 */
public class ArgsUtilTest extends FixtureUtil{

	private static final Logger log = Logger.getLogger(ArgsUtilTest.class);


	@BeforeClass
	public static void beforeClass() throws Exception {
		FixtureUtil.beforeClass();
	}
	/**
	 *
	 * Method: check(String symbol, boolean isShort)
	 *
	 */
	@Test
	public void testCheck() {
		log.info("* Run Check symbol test");


		String shortSymbol =  ArgsUtil.isStoreWithoutPrefix() ? "test" : "-test";
		String longSymbol = ArgsUtil.isStoreWithoutPrefix() ? "test" : "--test";

		String wrongShortSymbol = ArgsUtil.isStoreWithoutPrefix() ? "-test" : "test";
		String wrongLongSymbol = ArgsUtil.isStoreWithoutPrefix() ? "--test" : "test";

		String wrongShortSymbol2 = "-------------test";
		String wrongLongSymbol2 = "---------test";

		String expectedShortSymbol = ArgsUtil.isStoreWithoutPrefix() ? "test" : "-test";
		String expectedLongSymbol = ArgsUtil.isStoreWithoutPrefix() ? "test" : "--test";



		String  afterChecked = ArgsUtil.check(shortSymbol,true);
		assertEquals(expectedShortSymbol,afterChecked);

		afterChecked = ArgsUtil.check(longSymbol,false);
		assertEquals(expectedLongSymbol,afterChecked);


		afterChecked = ArgsUtil.check(wrongShortSymbol,true);
		assertEquals(expectedShortSymbol,afterChecked);

		afterChecked = ArgsUtil.check(wrongLongSymbol,false);
		assertEquals(expectedLongSymbol,afterChecked);



		afterChecked = ArgsUtil.check(wrongShortSymbol2,true);
		assertEquals(expectedShortSymbol,afterChecked);

		afterChecked = ArgsUtil.check(wrongLongSymbol2,false);
		assertEquals(expectedLongSymbol,afterChecked);
	}

	/**
	 *
	 * Method: isShort(String symbol)
	 *
	 */
	@Test
	public void testIsShort() throws Exception {

		log.info("* Run Is short symbol test");

		String symbol = "-test";
		boolean isShort =(Boolean) callPrivateMethod(ArgsUtil.class,"isShortSymbol",new Class[]{String.class},new Object[]{symbol});

		assertTrue(isShort);

		symbol = "--test";
		isShort =(Boolean) callPrivateMethod(ArgsUtil.class,"isShortSymbol",new Class[]{String.class},new Object[]{symbol});

		assertFalse(isShort);

		symbol = "test";
		isShort =(Boolean) callPrivateMethod(ArgsUtil.class,"isShortSymbol",new Class[]{String.class},new Object[]{symbol});

		assertFalse(isShort);
	}

	/**
	 *
	 * Method: isSymbolParameter(String symbol)
	 *
	 */
	@Test
	public void testIsSymbolParameter() {

		log.info("* Run Is symbol test");

		String symbol = "--test";
		boolean isSymbol = ArgsUtil.isSymbolParameter(symbol);

		assertTrue(isSymbol);

		symbol = "-test";
		isSymbol = ArgsUtil.isSymbolParameter(symbol);

		assertTrue(isSymbol);

		symbol = "test";
		isSymbol = ArgsUtil.isSymbolParameter(symbol);

		assertFalse(isSymbol);
	}


	/**
	 *
	 * Method: countOfPrefixes(String str, char prefix)
	 *
	 */
	@Test
	public void testCountOfPrefixes() throws Exception {

		Class [] sign = new Class[]{String.class, char.class};
		String str = "---";
		int count = (Integer)
			callPrivateMethod(ArgsUtil.class,"countOfPrefixes",sign,new Object[]{str,'-'});

		assertEquals(3,count);

	}
}
