package vara.app.startupargs;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import vara.app.startupargs.exceptions.ValidationObjectException;

import java.io.File;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * FileValueParameter Tester.
 *
 * @author Grzegorz (vara) Warywoda
 * @since <pre>06/20/2010</pre>
 * @version 1.0
 */
public class FileValueParameterTest{
	
	private static Logger log = Logger.getLogger(FileValueParameterTest.class);

	static final String [] VALID_PATHS = {"file:///home/",
							  "file:///home",
							 "//home",
							  "/home"};

	static final String [] INVALID_PATHS = {"files:///home/",
							  "file:///homeeee",
							 "//?home",
							  "//!/home///"};
	
	FileListParameter testedFileListParameter;
	
	@BeforeClass
	public static void setUp(){
		BasicConfigurator.configure(new ConsoleAppender(new
			PatternLayout(PatternLayout.DEFAULT_CONVERSION_PATTERN)));
	}
	
	@Before
	public void beforeTest(){

	}

	@Test
	public void validationTest(){
		log.info("**Validation file path test");
		final AtomicInteger expectedResults = new AtomicInteger(0);

		testedFileListParameter = new FileListParameter("test","test"){

			@Override
			public void handleOption(List<File> optionValue) throws ValidationObjectException {
				Assert.assertSame(expectedResults.get(),optionValue.size());
			}

			@Override public String getOptionDescription() {return null;}
		};
		
		testedFileListParameter.setBehaviour(AbstractFileValueParameter.BEHAVIOUR.CHECK_EXISTS);
		
		expectedResults.set(VALID_PATHS.length);
		testedFileListParameter.safeOption(VALID_PATHS);
		expectedResults.set(0);
		testedFileListParameter.safeOption(INVALID_PATHS);

	}
}
