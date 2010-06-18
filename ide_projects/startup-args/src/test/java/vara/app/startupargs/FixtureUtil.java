package vara.app.startupargs;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.junit.Before;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vara.app.startupargs.base.AbstractParameter;
import vara.app.startupargs.exceptions.CatchOnException;

import java.util.ArrayList;
import java.util.List;

/**
* User: Grzegorz (vara) Warywoda
* Date: 2010-03-04
* Time: 23:03:26
*/

//@Ignore /*http://jira.codehaus.org/browse/SUREFIRE-482 <ignore == skip>*/
public class FixtureUtil {
private static final Logger log = LoggerFactory.getLogger(FixtureUtil.class);

@BeforeClass
public static void beforeClass() throws Exception {
	BasicConfigurator.configure();
	org.apache.log4j.Logger.getLogger("org.apache.commons").setLevel(Level.INFO);
	ArgsParser.setCatchOnException(new Output());
	//JKlipperLoggerManager.activate();
}

@Before
public void beforeTest() throws Exception {}

static class Output implements CatchOnException{
	@Override
	public void caughtException(Exception e) {
		log.warn("",e);
	}
}

public static List<AbstractParameter> createParameters(){

	List<AbstractParameter> params = new ArrayList();

	params.add(new NoValueParameter("--verbose","-v") {
		@Override public void handleOption() {
			System.out.println("Detected verbose option");
		}
		@Override
		public String getOptionDescription() {
			return "option set debug level on root logger";
		}
	});

	params.add(new FloatValueParameter("--fraction","-f"){
		@Override
		public void handleOption(float optionValue) {
			System.out.println("Detected fraction option, value was set to "+optionValue);
		}

		@Override
		public String getOptionDescription() {
			return "Set fraction value [float]";
		}
	});

	params.add(new BooleanValueParameter("--boolean","-b"){
		@Override
		public void handleOption(boolean optionValue) {
			System.out.println("Detected boolean parameter, value was set to "+optionValue);
		}

		@Override
		public String getOptionDescription() {
			return "Set flag like a boolean value (true or false)";
		}
	});

	return params;
}
}
