package vara.app.startupargs;

import org.apache.log4j.Level;
import vara.app.startupargs.base.Parameters;

import java.util.Arrays;

/**
 * User: Grzegorz (vara) Warywoda
 * Date: 2010-08-25
 * Time: 02:16:39
 */
public class ForDebugQuickBoot extends FixtureUtil{
	public static void main(String[] args) throws Exception {
		beforeClass();
		org.apache.log4j.Logger.getLogger("vara.app.startupargs.base.Parameters").setLevel(Level.INFO);
		Parameters.putParameter(createParameters());

		int [] vals = ArgsParser.getSymbolIndexes(Arrays.asList("--boolean","true","-h"));
		System.out.println("vals.len="+vals.length);
		for (int val : vals) {
			System.out.println("Val:"+val);
		}

		ArgsParser.parseParameters(Arrays.asList("--boolean","2323","-h","dupa"));
	}
}
