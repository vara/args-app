package vara.app.startupargs;

import vara.app.startupargs.Exception.ParseOptionException;

import java.io.File;

/**
 *
 * @author Grzegorz (vara) Warywoda
 */
public abstract class FileValueParameter extends SingleValueParameter{

	public FileValueParameter(String symbol,String shortSymbol) {
		super(symbol,shortSymbol);
	}

	@Override
	public void handleOption(String optionValue) {

		File file = createFile(optionValue);
		if(file == null){
			throw new ParseOptionException("Cant resolve path for '"+optionValue+"'.");
		}
		handleOption(file);
	}

	@Override
	public boolean isExit() {
		return false;
	}

	public abstract void handleOption(File optionValue);

	private File createFile(String path){
		File f = new File(path);

		if(!f.exists()){
			f = null;
		}
		return f;
	}
}
