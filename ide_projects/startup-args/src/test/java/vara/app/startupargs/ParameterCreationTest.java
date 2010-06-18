package vara.app.startupargs;

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

	@Test
	public void createParamsPrefixes(){
		log.info("Create parameters test");

		check(new QuickAccessParam("frac","f"),			"frac","f");
		check(new QuickAccessParam("-frac","-f"),		"frac","f");
		check(new QuickAccessParam("--frac","--f"),		"frac","f");
		check(new QuickAccessParam("---frac","---f"),	"frac","f");
		check(new QuickAccessParam("----------frac","-----------f"),"frac","f");
		check(new QuickAccessParam("-frac","--f"),		"frac","f");
//		check(new QuickAccessParam("  -frac","  --f"),"frac","f");
//		check(new QuickAccessParam("  -  frac","-   -f"),"frac","f");
	}

	private static void check(AbstractParameter param,String s,String ss){
		Assert.assertEquals("--"+s,param.getSymbol());
		Assert.assertEquals("-"+ss,param.getShortSymbol());
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
