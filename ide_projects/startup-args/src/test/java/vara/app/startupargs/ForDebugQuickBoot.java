package vara.app.startupargs;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import vara.app.startupargs.base.Parameters;
import vara.app.startupargs.exceptions.ValidationObjectException;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * User: Grzegorz (vara) Warywoda
 * Date: 2010-08-25
 * Time: 02:16:39
 */
public class ForDebugQuickBoot extends FixtureUtil{
	private static final Logger log = Logger.getLogger(ForDebugQuickBoot.class);

	public static void init(){
		
		FileValueParameter fvp = new FileValueParameter("-file","-f",FileValueParameter.BEHAVIOUR.CHECK_EXISTS){

			@Override
			public void handleOption(File optionValue) throws ValidationObjectException {
				System.out.println("Detected file parameter '"+optionValue+"'");
			}

			@Override
			public String getOptionDescription() {
				return "Set File like parameter";
			}
		};

		FileListParameter fvps = new FileListParameter("-files","-fs",FileValueParameter.BEHAVIOUR.CHECK_EXISTS){

			@Override
			public void handleOption(List<File> optionValue) throws ValidationObjectException {
				System.out.println("Detected input files. Number of files : "+optionValue.size());
				for (File file : optionValue) {
					System.out.println("File:"+file);
				}
			}

			@Override
			public String getOptionDescription() {
				return "Set Files like parameter";
			}
		};

		Parameters.putParameter(fvp,Parameters.InsertBehavior.ReplaceExisting);
		Parameters.putParameter(fvps,Parameters.InsertBehavior.ReplaceExisting);

		GFileListParameter.define("globalFileList","gfl");
	}
	
	
	public static void main(String[] args) throws Exception {
		beforeClass();
		
		init();
		org.apache.log4j.Logger.getLogger("").setLevel(Level.TRACE);
		org.apache.log4j.Logger.getLogger("vara.app.startupargs.base.Parameters").setLevel(Level.DEBUG);
		//org.apache.log4j.Logger.getLogger("vara.app.startupargs").setLevel(Level.TRACE);

		ArgsParser.parseParameters(Arrays.asList("--boolean","2323","-f", "dupa","-fs=/home,/mnt","-fs","/home","/mnt","-gfl=/home,/mnt","-gfl=/"));

		List<File> globFiles = GlobalParameters.getFileListValue("-gfl");
		System.out.println("Global file list size "+globFiles.size());
		for (File file : globFiles) {
			System.out.println(""+file);
		}
	}



}
