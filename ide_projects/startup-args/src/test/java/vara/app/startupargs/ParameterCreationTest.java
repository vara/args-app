package vara.app.startupargs;

import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Assert;
import org.junit.Test;
import vara.app.startupargs.base.AbstractParameter;
import vara.app.startupargs.exceptions.ValidationObjectException;


/**
 * User: Grzegorz (vara) Warywoda
 * Date: 2010-06-03
 * Time: 22:19:31
 */
public class ParameterCreationTest extends FixtureUtil{
	private static final Logger log = LoggerFactory.getLogger(ParameterCreationTest.class);

	@Before
	public void init(){

		ArgsUtil.setStoreWithoutPrefix(true);
	}

	@Test
	public void createParametersWithPrefixes(){
		log.info("* Create parameters with prefixes test");

		ArgsUtil.setStoreWithoutPrefix(false);
		
		check(new QuickAccessParam("frac","f"),			"frac","f");
		check(new QuickAccessParam("-frac","-f"),		"frac","f");
		check(new QuickAccessParam("--frac","--f"),		"frac","f");
		check(new QuickAccessParam("---frac","---f"),	"frac","f");
		check(new QuickAccessParam("----------frac","-----------f"),"frac","f");
		check(new QuickAccessParam("-frac","--f"),		"frac","f");
//		check(new QuickAccessParam("  -frac","  --f"),"frac","f");
//		check(new QuickAccessParam("  -  frac","-   -f"),"frac","f");

	}

	@Test
	public void createParametersWithoutPrefixes(){
		log.info("* Create parameters without prefixes test");

		boolean withoutPrefixes = true;

		ArgsUtil.setStoreWithoutPrefix(withoutPrefixes);

		check(new QuickAccessParam("frac","f"),			"frac","f", withoutPrefixes);
		check(new QuickAccessParam("-frac","-f"),		"frac","f", withoutPrefixes);
		check(new QuickAccessParam("--frac","--f"),		"frac","f", withoutPrefixes);
		check(new QuickAccessParam("---frac","---f"),	"frac","f", withoutPrefixes);
		check(new QuickAccessParam("----------frac","-----------f"),"frac","f", withoutPrefixes);
		check(new QuickAccessParam("-frac","--f"),		"frac","f", withoutPrefixes);
//		check(new QuickAccessParam("  -frac","  --f"),"frac","f");
//		check(new QuickAccessParam("  -  frac","-   -f"),"frac","f");

	}

	private static void check(AbstractParameter param,String s,String ss){
		check(param,s,ss,false);
	}

	private static void check(AbstractParameter param,String s,String ss,boolean withoutPrefixes){

		Assert.assertEquals( (withoutPrefixes? s  : "--"+s ) ,param.getSymbol());
		Assert.assertEquals( (withoutPrefixes? ss : "-"+ss ) ,param.getShortSymbol());
	}



	class QuickAccessParam extends NoValueParameter{
		QuickAccessParam(String symbol, String shortSymbol) {
			super(symbol, shortSymbol);
		}

		@Override
		public void handleOption() throws ValidationObjectException {
		}

		@Override
		public String getOptionDescription() {
			return null;
		}
	}
}
