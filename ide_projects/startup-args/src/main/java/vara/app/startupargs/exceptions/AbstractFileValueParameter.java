package vara.app.startupargs.exceptions;

import vara.app.startupargs.base.DefaultParameter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Grzegorz (vara) Warywoda
 * Date: 2010-06-20
 * Time: 21:33:51
 */
public abstract class AbstractFileValueParameter extends DefaultParameter {

	public static enum BEHAVIOUR{
		CHECK_EXISTS,
		DO_NOTHING;
//		CREATE_IF_NOT_EXIST;
	}

	public BEHAVIOUR getBehaviour() {
		return behaviour;
	}

	private BEHAVIOUR behaviour;

	public AbstractFileValueParameter(String symbol,String shortSymbol){
		this(symbol,shortSymbol,BEHAVIOUR.DO_NOTHING);
	}

	public AbstractFileValueParameter(String symbol,String shortSymbol,BEHAVIOUR behaviour) {
		super(symbol,shortSymbol);
		this.behaviour = behaviour;
	}

	@Override
	public void safeOption(String[] optionValues)  throws ValidationObjectException{
		List<File> files = new ArrayList(optionValues.length);

			for(String optionValue : optionValues){

			   for (String path : parseOptionValue(optionValue,100)){
					files.add(createFile(path));
			   }
			}

			handleOptionFiles(files);
	}

	protected File createFile(String path){
		File f = new File(path);

		switch (behaviour) {
			case CHECK_EXISTS:
				if(!f.exists()){
					f = null;
				}
				break;
			case DO_NOTHING:	break;
//			case CREATE_IF_NOT_EXIST:	break;
		}
		return f;
	}

	private String[] parseOptionValue(String value,int limit){
		String [] paths = value.split(",",limit);
		return paths;
	}

	protected abstract void handleOptionFiles(List<File> files) throws ValidationObjectException;
}
