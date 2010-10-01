package vara.app.startupargs;

import org.apache.log4j.Logger;
import vara.app.startupargs.base.DefaultParameter;
import vara.app.startupargs.exceptions.ValidationObjectException;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Grzegorz (vara) Warywoda
 * Date: 2010-06-20
 * Time: 21:33:51
 */
public abstract class AbstractFileValueParameter extends DefaultParameter {

	private static final Logger log = Logger.getLogger(AbstractFileValueParameter.class);

	/**
	 *
	 */
	public static enum BEHAVIOUR{
		CHECK_EXISTS,
		DO_NOTHING;
//		CREATE_IF_NOT_EXIST;
	}

	private BEHAVIOUR behaviour;

	/**
	 *
	 * @return
	 */
	public BEHAVIOUR getBehaviour() {
		return behaviour;
	}

	public void setBehaviour(BEHAVIOUR behaviour) {
		this.behaviour = behaviour;
	}

	public AbstractFileValueParameter(String symbol,String shortSymbol){
		this(symbol,shortSymbol,BEHAVIOUR.DO_NOTHING);
	}

	public AbstractFileValueParameter(String symbol,String shortSymbol,BEHAVIOUR behaviour) {
		super(symbol,shortSymbol);
		this.behaviour = behaviour;
	}

	@Override
	public void safeOption(String[] optionValues)  throws ValidationObjectException {
		List<File> files = new ArrayList(optionValues.length);

			for(String optionValue : optionValues){

			   for (String path : parseOptionValue(optionValue,100)){

					File file = checkFile(createFile(path));
					if(file != null){
						files.add(file);
					}
			   }
			}

			handleOption(files);
	}

	/**
	 *
	 * @param file
	 * @return
	 */
	protected File checkFile(File file){

		if(file != null){
			switch (behaviour) {
				case CHECK_EXISTS:
					boolean exists = file.exists();

					if(log.isDebugEnabled())log.debug("Detected CHECK_EXISTS mode, exists="+exists);

					if(!exists){

						file = null;
					}
					break;
				case DO_NOTHING:	break;
	//			case CREATE_IF_NOT_EXIST:	break;
			}
		}
		return file;
	}

	/**
	 *
	 * @param path
	 * @return
	 */
	private static File createFile(String path){

		if(log.isDebugEnabled())log.debug("Try create File object from path '"+path+"'");

		File file=null;
		URI uri = createURI(path);

		if(uri != null){
			try{
				file = new File(uri);
				if(log.isDebugEnabled())log.debug(" from URI:"+uri);

			}catch (IllegalArgumentException e){}

		}

		if(file == null){
			file = new File(path);
		}

		return file;
	}

	private static URI createURI(String path){

		try{
			return new URI(path);
		}catch (Exception e){}

		return null;
	}

	private static String[] parseOptionValue(String value,int limit){
		//TODO: Make separator 
		String [] paths = value.split(",",limit);
		return paths;
	}

	abstract protected void handleOption(List<File> files) throws ValidationObjectException;
}
