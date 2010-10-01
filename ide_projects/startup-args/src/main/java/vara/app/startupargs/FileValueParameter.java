package vara.app.startupargs;

import vara.app.startupargs.base.RangeNumber;
import vara.app.startupargs.exceptions.ValidationObjectException;

import java.io.File;
import java.util.List;

/**
 *
 * @author Grzegorz (vara) Warywoda
 */
public abstract class FileValueParameter extends AbstractFileValueParameter{

	public FileValueParameter(String symbol,String shortSymbol){
		super(symbol,shortSymbol);
	}

	public FileValueParameter(String symbol,String shortSymbol,BEHAVIOUR behaviour) {
		super(symbol,shortSymbol,behaviour);
	}

	@Override
	protected void handleOption(List<File> files) throws ValidationObjectException {
		handleOption(files.get(0));
	}

	public abstract void handleOption(File optionValue) throws ValidationObjectException;

	@Override
	public final RangeNumber getOptionValuesLength() {
		return RangeNumber.ONE;
	}
}
