package vara.app.startupargs.base;

import org.apache.log4j.Logger;
import vara.app.startupargs.exceptions.BadDeclarationParameter;
import vara.app.startupargs.exceptions.UnexpectedNumberOfArguments;
import vara.app.startupargs.exceptions.ValidationObjectException;

/**
 *
 * @author wara
 */
public abstract class DefaultParameter implements AbstractParameter {
	private static Logger log = Logger.getLogger(DefaultParameter.class);

	private String symbol;
	private String shortSymbol;

	public DefaultParameter(String option,String shortOption){

		this.symbol = check(option,false);
		this.shortSymbol = check(shortOption,true);
	}

	private static String check(String symbol,boolean isShort){

		if(log.isDebugEnabled())log.debug("Check symbol '"+symbol+"' isShort:"+isShort);

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

			if(log.isDebugEnabled())log.debug("Actual Prefixes : "+prefixCount +" Need:"+neededPref);

			if(prefixCount>neededPref){
				int begin = prefixCount-neededPref;
				symbol = symbol.substring(begin);
				if(log.isDebugEnabled())log.debug("Substring from:"+begin +" Result:"+symbol);
			}else{
				int diff = (neededPref-prefixCount);
				symbol =  (diff == 1 ?"-" : "--") +symbol;
				if(log.isDebugEnabled())log.debug("Is less needed pref :" + diff +"result "+symbol);
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
		return getClass().getSimpleName()+"@'"+ symbol +"':'"+ shortSymbol +"'";
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
				throw new UnexpectedNumberOfArguments(this,"Wrong Number of input parameters. Got:"+nOptions+" expected:"+getOptionValuesLength());
			}

			safeOption(optionValues);
		}

	public abstract boolean isExit();
	public abstract void safeOption(String[] optionValues) throws ValidationObjectException;
}
