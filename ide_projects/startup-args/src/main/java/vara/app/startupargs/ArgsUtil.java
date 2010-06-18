package vara.app.startupargs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vara.app.startupargs.exceptions.BadDeclarationParameter;

/**
 * User: Grzegorz (vara) Warywoda
 * Date: 2010-06-18
 * Time: 05:35:43
 */
public class ArgsUtil {
	private static final Logger log = LoggerFactory.getLogger(ArgsUtil.class);

	private static char prefixChar = '-';

	public static String check(String symbol,boolean isShort){

		if(log.isDebugEnabled())log.debug("Check symbol '{}' willBeShort: {}",symbol,isShort);

		if(symbol == null){
			if(isShort) return null;
			else throw new NullPointerException("Symbol described parameter must be non-null !");
		}

		if(symbol.isEmpty()){
			if(isShort) return null;
			else throw new BadDeclarationParameter("Symbol described parameter can't be empty ( length != 0 ) !");
		}
		symbol = symbol.trim();

		int prefixCount = countOfPrefixes(symbol,prefixChar);
		int neededPref = isShort ? 1 : 2;

		if(prefixCount != neededPref){

			if(log.isDebugEnabled())log.debug("Actual Prefixes : {} Need: {}",prefixCount,neededPref);

			if(prefixCount>neededPref){
				int begin = prefixCount-neededPref;
				symbol = symbol.substring(begin);
				if(log.isDebugEnabled())log.debug("Substring from : {} result {}",begin,symbol);
			}else{
				int diff = (neededPref-prefixCount);
				symbol =  (diff == 1 ?"-" : "--") +symbol;
				if(log.isDebugEnabled())log.debug("Is less needed pref : {} result {}",diff,symbol);
			}
		}

		return symbol;
	}

	public boolean isShort(String symbol){
		return countOfPrefixes(symbol,prefixChar) == 1;
	}

	public static boolean isSymbolParameter(String symbol){

		if(symbol != null && !symbol.isEmpty())
			return  symbol.charAt(0) == prefixChar;
		return false;
	}

	private static int countOfPrefixes(String str,char prefix){
		int counter = 0;
		for(int i=0 ;i<str.length();i++){
			if(str.charAt(i)==prefix) counter++;
			else break;
		}
		return counter;
	}
}
