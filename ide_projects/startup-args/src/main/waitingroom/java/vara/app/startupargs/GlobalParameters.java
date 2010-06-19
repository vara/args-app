package vara.app.startupargs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vara.app.startupargs.base.AbstractParameter;
import vara.app.startupargs.base.Parameters;

/**
 * User: Grzegorz (vara) Warywoda
 * Date: 2010-06-18
 * Time: 06:01:40
 */
public class GlobalParameters extends Parameters{
	private static final Logger log = LoggerFactory.getLogger(GlobalParameters.class);

	private static GlobalParameter getGlobalParameter(String symbol){

		AbstractParameter resolvedParam = null;

		if(!ArgsUtil.isSymbolParameter(symbol)){
			//Now we know that symbol doesn't contain prefixes
			//create long symbol (with double prefixes)
			String newSymbol = ArgsUtil.check(symbol,false);
			resolvedParam = getParameter(newSymbol);

			if(resolvedParam == null) {

				//Long symbol not found , check for short symbol
				newSymbol = ArgsUtil.check(symbol,true);
				resolvedParam = getParameter(newSymbol);
			}

		}else{
			//Just search symbol
			resolvedParam = getParameter(symbol);
		}

		if(resolvedParam instanceof GlobalParameter)
			return  (GlobalParameter)resolvedParam;

		return null;
	}

	public static Object getValue(String symbol){
		return getValue(symbol,null);
	}

	public static Object getValue(String symbol,Object defaultObject){

		GlobalParameter resolvedParam = getGlobalParameter(symbol);

		return  (resolvedParam != null && resolvedParam.isSet()) ?
											resolvedParam.getValue() : defaultObject;
	}

	public static Integer getIntegerValue(String symbol,Integer defaultObject){
		GlobalParameter resolvedParam = getGlobalParameter(symbol);

		return  (resolvedParam instanceof GIntegerValueParameter && resolvedParam.isSet()) ?
							((GIntegerValueParameter)resolvedParam).getValue() : defaultObject;
	}

	public static Integer getIntegerValue(String symbol){
		return getIntegerValue(symbol,0);
	}

	public static String getStringValue(String symbol){
		return getStringValue(symbol,"");
	}

	public static String getStringValue(String symbol,String defaultObject){
		GlobalParameter resolvedParam = getGlobalParameter(symbol);

		return  (resolvedParam instanceof GStringValueParameter && resolvedParam.isSet()) ?
							((GStringValueParameter)resolvedParam).getValue() : defaultObject;
	}

	public static Boolean getBooleanValue(String symbol){
		return getBooleanValue(symbol,false);
	}

	public static Boolean getBooleanValue(String symbol,Boolean defaultObject){
		GlobalParameter resolvedParam = getGlobalParameter(symbol);

		return  (resolvedParam instanceof GBooleanValueParameter && resolvedParam.isSet()) ?
							((GBooleanValueParameter)resolvedParam).getValue() : defaultObject;
	}
}
