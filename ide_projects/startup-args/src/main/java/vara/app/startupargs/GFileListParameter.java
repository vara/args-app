package vara.app.startupargs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vara.app.startupargs.exceptions.ValidationObjectException;

import java.io.File;
import java.util.List;

/**
 * User: Grzegorz (vara) Warywoda
 * Date: 2010-09-14
 * Time: 03:17:27
 */
public abstract class GFileListParameter extends FileListParameter implements GlobalParameter<List<File>>{
	private static final Logger log = LoggerFactory.getLogger(GFileListParameter.class);

	private List<File> files = null;

	protected GFileListParameter(String symbol, String shortSymbol) {
		super(symbol, shortSymbol);
	}

	protected GFileListParameter(String symbol, String shortSymbol, BEHAVIOUR behaviour) {
		super(symbol, shortSymbol, behaviour);
	}

	@Override
	public void handleOption(List<File> optionValue) throws ValidationObjectException {

		if(files != null && log.isWarnEnabled()){ //Print warning about override global value
			log.warn("Detected overriding global value for {}",this);
			log.warn("Last value {} <= new value {}",files,optionValue);
		}

		files = optionValue;
	}

	@Override
	public List<File> getValue() {
		return files;
	}

	@Override
	public boolean isSet() {
		return files != null;
	}

	/**
	 * Factory methods
	 */

	/**
	 *
	 * @param ls
	 * @param ss
	 * @param description
	 * @param beh
	 */
	public static void define(final String ls,final String ss,final String description,BEHAVIOUR beh){

		final GlobalParameter val = new GFileListParameter(ls,ss,beh){

			@Override
			public String getOptionDescription() {
				return description;
			}
		};

		GlobalParameters.putParameter(val);
	}

	/**
	 *
	 * @param ls
	 * @param ss
	 * @param description
	 */
	public static void define(final String ls,final String ss,final String description){
		define(ls,ss,description,BEHAVIOUR.DO_NOTHING);
	}

	/**
	 *
	 * @param ls
	 * @param ss
	 * @param beh
	 */
	public static void define(final String ls,final String ss,BEHAVIOUR beh){
		define(ls,ss,"",beh);
	}

	/**
	 *
	 * @param ls
	 * @param ss
	 */
	public static void define(final String ls,final String ss){
		define(ls,ss,"");
	}
}
