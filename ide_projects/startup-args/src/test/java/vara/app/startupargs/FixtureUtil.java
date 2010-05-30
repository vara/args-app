package vara.app.startupargs;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import vara.app.startupargs.base.DefaultParameter;
import vara.app.startupargs.base.Parameters;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Grzegorz (vara) Warywoda
 * Date: 2010-03-04
 * Time: 23:03:26
 */

//@Ignore /*http://jira.codehaus.org/browse/SUREFIRE-482 <ignore == skip>*/
public class FixtureUtil {
	private static final Logger log = Logger.getLogger(FixtureUtil.class);

	@BeforeClass
	public static void beforeClass() throws Exception {
		BasicConfigurator.configure();
		Logger.getLogger("org.apache.commons").setLevel(Level.INFO);
		//JKlipperLoggerManager.activate();
	}

	@Before
	public void beforeTest() throws Exception {}


	public static List<DefaultParameter> createParameters(){

        List<DefaultParameter> params = new ArrayList<DefaultParameter>();

		params.add(new NoValueParameter("--help","-h") {
            @Override
            public void handleOption() {
                List<DefaultParameter> params = Parameters.getAllParameters();
				System.out.println("Options:");
				for (DefaultParameter ap : params) {
					System.out.printf("%s or %s\n\t %s\n",ap.getSymbol(),ap.getShortSymbol(),ap.getOptionDescription());
				}
            }

            @Override
            public String getOptionDescription() {
                return "Print help message";
            }

			@Override
			public boolean isExit() {
				return true;
			}
        });

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
