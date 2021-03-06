package vara.app.startupargs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vara.app.startupargs.base.RangeNumber;
import vara.app.startupargs.exceptions.ValidationObjectException;

import java.io.File;
import java.util.List;

/**
 * User: Grzegorz (vara) Warywoda
 * Date: 2010-06-20
 * Time: 21:26:01
 */
public abstract class FileListParameter extends AbstractFileValueParameter {
	private static final Logger log = LoggerFactory.getLogger(FileListParameter.class);

	protected FileListParameter(String symbol, String shortSymbol) {
		super(symbol, shortSymbol);
	}

	protected FileListParameter(String symbol, String shortSymbol, AbstractFileValueParameter.BEHAVIOUR behaviour) {
		super(symbol, shortSymbol, behaviour);
	}

	abstract public void handleOption(List<File> optionValue) throws ValidationObjectException;

	@Override
	public RangeNumber getOptionValuesLength() {
		return RangeNumber.ONE_OR_MORE;
	}
}
