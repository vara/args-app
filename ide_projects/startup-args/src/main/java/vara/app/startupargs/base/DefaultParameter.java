package vara.app.startupargs.base;

import vara.app.startupargs.Exception.ParseOptionException;

/**
 *
 * @author wara
 */
public abstract class DefaultParameter implements AbstractParameter {

	private String option;
	private String shortOption;

	public DefaultParameter(String option,String shortOption){

		//TODO:Added detection for prefixes '--' and '-'
		this.option = option;
		this.shortOption = shortOption;
	}

	@Override
	public String getSymbol() {
		return option;
	}

	@Override
	public String getShortSymbol() {
		return shortOption;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName()+"@'"+option+"':'"+shortOption+"'";
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		if( !(obj instanceof DefaultParameter) ) return false;
		if(obj == this) return true;

		DefaultParameter other = ((DefaultParameter)obj);

		return other.getSymbol().equals(option) || other.getShortSymbol().equals(shortOption);
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 97 * hash + (this.option != null ? this.option.hashCode() : 0);
		hash = 97 * hash + (this.shortOption != null ? this.shortOption.hashCode() : 0);
		return hash;
	}

	@Override
	public void handleOption(String[] optionValues){
			int nOptions = optionValues != null? optionValues.length: 0;
			if (nOptions != getOptionValuesLength()){
				throw new ParseOptionException("Wrong Number of input parameters. Got:"+nOptions+" expected:"+getOptionValuesLength());
			}

			safeOption(optionValues);
		}

	public abstract boolean isExit();
	public abstract void safeOption(String[] optionValues);
}
