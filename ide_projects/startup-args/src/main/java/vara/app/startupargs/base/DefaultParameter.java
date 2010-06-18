package vara.app.startupargs.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vara.app.startupargs.exceptions.BadDeclarationParameter;
import vara.app.startupargs.exceptions.UnexpectedNumberOfArguments;
import vara.app.startupargs.exceptions.ValidationObjectException;

/**
 *
 * @author wara
 */
public abstract class DefaultParameter implements AbstractParameter {
	private static Logger log = LoggerFactory.getLogger(DefaultParameter.class);

	private String symbol;
	private String shortSymbol;

	public DefaultParameter(String option,String shortOption){

		this.symbol = check(option,false);
		this.shortSymbol = check(shortOption,true);
	}

	private static String check(String symbol,boolean isShort){

		if(log.isDebugEnabled())log.debug("Check symbol '{}' isShort: {}",symbol,isShort);

		if(symbol == null){
			if(isShort) return null;
			else throw new NullPointerException("Symbol described parameter must be non-null !");
		}

		if(symbol.isEmpty()){
			if(isShort) return null;
			else throw new BadDeclarationParameter("Symbol described parameter can't be empty ( length != 0 ) !");
		}
		symbol = symbol.trim();
		
		int prefixCount = countOfPrefixes(symbol,'-');
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

	private static int countOfPrefixes(String str,char prefix){
		int counter = 0;
		for(int i=0 ;i<str.length();i++){
			if(str.charAt(i)==prefix) counter++;
			else break;
		}
		return counter;
	}

	@Override
	public String getSymbol() {
		return symbol;
	}

	@Override
	public String getShortSymbol() {
		return shortSymbol;
	}

	@Override
	public String getOptionUsage() {
		if(shortSymbol != null)
			return getSymbol()+"|"+getShortSymbol();
		return symbol;
	}

	@Override
	public String toString() {
		String str = new StringBuilder(getClass().getSimpleName()).
						append("@'").append(symbol).append("':'").append(shortSymbol).append("'").toString();
		return str;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		if( !(obj instanceof DefaultParameter) ) return false;
		if(obj == this) return true;

		DefaultParameter other = ((DefaultParameter)obj);

		return other.getSymbol().equals(symbol) || other.getShortSymbol().equals(shortSymbol);
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 97 * hash + (this.symbol != null ? this.symbol.hashCode() : 0);
		hash = 97 * hash + (this.shortSymbol != null ? this.shortSymbol.hashCode() : 0);
		return hash;
	}

	@Override
	public void handleOption(String[] optionValues)  throws ValidationObjectException{
			int nOptions = optionValues != null? optionValues.length: 0;
			if (nOptions != getOptionValuesLength()){

				String msg = new StringBuilder("Wrong Number of input parameters. Got:").
										append(nOptions).append(" expected:").
										append(getOptionValuesLength()).toString();

				throw new UnexpectedNumberOfArguments(this,msg);
			}

			safeOption(optionValues);
		}

	public abstract boolean isExit();
	public abstract void safeOption(String[] optionValues) throws ValidationObjectException;
}
