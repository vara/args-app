package vara.app.startupargs;

import vara.app.startupargs.exceptions.UnexpectedValueException;
import vara.app.startupargs.exceptions.ValidationObjectException;

import java.io.File;

/**
 *
 * @author Grzegorz (vara) Warywoda
 */
public abstract class FileValueParameter extends StringValueParameter {

	public FileValueParameter(String symbol,String shortSymbol) {
		super(symbol,shortSymbol);
	}

	@Override
	public void handleOption(String optionValue)  throws ValidationObjectException{

		File file = createFile(optionValue);
		if(file == null){
			throw new UnexpectedValueException(this,"Cant resolve path for '"+optionValue+"'.");
		}
		handleOption(file);
	}

	@Override
	public boolean isExit() {
		return false;
	}

	public abstract void handleOption(File optionValue) throws ValidationObjectException;

	private File createFile(String path){
		File f = new File(path);

		if(!f.exists()){
			f = null;
		}
		return f;
	}
}
